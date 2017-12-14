package com.max_baker.magicdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        ArrayList<String> aggregators = new ArrayList<>();
        aggregators.add("Maximum");
        aggregators.add("Average");
        aggregators.add("Minimum");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,aggregators);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner aggrSpinner = new Spinner(this);
        aggrSpinner.setAdapter(adapter);
        linearLayout.addView(aggrSpinner);

        ArrayList<String> attributes = new ArrayList<>();
        attributes.add("Mana Cost");
        attributes.add("Power");
        attributes.add("Toughness");
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,attributes);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner attrSpinner1 = new Spinner(this);
        attrSpinner1.setAdapter(adapter2);
        linearLayout.addView(attrSpinner1);

        TextView textView = new TextView(this);
        textView.setText("Per");
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(textView);

        ArrayList<String> attributes2 = new ArrayList<>();
        attributes2.add("Mana Cost");
        attributes2.add("Power");
        attributes2.add("Toughness");
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,attributes2);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner attrSpinner2 = new Spinner(this);
        attrSpinner2.setAdapter(adapter3);
        linearLayout.addView(attrSpinner2);

        ArrayList<String> comparators = new ArrayList<>();
        comparators.add("Greater Than");
        comparators.add("Less Than");
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,comparators);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner compSpinner = new Spinner(this);
        compSpinner.setAdapter(adapter4);
        linearLayout.addView(compSpinner);

        ArrayList<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        values.add(3);
        values.add(4);
        values.add(5);
        values.add(6);
        values.add(7);
        values.add(8);
        values.add(9);
        values.add(10);
        ArrayAdapter<Integer> adapter5 = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,values);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner valueSpinner = new Spinner(this);
        valueSpinner.setAdapter(adapter5);
        linearLayout.addView(valueSpinner);

        Button button = new Button(this);
        button.setText("Search");
        button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FactsActivity.this, FactResultActivity.class);
                String aggregate = aggrSpinner.getSelectedItem().toString();
                switch(aggregate){
                    case "Maximum":
                        intent.putExtra("aggregate", "MAX");
                        break;
                    case "Average":
                        intent.putExtra("aggregate", "AVG");
                        break;
                    case "Minimum":
                        intent.putExtra("aggregate", "MIN");
                        break;
                    default:
                        intent.putExtra("aggregate", "MAX");
                }
                intent.putExtra("attr1",attrSpinner1.getSelectedItem().toString());
                intent.putExtra("attr2",attrSpinner2.getSelectedItem().toString());
                intent.putExtra("comparator", compSpinner.getSelectedItem().toString());
                intent.putExtra("value", valueSpinner.getSelectedItem().toString());

                startActivity(intent);
            }
        });
        linearLayout.addView(button);
        setContentView(linearLayout);
    }
}
