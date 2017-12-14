package com.max_baker.magicdb;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CardListActivity extends AppCompatActivity {
    static int ADD_CARD_CODE=321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Cursor cursor = ((MyApplication) this.getApplication()).myDb.GetCardCursor(getIntent().getStringExtra("userName"), getIntent().getStringExtra("deckName"));
        //cursor.moveToFirst();
        final ArrayList<Stat> stats = new ArrayList<>();
        while(cursor.moveToNext())
        {
            stats.add(new Stat(cursor.getString(0),cursor.getString(2)));
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_deck_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        switch(menuId){
            case R.id.addCard:
                Intent intent = new Intent(this, CardSearchCriteria.class);
                intent.putExtra("deckName", getIntent().getStringExtra("deckName"));
                startActivity(intent);
                return true;
            case R.id.deleteAllCards:
                //TODO
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_CARD_CODE && resultCode == Activity.RESULT_OK){
            //TODO
        }
    }
}
