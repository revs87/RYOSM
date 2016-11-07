package com.ryosm.core.com.ryosm.comms.libsodium;

import android.util.Base64;

import com.google.gson.Gson;
import com.ryosm.core.com.ryosm.comms.api.Response;
import com.ryosm.core.com.ryosm.comms.api.requests.RequestLogin;
import com.ryosm.core.com.ryosm.comms.api.requests.RequestLoginObj;
import com.ryosm.core.com.ryosm.comms.api.requests.RequestObject;
import com.ryosm.core.com.ryosm.utils.L;

import org.libsodium.jni.Sodium;
import org.libsodium.jni.crypto.Box;
import org.libsodium.jni.crypto.Random;
import org.libsodium.jni.encoders.Encoder;
import org.libsodium.jni.keys.KeyPair;
import org.libsodium.jni.keys.PrivateKey;
import org.libsodium.jni.keys.PublicKey;

/**
 * Created by revs on 16/10/2016.
 */

public class RyoLibsodium {
    private Sodium sodium;

    // User keys
    private Box identityBox;
    private PublicKey identityPublicKey;
    private PrivateKey identityPrivateKey;

    // Other X user keys
    private Box xBox;
    private PublicKey xPublicKey;

    // Comm keys
    private String serverPublicKeyStr = "ubbweRzp9fw2z6W9mLIwxtr3zb3Q5HcOAMoQA4R1OD0=";
    private Box kpBox;
    private PublicKey serverPublicKey;
    private PublicKey kpPublicKey;
    private PrivateKey kpPrivateKey;

    // Auxiliary
    private Random random;
    private Encoder encoder;


    /**
     * Constructor
     */
    public RyoLibsodium() {
        sodium = new Sodium();

        init();
    }

    /**
     *
     * */
    public RequestLogin getRequestLogin(String username, String password) {
        generateSessionBox();

        RequestLoginObj requestLogin = new RequestLoginObj(
                username,
                password,
                encode64(identityPublicKey.toBytes())
        );

        Gson gson = new Gson();
        RequestLogin requestObjectEncrypted = encryptObject(gson.toJson(requestLogin));

        L.d("RyoLibsodium", gson.toJson(requestLogin));
        L.d("RyoLibsodium", gson.toJson(requestObjectEncrypted));

        return requestObjectEncrypted;
    }

    private void init() {
        random = new Random();
        encoder = Encoder.RAW;

        /**
         * Define Server PublicKey
         * */
        serverPublicKey = new PublicKey(decode64(serverPublicKeyStr.getBytes()));

        /**
         * Create user Box
         * */
        createIdentityBox();
    }

    /**
     * Base tool methods
     */
    public Box createIdentityBox() {
        if (identityBox == null) {
            KeyPair identity = new KeyPair();
            identityPublicKey = identity.getPublicKey();
            identityPrivateKey = identity.getPrivateKey();
            identityBox = new Box(identityPublicKey, identityPrivateKey);
        }
        return identityBox;
    }

    public Box generateSessionBox() {
        KeyPair kp = new KeyPair();
        kpPublicKey = kp.getPublicKey();
        kpPrivateKey = kp.getPrivateKey();
        kpBox = new Box(serverPublicKey, kpPrivateKey);
        return kpBox;
    }

    public Box generateXBox(String xPublicKeyStr) {
        createIdentityBox();
        xPublicKey = new PublicKey(decode64(xPublicKeyStr.getBytes()));
        xBox = new Box(xPublicKey, kpPrivateKey);
        return xBox;
    }

    public byte[] generateNonce() {
        byte[] nonce = random.randomBytes(sodium.crypto_secretbox_noncebytes());
        return nonce;
    }

    public String encode64(byte[] byteCode) {
        return Base64.encodeToString(byteCode, Base64.NO_WRAP);
    }

    public byte[] decode64(String str) {
        return Base64.decode(str, Base64.NO_WRAP);
    }

    public byte[] decode64(byte[] byteCode) {
        return Base64.decode(byteCode, Base64.NO_WRAP);
    }


    /**
     * Comm methods
     */
    public RequestLogin encryptObject(String jsonObject) {
        byte[] nonce = generateNonce();
        byte[] message = kpBox.encrypt(nonce, encoder.decode(jsonObject));
        return new RequestLogin(
                encode64(nonce),
                encode64(message),
                encode64(kpPublicKey.toBytes())
        );
    }

    public String decryptObject(Response responseObject) {
        byte[] message = kpBox.decrypt(
                decode64(responseObject.getNonce()),
                decode64(responseObject.getResponse()));
        return encoder.encode(message);
    }

    public RequestObject encryptXObject(String xPublicKeyStr, String jsonObject) {
        generateXBox(xPublicKeyStr);
        byte[] nonce = generateNonce();
        byte[] message = xBox.encrypt(nonce, encoder.decode(jsonObject));
        return new RequestObject(
                encode64(nonce),
                encode64(message)
        );
    }

    public String decryptXObject(Response responseObject) {
        byte[] message = xBox.decrypt(
                decode64(responseObject.getNonce()),
                decode64(responseObject.getResponse()));
        return encoder.encode(message);
    }

}
