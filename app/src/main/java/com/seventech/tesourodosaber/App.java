package com.seventech.tesourodosaber;

import android.app.Application;
import android.content.Context;

import com.seventech.tesourodosaber.session.SessionManager;

public class App extends Application {

    private static final String TAG = App.class.getSimpleName();

    private static App sInstance;
    private static SessionManager sSessionManager;


    public static Context getContext() {
        return sInstance;
    }

    public static SessionManager getSessionManager() {
        return sSessionManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sSessionManager = SessionManager.with(sInstance);
    }
}
