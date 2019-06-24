package com.automation.techassessment.api.errors;

import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.text.MessageFormat;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A library of assertions related to REST calls made by the framework.
 * <b>These are to be called from tests!</b>  No one else should call these methods, only tests.
 */
public class RestAssertions {
    private static Logger log = LogManager.getLogger(RestAssertions.class);
    /**
     * Execute a call and throw an AssertionError if the call was not successful.  The call is subjected to the
     * following criteria:
     * <ol>
     *     <li>HTTP response code is between 200 and 300</li>
     *     <li>The response is able to be read without an IOException occurring.</li>
     * </ol>
     *
     * @param call The call to execute and check the response of
     * @param <T> The Response type
     * @return The response as well as the body of the response (so you can perform further assertions if necessary).
     */
    public static <T> Response<T> assertCallSucceeds(Call<T> call) {
        Response<T> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new RestAssertionError(MessageFormat.format("REST request {0} to {1} caused an IOException.", call.request().method(), call.request().url()), e);
        }
        if(!response.isSuccessful()) {
            Request request = response.raw().request();
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Request:\n========================================================================\n");
            errorMessage.append(MessageFormat.format("{0} {1}\n", request.method(), request.url()));
            for(String headerName: request.headers().names()) {
                errorMessage.append(MessageFormat.format("{0}: {1}\n", headerName, request.header(headerName)));
            }
            errorMessage.append("\n");
            Buffer bodyBuffer = new Buffer();
            try {
                if(request.body() != null) {
                    request.body().writeTo(bodyBuffer);
                    errorMessage.append(bodyBuffer.readUtf8());
                } else {
                    errorMessage.append("Body of request not available.");
                }
                errorMessage.append("\n");
            } catch (IOException e) {
                errorMessage.append("Unable to get body from request: ");
                errorMessage.append(e.getMessage());
                errorMessage.append("\n");
            }
            errorMessage.append("\n");
            errorMessage.append("Response:\n=======================================================================\n");
            for(String headerName: response.headers().names()) {
                errorMessage.append(MessageFormat.format("{0}: {1}\n", headerName, response.headers().get(headerName)));
            }
            errorMessage.append("\n");
            ResponseBody errorBody = response.errorBody();
            String responseBody = "Body of error response not available.";
            if(errorBody != null) {
                try {
                    responseBody = errorBody.string();
                } catch (IOException e) {
                    responseBody = "Trying to get response body caused IOException: " + e.getMessage();
                }
            }
            errorMessage.append(responseBody);
            throw new RestAssertionError(MessageFormat.format("Request to {0} was not successful: \n{1}", request.url(), errorMessage.toString()));
        }
        return response;
    }
    
    /**
     * Execute a call and throw an AssertionError if the call does not include an error with the data.  The call is subjected to the
     * following criteria:
     * <ol>
     *     <li>HTTP response code is between 400 and 500</li>
     *     <li>The response is able to be read without an IOException occurring.</li>
     * </ol>
     *
     * @param call The call to execute and check the response of
     * @param <T> The Response type
     * @return The response as well as the body of the response (so you can perform further assertions if necessary).
     */
    public static <T> Response<T> assertCallDataError(Call<T> call) {
        Response<T> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new RestAssertionError(MessageFormat.format("REST request {0} to {1} caused an IOException.", call.request().method(), call.request().url()), e);
        }
        if(response.isSuccessful()) {
            Request request = response.raw().request();
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("Request:\n========================================================================\n");
            errorMessage.append(MessageFormat.format("{0} {1}\n", request.method(), request.url()));
            for(String headerName: request.headers().names()) {
                errorMessage.append(MessageFormat.format("{0}: {1}\n", headerName, request.header(headerName)));
            }
            errorMessage.append("\n");
            Buffer bodyBuffer = new Buffer();
            try {
                if(request.body() != null) {
                    request.body().writeTo(bodyBuffer);
                    errorMessage.append(bodyBuffer.readUtf8());
                } else {
                    errorMessage.append("Body of request not available.");
                }
                errorMessage.append("\n");
            } catch (IOException e) {
                errorMessage.append("Unable to get body from request: ");
                errorMessage.append(e.getMessage());
                errorMessage.append("\n");
            }
            errorMessage.append("\n");
            errorMessage.append("Response:\n=======================================================================\n");
            for(String headerName: response.headers().names()) {
                errorMessage.append(MessageFormat.format("{0}: {1}\n", headerName, response.headers().get(headerName)));
            }
            errorMessage.append("\n");
            ResponseBody errorBody = response.errorBody();
            String responseBody = "Body of error response not available.";
            if(errorBody != null) {
                try {
                    responseBody = errorBody.string();
                } catch (IOException e) {
                    responseBody = "Trying to get response body caused IOException: " + e.getMessage();
                }
            }
            errorMessage.append(responseBody);
            throw new RestAssertionError(MessageFormat.format("Request to {0} was not successful: \n{1}", request.url(), errorMessage.toString()));
        }
        return response;
    }
}
