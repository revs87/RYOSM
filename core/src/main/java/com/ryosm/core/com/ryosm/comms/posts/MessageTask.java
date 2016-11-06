package com.ryosm.core.com.ryosm.comms.posts;

import android.view.View;

import com.ryosm.core.com.ryosm.comms.api.ResponseObject;

import org.apache.http.message.BasicNameValuePair;

/**
 * Created by revs on 16/10/2016.
 */

public class MessageTask<RESP> {
    protected String message;
    protected String nonce;
    protected PostTask<RESP> postTask;
    protected MessageListener messageListener = new NullMessageListener();

    public MessageTask(Class<RESP> clazzResp, String url, String message, String nonce) {
        this.message = message;
        this.nonce = nonce;
        postTaskInit(clazzResp, url, getExtendedPostListener(), null);
    }

    public MessageTask(Class<RESP> clazzResp, String url, String message, String nonce, View loadingView) {
        this.message = message;
        this.nonce = nonce;
        postTaskInit(clazzResp, url, getExtendedPostListener(), loadingView);
    }

    public MessageTask(Class<RESP> clazzResp, String url, String message, String nonce, MessageListener messageListener) {
        this.message = message;
        this.nonce = nonce;
        this.messageListener = messageListener;
        postTaskInit(clazzResp, url, getExtendedPostListener(), null);
    }

    public MessageTask(Class<RESP> clazzResp, String url, String message, String nonce, View loadingView, MessageListener messageListener) {
        this.message = message;
        this.nonce = nonce;
        this.messageListener = messageListener;
        postTaskInit(clazzResp, url, getExtendedPostListener(), loadingView);
    }

    public void postTaskInit(Class<RESP> clazzResp, String url, PostListener postListener, View loadingView) {
        postTask = new PostTask<>(clazzResp, url, postListener, loadingView);
    }

    public void postTaskExecute() {
        postTask.execute(new BasicNameValuePair("message", message), new BasicNameValuePair("nonce", nonce));
    }

    public PostListener getExtendedPostListener() {
        return new PostListener() {
            @Override
            public void onSuccess(Object response, String responseStr) {
                ((ResponseObject) response).setResponseStr(responseStr);
                messageListener.onSuccess((ResponseObject) response);
            }

            @Override
            public void onFail(Exception e) {
                messageListener.onFail(e);
            }
        };
    }

    public interface MessageListener {
        void onSuccess(ResponseObject response);

        void onFail(Exception e);
    }

    public class NullMessageListener implements MessageListener {
        @Override
        public void onSuccess(ResponseObject response) {

        }

        @Override
        public void onFail(Exception e) {

        }
    }
}
