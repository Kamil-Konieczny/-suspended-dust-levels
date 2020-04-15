package com.example.listviev;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ChoosenStations extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosen_main);
        listView2 = findViewById(R.id.listView2);
        Intent intent = getIntent();
        context = this;
        stationObjLi = (ArrayList)intent.getIntegerArrayListExtra("stationObjLi") ;
        System.out.println(stationObjLi);
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

        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent,1);
        finish();
        return super.onOptionsItemSelected(item);

    }
    void fillTable()
    {
        List<StationObj> choosenStations = new ArrayList<>();
        for(StationObj obj: stationObjLi)
        {
            if(obj.isWybor()==true)
            {
                choosenStations.add(obj);
            }
        }
        Adapter = (ArrayAdapter)new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, choosenStations);
        listView2.setAdapter(Adapter);
    }


    ArrayAdapter Adapter;
    ListView listView2;
    Context context;
   private List<StationObj> stationObjLi;
}
