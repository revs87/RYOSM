package com.ryosm.core.com.ryosm.comms.RoboSpice.rest;

import com.ryosm.core.com.ryosm.utils.L;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by revs on 16/10/2016.
 */

public class CoreLoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        L.debug("===========================request begin================================================");
        L.debug("URI         : {}" + request.getURI());
        L.debug("Method      : {}" + request.getMethod());
        L.debug("Headers     : {}" + request.getHeaders());
        L.debug("Request body: {}" + new String(body, "UTF-8"));
        L.debug("==========================request end================================================");
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
        String line = bufferedReader.readLine();
        while (line != null) {
            inputStringBuilder.append(line);
            inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
        L.debug("============================response begin==========================================");
        L.debug("Status code  : {}" + response.getStatusCode());
        L.debug("Status text  : {}" + response.getStatusText());
        L.debug("Headers      : {}" + response.getHeaders());
        L.debug("ResponseObj body: {}" + inputStringBuilder.toString());
        L.debug("=======================response end=================================================");
    }
}