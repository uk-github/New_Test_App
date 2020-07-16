package com.example.testappone;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ClientService extends Service {
    public ClientService() {
    }

    public static final int REQUEST_NONE = 0;
    public static final int REQUEST_ONE = 1;
    public static final int REQUEST_TWO = 2;
    public static final int REQUEST_THREE = 3;
    public static final int REQUEST_FOUR = 4;
    public static final int REQUEST_INVALID = 5;


    private class ServiceHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {

            switch (msg.what){
                case REQUEST_NONE:
                    Message messageSender = Message.obtain(null, REQUEST_NONE);

                    /*
                    this http request is asynchronous call, so it's main loop is different from it, so it doesn't return the fetched data with msg.reply.send api
                     */

                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://galaxy.wni.com/api_v2/weather.cgi?lat=23.7436389&lon=90.3747265")
                            .build();

                    okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful()){
                                String bodystring = response.body().string();
                                Log.d("uttam: onResponse ", bodystring);
                                try {
                                    JSONArray jsonArray = new JSONArray(bodystring);
                                    JSONObject jsonObject = (JSONObject)jsonArray.getJSONObject(0);
                                    new JsonParser().ParseJson(jsonObject);
                                    Log.d("uttam: onResponse ", "response and parsing done");
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }

                        }
                    });
                    /*
                    it doesn't return fetched data. the following line will be executed before coming response
                     */
                    WeatherDataSharer weatherDataSharer = new WeatherDataSharer();
                    // Bundle resultBundle = getBundleData();
                    Bundle resultBundle = weatherDataSharer.MakeWeatherBundle(new Bundle());
                    messageSender.setData(resultBundle);
                    try {
                        msg.replyTo.send(messageSender);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
            }
            super.handleMessage(msg);
        }
    }

    private Messenger messenger = new Messenger(new ServiceHandler());


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return messenger.getBinder();
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    private Bundle getBundleData(){

        for (int i=0; i<10; i++){
            Toast.makeText(this, "Downloading "+i*10+"%", Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {

            }

        }
        Bundle bundle = new Bundle();
        bundle.putString("cityID","10009");
        bundle.putString("cityName","Dhaka");
        bundle.putString("cityLat","100.09");
        bundle.putString("cityLon","98.0923");
        bundle.putString("cityWeather","Rainy");
        bundle.putString("cityCurrentTime","10:10");
        return bundle;
    }
}
