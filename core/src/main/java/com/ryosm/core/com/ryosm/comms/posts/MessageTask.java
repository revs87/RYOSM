package com.ryosm.core.com.ryosm.comms.posts;

import android.view.View;

import com.ryosm.core.com.ryosm.comms.api.Response;
import com.ryosm.core.com.ryosm.core.Core;

import org.apache.http.message.BasicNameValuePair;

/**
 * Created by revs on 16/10/2016.
 */

public class MessageTask<RESP> {

    protected Core core;
    protected String message;
    protected String nonce;
    protected PostTask<RESP> postTask;
    protected MessageListener messageListener = new NullMessageListener();

    public MessageTask(Core core, Class<RESP> clazzResp, String url, String message, String nonce) {
        this.core = core;
        this.message = message;
        this.nonce = nonce;
        postTaskInit(clazzResp, url, getExtendedPostListener(), null);
    }

    public MessageTask(Core core, Class<RESP> clazzResp, String url, String message, String nonce, View loadingView) {
        this.core = core;
        this.message = message;
        this.nonce = nonce;
        postTaskInit(clazzResp, url, getExtendedPostListener(), loadingView);
    }

    public MessageTask(Core core, Class<RESP> clazzResp, String url, String message, String nonce, MessageListener messageListener) {
        this.core = core;
        this.message = message;
        this.nonce = nonce;
        this.messageListener = messageListener;
        postTaskInit(clazzResp, url, getExtendedPostListener(), null);
    }

    public MessageTask(Core core, Class<RESP> clazzResp, String url, String message, String nonce, View loadingView, MessageListener messageListener) {
        this.core = core;
        this.message = message;
        this.nonce = nonce;
        this.messageListener = messageListener;
        postTaskInit(clazzResp, url, getExtendedPostListener(), loadingView);
    }

    public void postTaskInit(Class<RESP> clazzResp, String url, PostListener postListener, View loadingView) {
        postTask = new PostTask<>(getCore(), clazzResp, url, postListener, loadingView);
    }

    public void postTaskExecute() {
        postTask.execute(new BasicNameValuePair("message", message), new BasicNameValuePair("nonce", nonce));
    }

    public PostListener getExtendedPostListener() {
        return new PostListener() {
            @Override
            public void onSuccess(Object response, String responseStr) {
                ((Response) response).setResponseStr(responseStr);
                messageListener.onSuccess((Response) response);
            }

            @Override
            public void onFail(Exception e) {
                messageListener.onFail(e);
            }
        };
    }

    public interface MessageListener {
        void onSuccess(Response response);

        void onFail(Exception e);
    }

    public class NullMessageListener implements MessageListener {
        @Override
        public void onSuccess(Response response) {

        }

        @Override
        public void onFail(Exception e) {

        }
    }

    public Core getCore() {
        return core;
    }

    public void setCore(Core core) {
        this.core = core;
    }

}
