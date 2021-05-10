package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ListView historyList = findViewById(R.id.historyList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getData());
        historyList.setAdapter(arrayAdapter);
    }

    private List<String> getData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        List<String> data = new ArrayList<>();
        data.add("Прошлые посещения:");
        for (Map.Entry<String, ?> pref : preferences.getAll().entrySet()) {
            if (pref.getKey().startsWith("myApp")) {
                data.add(pref.getValue().toString());
            }
            System.out.println(pref.getKey() + " " + pref.getValue());
        }
        return data;
    }
}