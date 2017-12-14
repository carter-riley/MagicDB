package com.max_baker.magicdb;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FactResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String aggr = intent.getStringExtra("aggregate");
        String att1 = intent.getStringExtra("attr1");
        String att2 = intent.getStringExtra("attr2");
        String comp = intent.getStringExtra("comparator");
        String value = intent.getStringExtra("value");
        switch(att1){
            case "Mana Cost":
                att1 = "cost";
                break;
            case "Power":
                att1 = "power";
                break;
            case "Toughness":
                att1 = "toughness";
                break;
            default:
                att1 = "toughness";
        }
        switch(att2){
            case "Mana Cost":
                att2 = "cost";
                break;
            case "Power":
                att2 = "power";
                break;
            case "Toughness":
                att2 = "toughness";
                break;
            default:
                att1 = "cost";
        }
        switch(comp){
            case "Greater Than":
                comp = ">";
                break;
            case "Less Than":
                comp = "<";
                break;
            default:
                comp = "<";
        }

        Cursor cursor = ((MyApplication) this.getApplication()).myDb.GetComplexQuery(aggr,att1,att2,comp,value);
        cursor.moveToFirst();
        Log.d("sdgsdf",cursor.getString(0));
        final ArrayList<Stat> stats = new ArrayList<>();
        while(cursor.moveToNext())
        {
            stats.add(new Stat(cursor.getString(0),cursor.getString(1)));
        }
        ListView listView = new ListView(this);
        ArrayAdapter<Stat> arrayAdapter= new ArrayAdapter<Stat>(this, android.R.layout.simple_list_item_2,
                android.R.id.text1,
                stats) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(stats.get(position).getStat1());
                text2.setText(stats.get(position).getStat2());
                return view;
            }
        };
        listView.setAdapter(arrayAdapter);
        setContentView(listView);
    }
}
