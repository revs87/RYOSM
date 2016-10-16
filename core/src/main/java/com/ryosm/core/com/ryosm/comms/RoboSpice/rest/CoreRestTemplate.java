package com.ryosm.core.com.ryosm.comms.RoboSpice.rest;

import android.util.Log;

import com.ryosm.core.com.ryosm.utils.L;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

/**
 * Created by revs on 16/10/2016.
 */

public class CoreRestTemplate extends RestTemplate {

    private String TAG = "CoreRestTemplate";

    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback,
                              ResponseExtractor<T> responseExtractor) throws RestClientException {

        Assert.notNull(url, "'url' must not be null");
        Assert.notNull(method, "'method' must not be null");
        ClientHttpResponse response = null;
        try {
            ClientHttpRequest request = createRequest(url, method);
            if (requestCallback != null) {
                requestCallback.doWithRequest(request);
            }


            response = request.execute();
            if (!getErrorHandler().hasError(response)) {
                logResponseStatus(method, url, response);
            } else {
                handleResponseError(method, url, response);
            }
            if (responseExtractor != null) {
                return responseExtractor.extractData(response);
            } else {
                return null;
            }
        } catch (IOException ex) {
            throw new ResourceAccessException("I/O error: " + ex.getMessage(), ex);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private void logResponseStatus(HttpMethod method, URI url, ClientHttpResponse response) {
        if (L.isLoggable(TAG, Log.DEBUG)) {
            try {
                L.d(TAG,
                        method.name() + " request for \"" + url + "\" resulted in " + response.getStatusCode() + " (" +
                                response.getStatusText() + ")");
            } catch (IOException e) {
                // ignore
            }
        }
    }

    private void handleResponseError(HttpMethod method, URI url, ClientHttpResponse response) throws IOException {
        if (L.isLoggable(TAG, Log.WARN)) {
            try {
                L.w(TAG,
                        method.name() + " request for \"" + url + "\" resulted in " + response.getStatusCode() + " (" +
                                response.getStatusText() + "); invoking error handler");
            } catch (IOException e) {
                // ignore
            }
        }
        getErrorHandler().handleError(response);
    }
}
