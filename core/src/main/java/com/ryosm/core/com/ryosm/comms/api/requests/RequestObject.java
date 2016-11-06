
package com.ryosm.core.com.ryosm.comms.api.requests;

import android.util.Base64;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Type;

public class RequestObject implements JsonSerializer<byte[]>, JsonDeserializer<byte[]>, Serializable {

    private static final long serialVersionUID = 7697330253727352850L;

    @SerializedName("nonce")
    private String nonce;
    @SerializedName("message")
    private String message;

    public RequestObject(String nonce, String message) {
        this.nonce = nonce;
        this.message = message;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
