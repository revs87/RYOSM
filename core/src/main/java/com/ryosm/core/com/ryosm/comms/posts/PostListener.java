package com.ryosm.core.com.ryosm.comms.posts;

/**
 * Created by revs on 16/10/2016.
 */

public abstract class PostListener<RESP> {

    public PostListener() {
    }

    public abstract void onSuccess(RESP response, String responseStr);

    public abstract void onFail(Exception e);

}



