package com.ryosm.core.com.ryosm.comms.Retrofit;

import com.ryosm.core.com.ryosm.CommunicationCenter;
import com.ryosm.core.com.ryosm.comms.api.requests.RequestLoginObj;
import com.ryosm.core.com.ryosm.comms.api.responses.ResponseLogin;
import com.ryosm.core.com.ryosm.utils.L;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

/**
 * Created by revs on 16/10/2016.
 */

public class LoginServiceGenerator {

    private static final String URL = CommunicationCenter.ServiceCustom;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(CommunicationCenter.getBaseUrl())
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, RequestLoginObj requestLogin) {

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .header("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
                        .method(original.method(), original.body());

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public interface LoginService {
        @POST(URL)
        Call<ResponseLogin> login();
    }

    public void getLogin() {
        LoginService loginService =
                LoginServiceGenerator.createService(LoginService.class, new RequestLoginObj("", "", ""));
        Call<ResponseLogin> call = loginService.login();
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, retrofit2.Response<ResponseLogin> response) {
                L.d("onResponse", "");
                if (response.isSuccessful()) {
                    // user object available
                } else {
                    // error response, no access to resource?
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                // something went completely south (like no internet connection)
                L.d("onFailure", t.getMessage());
            }
        });
    }
}
