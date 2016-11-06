
package com.ryosm.core.com.ryosm.comms.api.requests;

import android.util.Base64;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

public class RequestLogin extends RequestObject {

    private static final long serialVersionUID = 7697330253727352850L;

    @SerializedName("publicKey")
    private String publicKey;


    public RequestLogin(String nonce, String message, String publicKey) {
        super(nonce, message);
        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Base64.decode(json.getAsString(), Base64.NO_WRAP);
    }

    @Override
    public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(Base64.encodeToString(src, Base64.NO_WRAP));
    }
}
