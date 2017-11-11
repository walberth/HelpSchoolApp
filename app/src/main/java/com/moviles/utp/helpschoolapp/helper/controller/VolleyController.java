package com.moviles.utp.helpschoolapp.helper.controller;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Gustavo Ramos M. on 11/11/2017.
 */

public class VolleyController {

    public static final String TAG = VolleyController.class.getSimpleName();
    private RequestQueue volRequestQueue;
    private static VolleyController volInstance;
    private static Context mContext;


    private VolleyController(Context context) {
        mContext = context;
        volRequestQueue = getRequestQueue();
    }

    public static synchronized VolleyController getInstance(Context context) {
        if (volInstance == null) {
            volInstance = new VolleyController(context);
        }
        return volInstance;
    }

    public RequestQueue getRequestQueue() {
        if (volRequestQueue == null)
            volRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        return volRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (volRequestQueue != null) {
            volRequestQueue.cancelAll(tag);
        }
    }
}
