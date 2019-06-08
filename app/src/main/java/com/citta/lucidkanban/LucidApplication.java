package com.citta.lucidkanban;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.citta.lucidkanban.managers.TaskManager;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by Satya on 2019-06-08.
 */
public class LucidApplication extends Application {

    private Activity mActivity;

    private static LucidApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        TaskManager.getInstance().initialize();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                mActivity = activity;
            }

            @Override
            public void onActivityResumed(Activity activity) {
                mActivity = activity;
                Log.d(TAG, "onActivityResumed:" + activity.getLocalClassName());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                mActivity = null;
            }

            @Override
            public void onActivityStopped(Activity activity) {
                TaskManager.getInstance().saveTask(activity);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public static LucidApplication getInstance() {
        return instance;
    }

}
