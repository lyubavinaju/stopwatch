package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class MyService extends Service {
    public static int seconds;
    public static boolean running;
    Thread thread;
    SharedPreferences preferences;
    public static Deque<String> history = new ArrayDeque<>();

    public MyService() {
    }

    @Override
    public void onCreate() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        seconds = 0;
        running = false;
        for (Map.Entry<String, ?> pref : preferences.getAll().entrySet()) {
            System.out.println(pref.getKey() + " " + pref.getValue());
        }
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    if (running) {
                        seconds++;
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        thread.interrupt();
        SharedPreferences.Editor editor = preferences.edit();
        for (String h : history) {
            editor.putString("myApp" + System.currentTimeMillis(), h);
        }
        history.clear();
        editor.apply();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}