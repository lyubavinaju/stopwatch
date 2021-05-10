package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TextView time;
    private Handler handlerUI = new Handler();
    final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, MyService.class));
        Date date = new Date(System.currentTimeMillis());
        MyService.history.add(formatter.format(date));

        setContentView(R.layout.activity_main);
        time = findViewById(R.id.text);
        Button start = findViewById(R.id.start);
        Button stop = findViewById(R.id.stop);
        Button reset = findViewById(R.id.reset);
        Button history = findViewById(R.id.history);
        handlerUI = new Handler();
        start.setOnClickListener(view -> {
            MyService.running = true;
        });
        stop.setOnClickListener(view -> {
            MyService.running = false;
        });
        reset.setOnClickListener(view -> {
            MyService.running = false;
            MyService.seconds = 0;
        });
        history.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });
        handlerUI.post(new UpdateUI());
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, MyService.class));
        super.onDestroy();
    }

    private class UpdateUI implements Runnable {
        @Override
        public void run() {
            int seconds = MyService.seconds;
            int h = seconds / 3600;
            int m = (seconds % 3600) / 60;
            int s = seconds % 60;
            String result = String.format("%02d:%02d:%02d", h, m, s);
            time.setText(result);
            handlerUI.postDelayed(this, 100);
        }
    }
}