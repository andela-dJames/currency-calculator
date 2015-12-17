package com.andela.currencycalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.andela.currencycalculator.models.currency.Observer;

public class Main2Activity extends AppCompatActivity {
    private TextView baseCurrency, targetCurrency, expressionText,resultText;
    private Observer observer;

    private Spinner baseSpinner;

    private Spinner targetSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeScreens();
        initializeBaseSpinner();
        intializeTargetSpinner();

    }

    public void initializeScreens(){
        baseCurrency = (TextView) findViewById(R.id.base_screen);
        targetCurrency = (TextView) findViewById(R.id.target_screen);
        expressionText = (TextView) findViewById(R.id.expression_screen);
        resultText = (TextView) findViewById(R.id.evaluation_screen);

    }

    public void initializeBaseSpinner() {

        baseSpinner = (Spinner) findViewById(R.id.base_curry);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_code, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_gallery_item);

        baseSpinner.setAdapter(adapter);

        //rate.setBaseCurrency((String) adapter.getItem(0));

        baseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item =  parent.getSelectedItem().toString();
//                rate.setBaseCurrency(item);
//                rate = fetch(rate);
//                baseCurrency.setText(rate.getBaseCurrency());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void intializeTargetSpinner() {

        targetSpinner = (Spinner) findViewById(R.id.target_currency_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_code, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_gallery_item);

        targetSpinner.setAdapter(adapter);

        //rate.setTargetCurrency(((String) adapter.getItem(0)));

        targetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getSelectedItem().toString();

//                rate.setTargetCurrency(item);
//                rate = fetch(rate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
