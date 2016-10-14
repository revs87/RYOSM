package com.ryosm.core.com.ryosm.comms.RoboSpice;

import com.ryosm.core.com.ryosm.Configs;
import com.ryosm.core.com.ryosm.utils.Security.TLSSocketFactory;
import com.ryosm.core.com.ryosm.utils.Utils;

import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;


public class SimpleClientHttpSSLPinningRequestFactory extends SimpleClientHttpRequestFactory {

    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod)
            throws IOException {

        try {
            HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
//            TrustManager tm[] = {new PubKeyManager()};
            TrustManager tm[] = {};
            if (Utils.isBuildVersionOnInterval_16_19()) {
                TLSSocketFactory tlsSocketFactory = new TLSSocketFactory(tm);
                httpsConnection.setSSLSocketFactory(tlsSocketFactory);
            } else {
                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null, tm, null);
                httpsConnection.setSSLSocketFactory(context.getSocketFactory());
            }
        } catch (NoSuchAlgorithmException e) {
            if (!Configs.HIDE_VERBOSE_LOGGING) {
                throw new IOException("NoSuchAlgorithmException", e);
            } else {
                throw new IOException("", e);
            }
        } catch (KeyManagementException e) {
            if (!Configs.HIDE_VERBOSE_LOGGING) {
                throw new IOException("KeyManagementException", e);
            } else {
                throw new IOException("", e);
            }
        }

        super.prepareConnection(connection, httpMethod);
    }
}
