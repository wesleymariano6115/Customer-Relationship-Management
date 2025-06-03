package com.Aurora_Technologies.crm.api;

import android.content.Context;

import androidx.annotation.NonNull;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ApiClient {
    private static final String BASE_URL = "http://localhost:3000/";
    private static Retrofit retrofit = null;
    private static String jwtToken = null;

    public static void setJwtToken(String token) {
        jwtToken = token;
    }

    public static Retrofit getClient() {
        if (retrofit == null) {

            // Logging interceptor (optional, remove in production)
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(@NonNull Chain chain) throws IOException {
                            Request original = chain.request();
                            Request.Builder requestBuilder = original.newBuilder()
                                    .header("Accept", "application/json");
                            if (jwtToken != null && !jwtToken.isEmpty()) {
                                requestBuilder.header("Authorization", "Bearer " + jwtToken);
                            }
                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        }
                    });

            OkHttpClient client = httpClient.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
