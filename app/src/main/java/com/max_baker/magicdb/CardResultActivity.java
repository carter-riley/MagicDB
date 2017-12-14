package com.max_baker.magicdb;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CardResultActivity extends AppCompatActivity {
    EditText inQuantity;
    DialogClick dialogClick = new DialogClick();
    String deckName;
    String cardName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inQuantity = new EditText(this);
        Intent intent = getIntent();
        setContentView(R.layout.activity_card_result);
        String minCost = intent.getStringExtra("minCost");
        String maxCost = intent.getStringExtra("maxCost");
        if(minCost == null || maxCost == null||minCost==""||maxCost==""){
            Log.d("null", minCost);
            Log.d("null", maxCost);
            minCost="0";
            maxCost="15";
        }
        final Cursor cursor = ((MyApplication) this.getApplication()).myDb.GetCardResults(intent.getStringExtra("name"),intent.getBooleanExtra("white", false),
                intent.getBooleanExtra("blue",false),intent.getBooleanExtra("black",false),intent.getBooleanExtra("red",false),intent.getBooleanExtra("green",false),
                minCost, maxCost);
        ListView listView = new ListView(this);
        final List<String> cardNames= new ArrayList<String>();
        while(cursor.moveToNext())
        {
            cardNames.add(cursor.getString(0));
        }
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                cardNames
        );
        listView.setAdapter(arrayAdapter);
        deckName = getIntent().getStringExtra("deckName");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cursor.moveToPosition(i);
                cardName = cursor.getString(0);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(CardResultActivity.this);
                alertBuilder.setTitle("Add How Many?");
                alertBuilder.setView(inQuantity);
                alertBuilder.setMessage("");
                alertBuilder.setPositiveButton("add", dialogClick);
                alertBuilder.setNegativeButton("cancel", dialogClick);
                alertBuilder.show();
            }
        });
        setContentView(listView);    }

    private class DialogClick implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which==DialogInterface.BUTTON_POSITIVE){
                ((MyApplication) CardResultActivity.this.getApplication()).myDb.AddCard(cardName,deckName,inQuantity.getText().toString());
                finish();
            }
            //Nothing if no
        }
    }
}
