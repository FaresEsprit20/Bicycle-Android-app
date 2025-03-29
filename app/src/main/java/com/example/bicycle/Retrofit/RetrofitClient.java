package com.example.bicycle.Retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

public class RetrofitClient {
    // Local development server (use HTTPS in production!)
    private static final String BASE_URL = "http://10.0.2.2:3000/";

    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient = null;

    // Private constructor to prevent instantiation
    private RetrofitClient() {}

    public static synchronized Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)  // Set connection timeout
                    .readTimeout(30, TimeUnit.SECONDS)     // Set read timeout
                    .writeTimeout(30, TimeUnit.SECONDS)     // Set write timeout
                    .build();
        }
        return okHttpClient;
    }

    // Optional: Clear Retrofit instance (useful for testing)
    public static void resetClient() {
        retrofit = null;
        okHttpClient = null;
    }


}