package com.max_baker.magicdb;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
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
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.api.SetAPI;
import io.magicthegathering.javasdk.resource.Card;

public class MainActivity extends AppCompatActivity {
    DialogClick dialogClick = new DialogClick();
    EditText inUserName ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET}, 1);
            return;
        }
        // ((MyApplication) MainActivity.this.getApplication()).myDb.countCards();


        // APIRequestAsyncTask asyncTask = new APIRequestAsyncTask();
        // asyncTask.execute("magic");
        final Cursor cursor = ((MyApplication) this.getApplication()).myDb.getUserCursor();
        List<String> userNames= new ArrayList<String>();
        while(cursor.moveToNext())
        {
            userNames.add(cursor.getString(0));
        }
        ListView listView = new ListView(this);
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                userNames
        );
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DecksActivity.class);
                cursor.moveToPosition(position);
                intent.putExtra("user", cursor.getString(0));
                startActivity(intent);
            }
        });
        inUserName=  new EditText(this);
        setContentView(listView);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        switch(menuId){
            case R.id.addUser:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setTitle("Add User");
                alertBuilder.setView(inUserName);
                alertBuilder.setMessage("");
                alertBuilder.setPositiveButton("add", dialogClick);
                alertBuilder.setNegativeButton("cancel", dialogClick);
                alertBuilder.show();
                return true;
            case R.id.deleteAllUsers:
                ((MyApplication) MainActivity.this.getApplication()).myDb.deleteAllUsers();
                return true;
            case R.id.goToFacts:
                Intent intent = new Intent(this, FactsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private class DialogClick implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which==DialogInterface.BUTTON_POSITIVE){
                ((MyApplication) MainActivity.this.getApplication()).myDb.AddUser(inUserName.getText().toString());
            }
            //Nothing if no
        }
    }

    private class APIRequestAsyncTask extends AsyncTask<String, Void, Integer> {



        // must override doInBackground()

        @Override

        protected Integer doInBackground(String... strings) {

            // THIS CODE RUNS ON A BACKGROUND THREAD

            // varargs is the ...

            // variable number of arguments

            // treat like an array

            Log.d("async", "doInBackground: HELLO FROM BACKGROUND THREAD");
            ((MyApplication) MainActivity.this.getApplication()).myDb.cardsList = new ArrayList<>();
            for(int i=5000;i<10000;i++){
                Card j = CardAPI.getCard(i);
                Log.d("Retrive card", "going");
                if(j != null) {
                    ((MyApplication) MainActivity.this.getApplication()).myDb.insertCard(j);
                    Log.d("Retrive card", "working");
                }
            }



            try {

                Thread.sleep(2000);

            } catch (InterruptedException e) {

                e.printStackTrace();

            }



            // always assume success!!!

            return 0;

        }



        @Override

        protected void onPostExecute(Integer integer) {

            super.onPostExecute(integer);

            ((MyApplication) MainActivity.this.getApplication()).myDb.insertAllCards();

        }

    }

}
