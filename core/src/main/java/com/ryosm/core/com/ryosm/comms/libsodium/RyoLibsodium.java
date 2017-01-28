package com.ryosm.core.com.ryosm.comms.libsodium;

import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.ryosm.core.com.ryosm.comms.api.Response;
import com.ryosm.core.com.ryosm.comms.api.requests.RequestLogin;
import com.ryosm.core.com.ryosm.comms.api.requests.RequestLoginObj;
import com.ryosm.core.com.ryosm.comms.api.requests.RequestObject;
import com.ryosm.core.com.ryosm.core.Core;
import com.ryosm.core.com.ryosm.utils.L;

import org.libsodium.jni.Sodium;
import org.libsodium.jni.crypto.Box;
import org.libsodium.jni.crypto.Hash;
import org.libsodium.jni.crypto.Random;
import org.libsodium.jni.crypto.SecretBox;
import org.libsodium.jni.encoders.Encoder;
import org.libsodium.jni.keys.KeyPair;
import org.libsodium.jni.keys.PrivateKey;
import org.libsodium.jni.keys.PublicKey;

/**
 * Created by revs on 16/10/2016.
 */

public class RyoLibsodium {

    private Sodium sodium;
    private Core core;

    // User keys
    private SecretBox secretBox;
    private Box identityBox;
    private PublicKey identityPublicKey;
    private PrivateKey identityPrivateKey;
    private String password;

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
    private Hash hash;
    private Random random;
    private Encoder encoder;


    /**
     * Constructor
     *
     * @param core
     */
    public RyoLibsodium(Core core) {
        this.core = core;
        this.sodium = new Sodium();

        init();
    }

    /**
     *
     * */
    public RequestLogin getRequestLogin(String username, String password) {
        createIdentityBox(password);
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
//        createIdentityBox(password);
    }

    /**
     * Base tool methods
     */
    public Box createIdentityBox(String password) {
        if (identityBox == null) {
            if (core.getPreferences().getSecret() == null) {

                KeyPair identity = new KeyPair();
                identityPublicKey = identity.getPublicKey();
                identityPrivateKey = identity.getPrivateKey();
                identityBox = new Box(identityPublicKey, identityPrivateKey);

                /*
                * Save and protect privateKey
                * */
                String fullEncryptedObjStr = null;
                try {
                    fullEncryptedObjStr = exportKey(password);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                core.getPreferences().setSecret(fullEncryptedObjStr);
                core.getPreferences().setPublicKey(encode64(identityPublicKey.toBytes()));

            } else {

                /*
                * Load protected privateKey
                * */
                byte[] privateKey = new byte[sodium.crypto_secretbox_keybytes()];
                try {
                    privateKey = importKey(password, core.getPreferences().getSecret());
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                identityPrivateKey = new PrivateKey(privateKey);
                identityPublicKey = new PublicKey(decode64(core.getPreferences().getPublicKey()));
                identityBox = new Box(identityPublicKey, identityPrivateKey);

////TODO
//                byte[] seed;
//                KeyPair identity = new KeyPair(seed);
//                identityPublicKey = identity.getPublicKey();
//                identityPrivateKey = identity.getPrivateKey();
//                identityBox = new Box(identityPublicKey, identityPrivateKey);
            }
        }
        Log.d("createIdentityBox", core.getPreferences().getSecret());
        return identityBox;
    }

    public SecretBox createSecretBox() {
        secretBox = new SecretBox(identityPrivateKey.toBytes());
        return secretBox;
    }

    public String exportKey(String password) {
        createSecretBox();

        byte[] seed = identityPrivateKey.toBytes();
        String seedStr = encode64(seed);
        String obj = "{\"Seed\":\"" + seedStr + "\"}";
        byte[] salt = generateSalt();
        byte[] nonce = generateNonce();
        byte[] passwd = decode64(password);

        byte[] secretKey = new byte[sodium.crypto_secretbox_keybytes()];
        sodium.crypto_pwhash_scryptsalsa208sha256(
                secretKey,
                sodium.crypto_secretbox_keybytes(),
                passwd,
                passwd.length,
                salt,
                sodium.crypto_pwhash_scryptsalsa208sha256_opslimit_interactive(),
                sodium.crypto_pwhash_scryptsalsa208sha256_memlimit_interactive());

//        String secretKeyStr = hash.pwhash_scryptsalsa208sha256(
//                password,
//                encoder,
//                salt,
//                sodium.crypto_pwhash_scryptsalsa208sha256_opslimit_interactive(),
//                sodium.crypto_pwhash_scryptsalsa208sha256_memlimit_interactive());

        byte[] encryptedObj = new byte[sodium.crypto_secretbox_keybytes()];
        sodium.crypto_secretbox_easy(
                encryptedObj,
                decode64(obj),
                obj.length(),
                nonce,
                secretKey);

//        encryptedObj = secretBox.encrypt(nonce, decode64(obj));

        String fullEncryptedObjStr = "{\"Key\":\"" + encode64(encryptedObj) + "\","
                + " \"Salt\":\"" + encode64(salt) + "\","
                + " \"Nonce\":\"" + encode64(nonce) + "\"}";

        L.d("exportKey", fullEncryptedObjStr);
        return fullEncryptedObjStr;
    }

    public byte[] importKey(String password, String fullEncryptedObjStr) {

        //TODO parse Key, Salt and Nonce
        byte[] salt = generateSalt();
        byte[] nonce = generateNonce();

        byte[] passwd = decode64(password);

        byte[] secretKey = new byte[sodium.crypto_secretbox_keybytes()];
        sodium.crypto_pwhash_scryptsalsa208sha256(
                secretKey,
                sodium.crypto_secretbox_keybytes(),
                passwd,
                passwd.length,
                salt,
                sodium.crypto_pwhash_scryptsalsa208sha256_opslimit_interactive(),
                sodium.crypto_pwhash_scryptsalsa208sha256_memlimit_interactive());

        byte[] obj = new byte[sodium.crypto_secretbox_keybytes()];
        sodium.crypto_secretbox_open_easy(
                obj,
                decode64(fullEncryptedObjStr),
                sodium.crypto_secretbox_keybytes(),
                nonce,
                secretKey);

        String seed = encode64(obj);
        L.d("importKey", seed);

        return new byte[sodium.crypto_secretbox_keybytes()];
    }


    //    function getIdentity(obj){
//        var password = $('#password').val();
//        var salt = sodium.from_base64(obj['Salt']);
//        var nonce = sodium.from_base64(obj['Nonce']);
//        var secretKey = sodium.crypto_pwhash(sodium.crypto_secretbox_KEYBYTES,password,salt,sodium.crypto_pwhash_OPSLIMIT_INTERACTIVE,sodium.crypto_pwhash_MEMLIMIT_INTERACTIVE,sodium.crypto_pwhash_ALG_ARGON2I13);
//        try{
//            var key = sodium.crypto_secretbox_open_easy(sodium.from_base64(obj['Key']),nonce,secretKey);
//        } catch (e){
//            $('#status').html("Status: Your password is incorrect or your identity file has been tampered with.");
//            defer_key.resolve();
//            return;
//        }
//        var objid = JSON.parse(sodium.to_string(key));
//        identity = sodium.crypto_box_seed_keypair(sodium.from_base64(objid['Seed']));
//        id_loaded = 1;
//        $('#status').html("Status: Identity successfully decrypted.");
//        defer_key.resolve();
//    }

    public Box generateSessionBox() {
        KeyPair kp = new KeyPair();
        kpPublicKey = kp.getPublicKey();
        kpPrivateKey = kp.getPrivateKey();
        kpBox = new Box(serverPublicKey, kpPrivateKey);
        return kpBox;
    }

    public Box generateXBox(String xPublicKeyStr) {
        createIdentityBox(password);
        xPublicKey = new PublicKey(decode64(xPublicKeyStr.getBytes()));
        xBox = new Box(xPublicKey, kpPrivateKey);
        return xBox;
    }

    public byte[] generateNonce() {
        byte[] nonce = random.randomBytes(sodium.crypto_secretbox_noncebytes());
        return nonce;
    }

    public byte[] generateSalt() {
        byte[] salt = random.randomBytes(sodium.crypto_pwhash_scryptsalsa208sha256_saltbytes());
        return salt;
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
