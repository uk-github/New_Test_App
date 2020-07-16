package com.example.testappone;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class MyService extends Service {
    public MyService() {
    }

    private Looper serviceLooper;
    private ServiceHandler serviceHandler;
    ResultReceiver receiver;
    private final class ServiceHandler extends Handler{
        public ServiceHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            for (int i=0;i<10;i++){
                Log.d("uttam: MyService  thread", "cout: "+i);
                try{
                    Bundle bundle = new Bundle();
                    String str = "MyService Downloading " + i*10 +"%";
                    bundle.putString("ret_field", str);
                    receiver.send(100, bundle);
                    Thread.sleep(1000);
                }catch (Exception e){

                }
            }

            Bundle bundle = new Bundle();
            bundle.putString("ret_field"," MyService Download complete");
            receiver.send(404, bundle);

            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        HandlerThread thread = new HandlerThread("ServiceStartArgument", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        serviceLooper=thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
        Toast.makeText(this, "service creating .. ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String str = "service starting .. ";
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
         receiver = intent.getParcelableExtra("receiver");


        Message msg = serviceHandler.obtainMessage();
        msg.arg1=startId;
        serviceHandler.sendMessage(msg);

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        String str = "service onBind .. ";
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
        receiver = intent.getParcelableExtra("receiver");


        Message msg = serviceHandler.obtainMessage();
       // msg.arg1=startId;
        serviceHandler.sendMessage(msg);

       // return START_STICKY;
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
       Toast.makeText(this, "service destroying ..", Toast.LENGTH_LONG).show();
       Log.d("uttam: Myservice: ", "service destroying ");
       //stopSelf();
        super.onDestroy();
    }
}
