
package com.ryosm.core.com.ryosm.comms.RoboSpice;

import android.content.Intent;
import android.net.Uri;

import com.google.gson.Gson;
import com.ryosm.core.com.ryosm.Configs;
import com.ryosm.core.com.ryosm.utils.L;
import com.ryosm.core.com.ryosm.base.CoreLauncherActivity;
import com.ryosm.core.com.ryosm.objects.KeyValueObject;
import com.ryosm.core.com.ryosm.comms.api.ResponseObject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PostBaseSpiceRequest<REQ, RESP extends ResponseObject> extends
        BaseSpiceRequest<REQ, RESP> {

    private static final String TAG = PostBaseSpiceRequest.class
            .getSimpleName();

    public static int timeoutConnection;
    public static int proxyPort;
    public static String proxyHost;
    public static boolean hasProxy;
    private List<KeyValueObject> extraHeaders;
    private String logString = null;
    private boolean isVisibleHuman = false;

    public PostBaseSpiceRequest(Class<RESP> clazzResp, REQ request,
                                String service) {
        super(clazzResp, request, service);
    }

    public PostBaseSpiceRequest(Class<RESP> clazzResp, REQ request,
                                String service, List<KeyValueObject> extraHeaders) {
        super(clazzResp, request, service);
        this.extraHeaders = extraHeaders;
    }

    @Override
    public RESP loadDataFromNetwork() throws Exception {

        Gson gson = new Gson();

        // Create standard headers
        HttpHeaders requestHeaders = createHttpHeaders();

        if (extraHeaders != null) {
            for (KeyValueObject header : extraHeaders) {
                requestHeaders.add(header.getKey(), header.getValue());
            }
        }

        L.d(TAG, requestHeaders.toString());

        // Genereate full URL
        String url = getUrl();
        L.d(TAG, "Request URL: \n" + url);

        // Begin request, generate HttpEntity request
        L.d(TAG, "Request content: \n" + gson.toJson(request));
        if (Configs.LOG_TO_FILE) {
            Date now = new Date(); // java.util.Date, NOT java.sql.Date or
            // java.sql.Timestamp!
            String format1 = new SimpleDateFormat("HH:mm:ss.SSS").format(now);

            logString = format1 + ";" + "REQUEST" + ";" + url + ";" + requestHeaders.toString()
                    + ";"
                    + gson.toJson(request);
            appendLog(logString);
        }

        HttpEntity<String> requestWithHeaders = new HttpEntity<String>(
                gson.toJson(request), requestHeaders);

        // Exchange and receive HttpEntity response
        HttpEntity<String> response = (HttpEntity<String>) getRestTemplate()
                .exchange(url, HttpMethod.POST, requestWithHeaders,
                        String.class);

        // Save new session cookie
        processResponseSessionCookie(response.getHeaders());

        // Get response
        String body = response.getBody();
        L.d(TAG, "Response URL: \n"
                + url + "\n"
                + "Response Body: \n"
                + body);
        if (Configs.LOG_TO_FILE) {
            Date now = new Date(); // java.util.Date, NOT java.sql.Date or
            // java.sql.Timestamp!
            String format1 = new SimpleDateFormat("HH:mm:ss.SSS").format(now);
            // appendLog("[" + format1 + " | ]RESPONSE: " + url + "\n" + body);

            logString = format1 + ";" + "RESPONSE" + ";" + url + ";" + null + ";"
                    + body;
            appendLog(logString);
        }

        boolean addError = false;
        if (addError) {
            body = "sd8g5sdfg89s56dfg9sd[{]}6{][";
        }

        RESP responseObject = gson.fromJson(body, clazzResp);

        /**
         * Decrypt layer
         * */
        //TODO
//        if (!responseObject.getStatus().equals(ResponseObject.OK)) {

        L.d(TAG, "Response content: \n" + gson.toJson(responseObject));

        return responseObject;

    }

    public void appendLog(String text) {
        if (Configs.LOG_TO_FILE) {

            File directory = new File("sdcard/standardbank/");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File logFile = new File("sdcard/standardbank/log.csv");
            if (!logFile.exists()) {
                try {
                    logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                // BufferedWriter for performance, true to set append to file
                // flag
                BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                buf.append(text);
                buf.newLine();
                buf.close();
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(logFile.getAbsoluteFile()));
                CoreLauncherActivity.getContext().sendBroadcast(intent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
