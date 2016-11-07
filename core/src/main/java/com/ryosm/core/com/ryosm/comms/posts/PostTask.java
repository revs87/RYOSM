package com.ryosm.core.com.ryosm.comms.posts;

import android.os.AsyncTask;
import android.view.View;

import com.google.gson.Gson;
import com.ryosm.core.com.ryosm.comms.api.Response;
import com.ryosm.core.com.ryosm.utils.L;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.ryosm.core.com.ryosm.base.CoreLauncherActivity.getCore;

/**
 * Created by revs on 16/10/2016.
 */

public class PostTask<RESP> extends AsyncTask<NameValuePair, Void, RESP> {

    private Class<RESP> clazzResp;
    protected String url;
    protected String responseStr;
    protected PostListener postListener = new PostListener() {
        @Override
        public void onSuccess(Object response, String responseStr) {

        }

        @Override
        public void onFail(Exception e) {

        }
    };
    private View loadingView;

    public PostTask(Class<RESP> clazzResp, String url, PostListener postListener, View loadingView) {
        this.clazzResp = clazzResp;
        this.url = url;
        if (postListener != null) {
            this.postListener = postListener;
        }
        this.loadingView = loadingView;
    }

    @Override
    protected RESP doInBackground(NameValuePair... data) {
        // New HttpClient and Post Header
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            // Set missing headers
            httpPost.setHeader(new BasicHeader("Accept", "text/plain"));

            // Data to be added
            List<NameValuePair> nameValuePairs = new ArrayList<>(data.length);
            for (int i = 0; i < data.length; i++) {
                nameValuePairs.add(data[i]);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));


            /**
             * Print request
             * */
            String requestStr = "{";
            for (int i = 0; i < data.length; i++) {
                if (i == 0) {
                    requestStr = requestStr + "\"" + data[i].getName() + "\"" + ":" + "\"" + data[i].getValue() + "\"";
                } else {
                    requestStr = requestStr + ", " + "\"" + data[i].getName() + "\"" + ":" + "\"" + data[i].getValue() + "\"";
                }
            }
            requestStr = requestStr + "}";
            L.d("PostTask", "Request:\n"
//                    + "Encrypted:" + body + "\n"
//                    + "Decrypted:" + responseStrDecrypted
                            + requestStr
            );


            // Execute HTTP Post Request
            HttpResponse response = httpClient.execute(httpPost);

            // Parse response
            Gson gson = new Gson();
            String body = responseStr = EntityUtils.toString(response.getEntity()).trim();
            RESP responseJson = gson.fromJson(body, clazzResp);


            /**
             * Print response
             * */
            String responseStatus = response.getStatusLine() + "";
            String responseStrDecrypted = getCore().getRyoLibsodium().decryptObject((Response) responseJson);
            //((Response) response).setResponseStr(responseStrDecrypted);
            L.d("PostTask", "Response(" + responseStatus + "):\n"
                    + "Encrypted:" + body + "\n"
                    + "Decrypted:" + responseStrDecrypted
            );

            return responseJson;
        } catch (IllegalStateException e) {
            postListener.onFail(e);
        } catch (ClientProtocolException e) {
            postListener.onFail(e);
        } catch (IOException e) {
            postListener.onFail(e);
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        if (loadingView != null) {
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPostExecute(RESP response) {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }

        if (response != null) {
            try {
                postListener.onSuccess(response, responseStr);
            } catch (Exception e) {
                postListener.onFail(e);
            }
        } else {
            postListener.onFail(new Exception());
        }
    }

}
