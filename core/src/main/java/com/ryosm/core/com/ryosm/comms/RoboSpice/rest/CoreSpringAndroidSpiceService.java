package com.ryosm.core.com.ryosm.comms.RoboSpice.rest;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.request.CachedSpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by revs on 16/10/2016.
 */

public abstract class CoreSpringAndroidSpiceService extends SpiceService {

    private CoreRestTemplate restTemplate;

    @Override
    public void onCreate() {
        super.onCreate();
        restTemplate = createCoreRestTemplate();

        //set interceptors/requestFactory
        ClientHttpRequestInterceptor ri = new CoreLoggingRequestInterceptor();
        List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
        ris.add(ri);

        //set restTemplate with interceptors
        restTemplate.setInterceptors(ris);
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
    }

    public abstract CoreRestTemplate createCoreRestTemplate();

    @Override
    public void addRequest(CachedSpiceRequest<?> request, Set<RequestListener<?>> listRequestListener) {
        if (request.getSpiceRequest() instanceof SpringAndroidSpiceRequest) {
            ((SpringAndroidSpiceRequest<?>) request.getSpiceRequest()).setRestTemplate(restTemplate);
        }
        super.addRequest(request, listRequestListener);
    }

}
