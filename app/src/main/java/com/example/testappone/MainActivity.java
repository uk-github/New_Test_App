package com.example.testappone;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.wear.ambient.AmbientModeSupport.AmbientCallbackProvider;

import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends WearableActivity implements LocationListener {

    private TextView mTextView;
    private TextView mCityName;

    Messenger receiveMessenger;
    public static final int REQUEST_CITY_INFO = 0;
    public Intent clientServiceIntent;

    public static final int MINIMUM_DOWN_BANDWIDTH = 320;

    OkHttpClient client;


    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);
        mCityName = (TextView) findViewById(R.id.cityName);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION
            },100);
        }
      //  if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION !=))



        // Enables Always-on
        setAmbientEnabled();
        // intentAddCity();
        City mCityObject = DataManager.GetObjcet().GetCity();
        mCityObject.InsertDummyCityInfo();
        String str = mCityObject.GetCityNameEnglish();
        System.out.print(str);
        mCityName.setText((String) mCityObject.GetCityNameLocal());


        //TODO:  MessageReceiver
        MessageReceiver messageReceiver = new MessageReceiver(new MessageR());


        //TODO:  MyService.class
        Intent ServiceIntent = new Intent(this, MyService.class);
        ServiceIntent.putExtra("receiver", messageReceiver);
        //  startService(ServiceIntent);


        //TODO:  EampleIntentService.class
        Intent intentServiceIntent = new Intent(this, EampleIntentService.class);
        intentServiceIntent.putExtra("name", "intent_service");
        intentServiceIntent.putExtra("request", "UPDATE_DATA");
        intentServiceIntent.putExtra("receiver", messageReceiver);
        // startService(intentServiceIntent);
        //  EampleIntentService.startActionTest(this, "test_action");
        //Toast.makeText(this,)


        clientServiceIntent = new Intent(this, ClientService.class);
        bindService(clientServiceIntent, serviceConnection, BIND_AUTO_CREATE);
        Log.d("uttam: bindService", "bindService: running.. ");
        ///TODO: Connectivity check

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork != null) {
            int bandWidthDown = connectivityManager.getNetworkCapabilities(activeNetwork).getLinkDownstreamBandwidthKbps();
            //int bandWidthUP= connectivityManager.getNetworkCapabilities(activeNetwork).getLinkUpstreamBandwidthKbps();

            if (bandWidthDown > MINIMUM_DOWN_BANDWIDTH) {
                //Ready to request
                Log.d("uttam: Connectivity Check", "down Band width: " + bandWidthDown);
            } else {
                Log.d("uttam: Connectivity Check", "low down band width..");
            }

        } else {
            Log.d("uttam: Connectivity Check", "No active Network available");
        }



        /*
        TODO: sample test link

        "http://publicobject.com/helloworld.txt"
        "http://galaxy.wni.com/api_v2/weather.cgi?lat=23.7436389&lon=90.3747265"
         */
        /*
         client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://galaxy.wni.com/api_v2/weather.cgi?lat=23.7436389&lon=90.3747265")
                .build();
        Log.d("uttam: OkHttpClient ","request Done");
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
              //  Toast.makeText(MainActivity.this, "onresponse failed...", Toast.LENGTH_LONG).show();
                Log.d("uttam: newcall ","onFailure fail");
                e.printStackTrace();

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                   // Toast.makeText(MainActivity.this, "response: " + response.body().toString(), Toast.LENGTH_LONG).show();
                  //  Log.d("uttam: newcall ","response success: " + response.body().string());
                    JSONArray jsonArray;
                    JSONObject jsonObject;
                    String bodystring = response.body().string();
                    Log.d("uttam jsonbody: ", bodystring);
                    try {
                        jsonArray = new JSONArray(bodystring);
                        jsonObject = jsonArray.getJSONObject(0);
                       // jsonArray = new JSONArray(bodystring);
                       // JSONObject newjsObject = new JSONObject(bodystring);
                       new JsonParser().ParseJson(jsonObject);
                   }catch(JSONException e){
                        e.printStackTrace();

                    }

                }else {
                    Log.d("uttam: newcall ","response but something wrong!! ");
                }

            }
        });
        */

        ///TODO: Connectivity check END:::::::::::::::

        ///TODO: Location update api
        getLocation();

    }

    private void getLocation() {
        Log.d("uttam: getLocation Check", "getLocation");
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("uttam: getLocation Check", "permission error");
        }else {
            try {
                if (LocationManager.GPS_PROVIDER == "gps") {
                    Log.d("uttam: getLocation Check", "gps");
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, MainActivity.this);
                } else {
                    Log.d("uttam: getLocation Check", "wps");
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, MainActivity.this);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        Log.d("uttam Locatoin Updated ","Lat: "+location.getLatitude() +"Lon: "+location.getLongitude());
        Toast.makeText(this, "Location: \nLat: "+location.getLatitude() +"\nLon: "+location.getLongitude(),Toast.LENGTH_LONG).show();

    }

    public class MessageR {
        public void displayMessage(int resultCode, Bundle resultData){
               String message = resultData.getString("ret_field");
            Log.d("uttam: ret_result: ", message);
            Toast.makeText(MainActivity.this, resultCode + message, Toast.LENGTH_SHORT).show();

        }
    }


    class ServiceHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {

            switch (msg.what){
                case REQUEST_CITY_INFO:
                {
                    Log.d("uttam: bindService", "bindService: running.. ");
                    Bundle bundle = msg.getData();
                    String cityName = bundle.getString("cityName");
                    String cityWeather = bundle.getString("cityWeather");
                    String cityCurrentTime = bundle.getString("cityCurrentTime");
                    String cityCountryName = bundle.getString("cityCountryName");
                    Log.d("uttam: bindService", cityName+ " \n"+cityWeather+" \n"+cityCurrentTime+"\n"+cityCountryName);
                    Toast.makeText(MainActivity.this,cityName+ " \n"+cityWeather+" \n"+cityCurrentTime+"\n"+cityCountryName,Toast.LENGTH_LONG).show();

                }

            }
            super.handleMessage(msg);
            unbindService(serviceConnection);
            serviceConnection= null;
            receiveMessenger =null;
        }
    }


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            receiveMessenger = new Messenger(new ServiceHandler());
            Message message = Message.obtain(null,REQUEST_CITY_INFO);
            message.replyTo=receiveMessenger;
            try{
                new Messenger(iBinder).send(message);
            } catch (RemoteException e){

            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            receiveMessenger = null;

        }
    };



    public void intentAddCity(View view){
        startActivity(new Intent(this, AddCity.class));
    }




}
