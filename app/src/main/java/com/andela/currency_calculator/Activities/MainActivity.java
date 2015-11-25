package com.andela.currency_calculator.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.currency_calculator.R;
import com.andela.currency_calculator.models.Currency.Rate;
import com.andela.currency_calculator.models.dal.EchangeRateAPICollection;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private URL url;
    private Spinner spinner;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ON CREATE", "what happens");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeBaseSpinner();
        intializeTargetSpinner();
         EchangeRateAPICollection exchangeRateAPICollection = new EchangeRateAPICollection();
         Rate rate = new Rate("USD", "NGN");
        exchangeRateAPICollection.execute(rate);


    }

    public void initializeBaseSpinner() {
        spinner = (Spinner) findViewById(R.id.base_currency_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currency_code, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    public void intializeTargetSpinner() {
        spinner = (Spinner) findViewById(R.id.target_currency_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currency_code, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ON STOP", "what happens");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ON DESTROY", "what happens");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ON PAUSE", "what happens");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ON RESUME", "what happens");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ON START", "what happens");
    }
}
