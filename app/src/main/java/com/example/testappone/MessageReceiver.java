package com.example.testappone;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;



import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallbacks;

@SuppressLint("ParcelCreator")
public class MessageReceiver extends ResultReceiver {
    private MainActivity.MessageR message;
    public MessageReceiver(MainActivity.MessageR message){
        super(new Handler());
        this.message = message;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        message.displayMessage(resultCode,resultData);
    }
}