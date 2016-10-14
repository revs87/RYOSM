package com.ryosm.core.com.ryosm.comms.RoboSpice;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.ryosm.core.com.ryosm.CommunicationCenter;
import com.ryosm.core.com.ryosm.utils.L;

import org.springframework.http.HttpHeaders;

import java.net.HttpCookie;
import java.util.List;

public abstract class BaseSpiceRequest<REQ, RESP> extends
        SpringAndroidSpiceRequest<RESP> {

    private static final String TAG = BaseSpiceRequest.class.getSimpleName();

    protected Class<RESP> clazzResp;
    protected REQ request;
    protected String service;

    public BaseSpiceRequest(Class<RESP> clazzResp, REQ request, String service) {
        super(clazzResp);
        this.clazzResp = clazzResp;
        this.request = request;
        this.service = service;

        // Just make one communication attempt
        this.setRetryPolicy(null);

        if (request == null)
            L.w(TAG, "Request content empty");
    }

    protected HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "application/json; charset=utf-8");

        return headers;
    }

    protected void processResponseSessionCookie(HttpHeaders responseHeaders) {

        List<String> cookiesHeader = responseHeaders.get("Set-Cookie");
        if (cookiesHeader != null) {
            for (String cookie : cookiesHeader) {
                HttpCookie sessionCookie = HttpCookie.parse(cookie).get(0);
                CommunicationCenter.getCookieManager().getCookieStore()
                        .add(null, sessionCookie);
                L.v(TAG, "New Cookie:" + sessionCookie);
            }
        }

    }

    public String createCacheKey() {
        return clazzResp.getSimpleName() + "/"
                + request.getClass().getSimpleName();
    }

    protected String getUrl() {
        return CommunicationCenter.getBaseUrl() + "/" + service;
    }

}
