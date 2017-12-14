package com.max_baker.magicdb;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class CardSearchCriteria extends AppCompatActivity {
    static int SEARCH_CARD=808;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_search_criteria);
    }
    public void goSearch(View view){
        Intent intent = new Intent(this, CardResultActivity.class);
        EditText cardName = (EditText) findViewById(R.id.cardNameSearch);
        CheckBox white = (CheckBox) findViewById(R.id.white);
        CheckBox blue = (CheckBox) findViewById(R.id.blue);
        CheckBox black = (CheckBox) findViewById(R.id.black);
        CheckBox red = (CheckBox) findViewById(R.id.red);
        CheckBox green = (CheckBox) findViewById(R.id.green);
        EditText minCost = (EditText) findViewById(R.id.minCost);
        EditText maxCost = (EditText) findViewById(R.id.maxCost);
        intent.putExtra("name", cardName.getText().toString());
        intent.putExtra("white", white.isChecked());
        intent.putExtra("blue", blue.isChecked());
        intent.putExtra("black", black.isChecked());
        intent.putExtra("red", red.isChecked());
        intent.putExtra("green", green.isChecked());
        intent.putExtra("minCost", minCost.getText().toString());
        intent.putExtra("maxCost", maxCost.getText().toString());
        intent.putExtra("deckName", getIntent().getStringExtra("deckName"));

        startActivity(intent);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SEARCH_CARD && resultCode == Activity.RESULT_OK){
            //TODO
            finish();
        }
    }
}
