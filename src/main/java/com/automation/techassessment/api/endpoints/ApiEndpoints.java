package com.automation.techassessment.api.endpoints;

import com.automation.techassessment.api.UsePort;
import com.automation.techassessment.api.endpoints.tv.TVEndpoint;
import com.automation.techassessment.api.endpoints.movie.MovieEndpoint;
import com.google.gson.Gson;
import com.automation.techassessment.api.errors.ApiEndpointInitializationError;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * The root of the low level api client.  Low level means that all that is offered is the actual rest call and
 * serialization (possibly) of the result.  These are all defined in interfaces using retrofit.  Look at the endpoints
 * package for various endpoint examples.  These should be logically constructed into a simple tree of attributes off
 * of this class.
 *
 * Creating an instance of this class is done from it's builder.  By default the builder looks at the System properties
 * for information about what target to use, however you can set this yourself.
 */
public class ApiEndpoints {

    protected Retrofit retrofit = null;
    protected OkHttpClient client = null;
    private String baseUrl = null;
    public TVEndpoint tv = null;
    public MovieEndpoint movie = null;
    
    public static final class Builder {
        private String baseUrl = null;
        private Gson gson = null;
        private OkHttpClient httpClient = null;

        private Builder() {

        }

        public void baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public void gson(Gson gson) {
            this.gson = gson;
        }

        public void httpClient(OkHttpClient client) {
            this.httpClient = client;
        }

        private void validateBaseUrl() throws ApiEndpointInitializationError {
            if(baseUrl == null) {
                baseUrl = ApiEndpointDefaults.getDefaultBaseUrl();
            }
            if(baseUrl == null) {
                throw new ApiEndpointInitializationError("baseUrl", "null");
            }
            if("".equals(baseUrl)) {
                throw new ApiEndpointInitializationError("baseUrl", "empty string");
            }
            if(!baseUrl.startsWith("http")) {
                throw new ApiEndpointInitializationError("baseUrl", "non http/https value: " + baseUrl);
            }
            try {
                new URL(baseUrl);
            } catch (MalformedURLException e) {
                throw new ApiEndpointInitializationError("Malformed URL provided to ApiEndpoints: " + baseUrl, e);
            }
        }

        public ApiEndpoints build() throws ApiEndpointInitializationError {
            validateBaseUrl();
            if(gson == null) {
                gson = ApiEndpointDefaults.getDefaultGsonInstance();
            }
            if(httpClient == null) {
                this.httpClient = ApiEndpointDefaults.createNewDefaultHttpClient();
            }
            return new ApiEndpoints(this);
        }

    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private ApiEndpoints(Builder builder) {
        baseUrl = builder.baseUrl;
        Retrofit.Builder retrofitBuilder = ApiEndpointDefaults.getRetrofitClientBuilder()
                                           .baseUrl(builder.baseUrl)
                                           .client(builder.httpClient);
        retrofitBuilder.converterFactories().clear();
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create(builder.gson));
        retrofit = retrofitBuilder.build();
        client = builder.httpClient;
        buildEndpointInstances();
    }



    /**
     * In case you are inheriting this class (I don't know why you would), you can override this to be called from
     * the constructor to build the endpoint instances that make up the attributes of this class.
     */
    public void buildEndpointInstances() {
        tv = createService(TVEndpoint.class);
        movie = createService(MovieEndpoint.class);
    }

    /**
     * If you want to build a different retrofit instance yourself, that's fine, but retrofit has some interesting
     * global state variables, so you might want to get the builder from this instance and just override what you need.
     *
     * Not using this, but creating your own retrofit builder could cause multiple requests to happen from one method
     * call.
     *
     * @return a new builder base off the current retrofit instance.
     */
    public Retrofit.Builder getRetrofitBuilder() {
        return retrofit.newBuilder();
    }

    /**
     * Create a Service Object
     * @param serviceClass Service Class interface which is a representation of an API Service.
     * @param <T> Type is NOT protected by Annotation or Marker Interface.
     * @return Instance of a Class defined by the Type interface passed in.
     */
    public <T> T createService(Class<T> serviceClass) {
        if(serviceClass.isAnnotationPresent(UsePort.class)) {
            return createService(serviceClass, serviceClass.getAnnotation(UsePort.class).value());
        }
        return retrofit.create(serviceClass);
    }

    /**
     * Create a Service (API Endpoint client based on interface definition) using a custom port.  The same host name
     * is used, but SSL is turned off and the custom port is used.
     *
     * @param serviceClass The interface you want an instance of.
     * @param port the port you want to target
     * @param <T> The definition of the REST interface
     * @return A retrofit client instance of the defined REST interface.
     */
    public <T> T createService(Class<T> serviceClass, int port) {
        String hostCopy = baseUrl;
        if(hostCopy.contains("https")) {
            hostCopy = hostCopy.replace("https", "http");
        }

        Retrofit.Builder builder = retrofit.newBuilder()
                .baseUrl(hostCopy + ":" + port);
        builder.converterFactories().clear();
        builder.converterFactories().addAll(retrofit.converterFactories());
        builder.client(client);
        return builder.build().create(serviceClass);

    }

}
