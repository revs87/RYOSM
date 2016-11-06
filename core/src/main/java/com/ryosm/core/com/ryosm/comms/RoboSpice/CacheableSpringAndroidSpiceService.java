
package com.ryosm.core.com.ryosm.comms.RoboSpice;

import android.app.Application;
import android.content.Context;

import com.octo.android.robospice.networkstate.NetworkStateChecker;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.ryosm.core.com.ryosm.Configs;
import com.ryosm.core.com.ryosm.comms.RoboSpice.rest.CoreRestTemplate;
import com.ryosm.core.com.ryosm.comms.RoboSpice.rest.CoreSpringAndroidSpiceService;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.util.List;

public class CacheableSpringAndroidSpiceService extends
                                                CoreSpringAndroidSpiceService {

    @Override
    public CoreRestTemplate createCoreRestTemplate() {

        CoreRestTemplate restTemplate = new CoreRestTemplate();

        // Setup connection and read timeouts
        ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory())
                .setReadTimeout(Configs.TIMEOUT_CONNECTION);
        ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory())
                .setConnectTimeout(Configs.TIMEOUT_CONNECTION);

        // ** Create message converters

        // To convert http payload to Json Objects
        // GsonHttpMessageConverter jsonConverter = new
        // GsonHttpMessageConverter();

        // To handle form data, including multipart form data
        FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();

        // To read and write strings
        // By default, this converter supports all media types (*/*), and writes
        // with a Content-Type of text/plain.
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();

        // To read and write byte arrays.
        // By default, this converter supports all media types (*/*), and writes
        // with a Content-Type of application/octet-stream
        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();

        final List<HttpMessageConverter<?>> listHttpMessageConverters = restTemplate
                .getMessageConverters();

        // ** Insert converters in restTemplate
        // listHttpMessageConverters.add(jsonConverter);
        listHttpMessageConverters.add(formHttpMessageConverter);
        listHttpMessageConverters.add(stringHttpMessageConverter);
        listHttpMessageConverters.add(byteArrayHttpMessageConverter);
        restTemplate.setMessageConverters(listHttpMessageConverters);

        //TODO
//        if (Configs.TEST_SERVER_URL != null
//                && Configs.TEST_SERVER_URL.startsWith("https")) {
//            restTemplate.setRequestFactory(new SimpleClientHttpSSLPinningRequestFactory());
//        }

        return restTemplate;
    }

    @Override
    public CacheManager createCacheManager(Application application)
            throws CacheCreationException {

        // Setup cache manager to cache Json Objects, with gsonPersister
        CacheManager cacheManager = new CacheManager();
        MemoryGsonObjectPersisterFactory gsonObjectPersisterFactory = new MemoryGsonObjectPersisterFactory(
                application);
        cacheManager.addPersister(gsonObjectPersisterFactory);
        return cacheManager;

    }

    @Override
    protected NetworkStateChecker getNetworkStateChecker() {

        if (Configs.INFINITE_CACHE) {
            return new NetworkStateChecker() {

                @Override
                public boolean isNetworkAvailable(Context arg0) {
                    return true;
                }

                @Override
                public void checkPermissions(Context arg0) {

                }
            };
        }

        return super.getNetworkStateChecker();
    }

    @Override
    public int getThreadCount() {
        return Configs.NBR_OF_THREADS_TO_PROCESS_REQUESTS;
    }

}
