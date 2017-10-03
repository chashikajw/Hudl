package hudlmo.interfaces.registerPage;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.volley.Request;
import android.volley.RequestQueue;
import android.volley.toolbox.Volley;

public class RequestHandelr {
    private static RequestHandelr mInstance;
    private RequestQueue mRequestQueue;

    private static Context mCtx;

    private RequestHandelr(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();


    }

    public static synchronized RequestHandelr getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RequestHandelr(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


}