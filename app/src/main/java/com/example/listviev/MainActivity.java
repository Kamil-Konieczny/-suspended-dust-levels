package com.example.listviev;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.graphics.Color.*;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView = (CheckedTextView) view;
                boolean aktualnyWybor = checkedTextView.isChecked();
                StationObj wybor = (StationObj) listView.getItemAtPosition(position);
                wybor.setWybor(aktualnyWybor);
            }
        });
        fillTable();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            Intent intent = new Intent(this, ChoosenStations.class);
            intent.putExtra("stationObjLi",(ArrayList<StationObj>)stationObjList);
            setResult(RESULT_OK, intent);
            startActivityForResult(intent,1);
             finish();
        return super.onOptionsItemSelected(item);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                stationObjList = (ArrayList<StationObj>) data.getSerializableExtra("stationObjList");
            }
        }
    }

    public void fillTable() {

        AsyncTask.execute(new Runnable() {
            ApiConnection apiConnection = new ApiConnection();
            @Override
            public void run() {
                try {

                    apiConnection.connect();
                    stationsList = new ArrayList<>();

                    for(MainPojo main: apiConnection.stations)
                    {
                        int id = main.getId();
                       SensorsPojo sensorsPojo = apiConnection.getSensor(id);
                       int sensorId = sensorsPojo.getId();
                       String paramCode = sensorsPojo.getParam().getParamCode();
                        Double vvalue;
                       if(paramCode.equals("PM10"))
                       {

                          List<Value> value = apiConnection.getData(sensorId).getValues(0);
                          vvalue = value.get(0).getValue();
                          int i=0;
                          if(vvalue == null)
                          {
                              do {
                                  i++;
                                  vvalue = value.get(i).getValue();
                              }
                              while (vvalue == null);
                          }


                          String name = main.getStationName();
                        stationObjList.add(new StationObj(name,vvalue));
                       }
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_checked, stationObjList);
                            listView.setAdapter(arrayAdapter);
                            zaznacz();
                        }
                    });
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }
    List<StationObj> getArray()
    {
        return stationObjList;
    }
    public void zapisz(View view) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Set<String> kodyStacji = new HashSet<>();
        for (StationObj station : stationObjList) {
            if (station.isWybor()) {
                kodyStacji.add(station.getName());
            }
        }
        pref.edit().putStringSet("nazwaStacji", kodyStacji).apply();
    }
    private void zaznacz() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Set<String> nazwaStacji = pref.getStringSet("nazwaStacji", new HashSet<String>());
        for (int i = 0; i < stationObjList.size(); i++) {
            StationObj station = stationObjList.get(i);
            if (nazwaStacji.contains(station.getName())) {
                station.setWybor(true);
                listView.setItemChecked(i, true);

            }
        }
    }
    List<StationObj> stationObjList = new ArrayList<>();;
    List<MainPojo> stationsList;
}
