package com.example.testappone;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.ResultReceiver;
import android.os.Bundle;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */


public class EampleIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.testappone.action.FOO";
    private static final String ACTION_BAZ = "com.example.testappone.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.testappone.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.testappone.extra.PARAM2";

    /// My Action
    private static final String ACTION_TEST_ACTION = "test_action";
    private static final String EXTRA_PARAM_TEST = "test_parameter";

    public EampleIntentService() {
        super("EampleIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, EampleIntentService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, EampleIntentService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    ///TODO: my custom action
    public static void startActionTest(Context context, String param){
        Intent intent = new Intent(context, EampleIntentService.class);
        intent.setAction(ACTION_TEST_ACTION);
        intent.putExtra(EXTRA_PARAM_TEST, param);
        Log.d("uttam: startActionTest", "startActionTest: running.. ");
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ResultReceiver receiver = intent.getParcelableExtra("receiver");

        /*Bundle appName = intent.getBundleExtra("name");
        String appNameString = appName.toString();

        Bundle request = intent.getBundleExtra("request");
        String requestType = appName.toString();*/
        Log.d("uttam: onHandleIntent", "onHandleIntent: running.. ");

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                Log.d("uttam: onHandleIntent", "ACTION_FOO: running.. ");
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                Log.d("uttam: onHandleIntent", "ACTION_BAZ: running.. ");
                handleActionBaz(param1, param2);
            }else if (ACTION_TEST_ACTION.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM_TEST);
                Log.d("uttam: onHandleIntent", "ACTION_TEST_ACTION: running.. ");
                Bundle bundle = new Bundle();
                bundle.putString("ret_field","this is return string after counting");
                receiver.send(404, bundle);
                handleActionTest(param1);
            }else {
                for (int i=0;i<10;i++){
                    Log.d("uttam: non action thread", "cout: "+i);
                    try{
                        Thread.sleep(1000);
                    }catch (Exception e){

                    }
                }

                Bundle bundle = new Bundle();
                bundle.putString("ret_field"," EampleIntentService this is return string after counting");
                receiver.send(404, bundle);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
       // throw new UnsupportedOperationException("Not yet implemented");
    }

    private void handleActionTest(String param) {
        // TODO: Handle action Baz
      //  throw new UnsupportedOperationException("Not yet implemented");
    }
}
