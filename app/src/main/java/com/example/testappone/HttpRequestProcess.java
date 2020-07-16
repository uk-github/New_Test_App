package com.example.testappone;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequestProcess {

    private String url ;
    Bundle resultBundle;

    public void setUrl(String url){ this.url = url; }
    public String getUrl(){ return url; }


    private OkHttpClient client;

    public Bundle sendRequest(){
        client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
       // Bundle bundle= new Bundle();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                resultBundle.putString("result", response.body().string());
                Log.d("uttam: sendRequest ","response should be successfull");
            }
        });
        Log.d("uttam: sendRequest ","response should be successfull");
        return resultBundle;
    }
}
