package com.blesh.demo.bleshapp;

import android.app.Application;

import com.blesh.sdk.di.component.BleshSdkComponent;

/**
 * @author Skylifee7 on 23/06/2017.
 */

public class GlobalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        BleshSdkComponent.init(this);

    }
}
