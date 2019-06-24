package com.automation.techassessment.api.endpoints;

import com.automation.techassessment.APIPropertyManager;
import com.automation.techassessment.api.authentication.FauxTrustManager;
import com.automation.techassessment.api.errors.ApiEndpointInitializationError;
import com.automation.techassessment.api.interceptor.GlobalInterceptor;
import com.automation.techassessment.api.util.RunUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.CookieJar;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

public class ApiEndpointDefaults {
    private static Logger log;
    private static String baseUrl;
    private static Gson gson;
    private static GsonConverterFactory gsonConverterFactory;
    private static Retrofit retrofit;
    private static okhttp3.OkHttpClient httpClient;

    static {
        try {
            init();
        } catch (ApiEndpointInitializationError e) {
             throw new Error(e);
        }
    }

    /**
     *  If you refactor this, please ensure you understand how retrofit builders work.
     *  With retrofit, you typically want a single Retrofit instance for each communication pipeline. Retrofit is typically used in
     *  Android app development where multiple open connections are handled by the framework. For the test framework, we only want one
     *  retrofit instance.
     *
     *  If you create a 2nd retrofit instance, you will see all network requests happen twice. In order to extend the current retrofit
     *  instance, you need to call {@link Retrofit#newBuilder()} instead of creating a `new Retrofit.Builder` instance.
     *  {@link ApiEndpoints#newBuilder()} has been created to help ensure others don't trip up on this.
     */
    private static void init() throws ApiEndpointInitializationError {   
        log = LogManager.getLogger(ApiEndpointDefaults.class);     
        baseUrl = APIPropertyManager.getInstance().getBaseAPIUrl();      
        if(!baseUrl.startsWith("http")) {
           baseUrl = "https://" + baseUrl;
        }
        if(!baseUrl.endsWith("/3/")) {
            baseUrl = baseUrl + "/3/";
        }
        gson = initGson();
        gsonConverterFactory = GsonConverterFactory.create(gson);
        httpClient = createNewDefaultHttpClient();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .client(httpClient)
                .build();
    }

    public static String getDefaultBaseUrl() {
        return baseUrl;
    }

    public static Gson getDefaultGsonInstance() {
        return gson;
    }

    /**
     * Add any custom GsonBuilder configuration you need here.
     * @return Gson instance which will be set to the global gson instance for this class.
     */
    private static Gson initGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        return gsonBuilder.setLenient().create();
    }

    /**
     * This method ensures you get a builder instance based on the singleton retrofit instance this class contains.
     *  If you do not do this you will find yourself with two retrofit instances, which then means all retrofit service calls will
     *  happen twice.
     */
    public static Retrofit.Builder getRetrofitClientBuilder() {
        return retrofit.newBuilder();
    }

    /**
     * This method is the heart of initializing our OkHttpClient. Here is everything it currently handles as why:
     *  - {@link FauxTrustManager} is included to ensure we trust all certificates. This is essentially what RelaxedHTTP mode in
     *      RestAssured does.
     *  - {@link HttpLoggingInterceptor} is included to provide network logging by default. This class should be extended to handle
     *      logging control logic.
     *  - {@link CookieJar} is exactly what it sounds like. It stores cookies related to HTTP traffic for our OkHttpClient.
     *  - {@link GlobalInterceptor} contains logic for Headers that should be included on every single request this client makes.
     *  - Timeouts are included as a slight override to the OkHttp Defaults, which are 10 seconds for connection, write, and read. When
     *      debugging the default timeouts are used.
     *
     * @return OkHttpClient Client for all Network traffic within Retrotfit and the rest of this framework.
     */
    public static okhttp3.OkHttpClient createNewDefaultHttpClient() throws ApiEndpointInitializationError {
        try {
            FauxTrustManager fauxTrustManager = new FauxTrustManager();

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[] { fauxTrustManager }, new SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            okhttp3.OkHttpClient.Builder clientBuilder = new okhttp3.OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, fauxTrustManager)
                    .followRedirects(true)
                    .followSslRedirects(true)
                    .addInterceptor(new GlobalInterceptor())
                    .addInterceptor(getHttpLoggingInterceptor())
                    .hostnameVerifier((hostname, session) -> true);

            if(!RunUtils.isDebugging()) {
                clientBuilder.connectTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS);
            }
            return clientBuilder.build();

        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * //TODO: Allow the logging level to be set via configuration/property.
     * @return basic implementation of {@link HttpLoggingInterceptor} with logging level configured.
     */
    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return httpLoggingInterceptor;
    }
}
