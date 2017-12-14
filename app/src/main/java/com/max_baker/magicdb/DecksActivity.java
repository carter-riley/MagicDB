package com.max_baker.magicdb;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DecksActivity extends AppCompatActivity {
    EditText inDeckName ;
    DialogClick dialogClick = new DialogClick();
    String userName;
    AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inDeckName=  new EditText(this);
        ListView listView = new ListView(this);
        final Cursor cursor = ((MyApplication) this.getApplication()).myDb.getDeckCursor(getIntent().getStringExtra("user"));
        List<String> deckNames= new ArrayList<String>();
        userName=getIntent().getStringExtra("user");
        //cursor.moveToFirst();
        //Log.d("this", cursor.getString(0));
        while(cursor.moveToNext())
        {
            deckNames.add(cursor.getString(0));
        }
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                deckNames
        );
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DecksActivity.this, CardListActivity.class);
                cursor.moveToPosition(position);
                intent.putExtra("deckName", cursor.getString(0));
                intent.putExtra("userName", userName);
                startActivity(intent);
            }
        });
        setContentView(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.deck_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void onClickCardList(View view){
        Intent intent = new Intent(this, CardListActivity.class);
        startActivity(intent);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        switch(menuId){
            case R.id.addDeck:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(DecksActivity.this);
                alertBuilder.setTitle("Add Deck");
                alertBuilder.setView(inDeckName);
                alertBuilder.setMessage("");
                alertBuilder.setPositiveButton("add", dialogClick);
                alertBuilder.setNegativeButton("cancel", dialogClick);
                alertBuilder.show();
                return true;
            case R.id.deleteAllDecks:
                ((MyApplication) DecksActivity.this.getApplication()).myDb.deleteAllDecks();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private class DialogClick implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which==DialogInterface.BUTTON_POSITIVE){
                Log.d("yes",userName);
                Log.d("yes",inDeckName.getText().toString());
                ((MyApplication) DecksActivity.this.getApplication()).myDb.AddDeck(userName, inDeckName.getText().toString());
            }
            //Nothing if no
        }
    }
}
