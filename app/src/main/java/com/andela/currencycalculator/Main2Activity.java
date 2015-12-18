package com.andela.currencycalculator;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.andela.currencycalculator.keypad.DecimalPointKeypad;
import com.andela.currencycalculator.keypad.DeleteButton;
import com.andela.currencycalculator.keypad.EqualityKeyPad;
import com.andela.currencycalculator.keypad.KeyPadButton;
import com.andela.currencycalculator.keypad.KeyZero;
import com.andela.currencycalculator.keypad.NumberKeyPad;
import com.andela.currencycalculator.keypad.OperatorButton;
import com.andela.currencycalculator.models.currency.Observer;
import com.andela.currencycalculator.models.currency.Rate;
import com.andela.currencycalculator.models.dal.FetchTask;
import com.andela.currencycalculator.models.dal.SqlLiteDataAccess;
import com.andela.currencycalculator.parcer.Parser;
import com.andela.currencycalculator.parcer.exception.EvaluationException;
import com.andela.currencycalculator.parcer.exception.ParserException;
import com.andela.currencycalculator.parcer.expressionnodes.ExpressionNode;

import java.util.Stack;

import jonathanfinerty.once.Once;

import static jonathanfinerty.once.Once.beenDone;
import static jonathanfinerty.once.Once.markDone;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "Main Activity";

    private String installDB = "INSTALL DATABASE";

    private String updateDB = "UPDATE DATABASE";

    private TextView baseCurrency, targetCurrency, expressionText,resultText;
    private Observer observer;

    private Spinner baseSpinner;

    private Spinner targetSpinner;
    private TextView buttonZero, button_1, button_2, button_3, button_4,
            button_5, button_6, button_7, button_8, button_9, buttonDelete,
            buttonDivision, buttonMultiply, buttonMinus, buttonPlus, buttonDecimal,
            buttonEvaluator, buttonClear;

    private Stack<String> inputBuffer;
    private Stack<String> operationBuffer;
    private Rate rate;
    private RelativeLayout parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rate = new Rate();
        observer = new Observer(rate);
        initializeScreens();
        initializeBaseSpinner();
        intializeTargetSpinner();
        initializeComponents();

        Once.initialise(this);
        runInBackground(getApplicationContext());
        fetch(rate);

    }

    public Rate fetch(Rate rate) {

        SqlLiteDataAccess sqlLiteDataAccess = new SqlLiteDataAccess(getApplicationContext());
        double ex =  sqlLiteDataAccess.get(rate.getBaseCurrency(), rate.getTargetCurrency());
        rate.setExchangeRate(ex);
        return rate;

    }

    public void initializeComponents() {
        parentView = (RelativeLayout) findViewById(R.id.parent_view);
        buttonZero = (TextView) findViewById(R.id.key_zero);
        button_1 = (TextView) findViewById(R.id.key_one);
        button_2 = (TextView) findViewById(R.id.key_two);
        button_3 = (TextView) findViewById(R.id.key_three);
        button_4 = (TextView) findViewById(R.id.key_four);
        button_5 = (TextView) findViewById(R.id.key_five);
        button_6 = (TextView) findViewById(R.id.key_six);
        button_7 = (TextView) findViewById(R.id.key_seven);
        button_8 = (TextView) findViewById(R.id.key_eight);
        button_9 = (TextView) findViewById(R.id.key_nine);
        buttonClear = (TextView) findViewById(R.id.key_clear);
        buttonDelete = (TextView) findViewById(R.id.key_delete);
        buttonDelete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clearStacks();
                return false;
            }
        });
        buttonDivision = (TextView) findViewById(R.id.key_divide);
        buttonMultiply = (TextView) findViewById(R.id.key_multiply);
        buttonMinus = (TextView) findViewById(R.id.key_subtraction);
        buttonPlus = (TextView) findViewById(R.id.key_addition);
        buttonDecimal = (TextView) findViewById(R.id.decimal_separator);
        buttonEvaluator = (TextView) findViewById(R.id.key_equal);
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

        rate.setBaseCurrency((String) adapter.getItem(0));

        baseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item =  parent.getSelectedItem().toString();
                rate.setBaseCurrency(item);
                rate = fetch(rate);
                baseCurrency.setText(rate.getBaseCurrency());
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

        rate.setTargetCurrency(((String) adapter.getItem(0)));

        targetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getSelectedItem().toString();

                rate.setTargetCurrency(item);
                rate = fetch(rate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void buttonOne(View v) {

        KeyPadButton keyPadButton = new NumberKeyPad();
        keyPadButton.setKeyString("1");
        String result = keyPadButton.onKeyPress(expressionText.getText().toString());
        expressionText.setText(result);

    }
    public void buttonTwo(View v) {

        KeyPadButton keyPadButton = new NumberKeyPad();
        keyPadButton.setKeyString("2");
        String result = keyPadButton.onKeyPress(expressionText.getText().toString());
        expressionText.setText(result);

    }
    public void buttonThree(View v) {

        KeyPadButton keyPadButton = new NumberKeyPad();
        keyPadButton.setKeyString("3");
        String result = keyPadButton.onKeyPress(expressionText.getText().toString());
        expressionText.setText(result);

    }
    public void buttonFour(View v) {

        KeyPadButton keyPadButton = new NumberKeyPad();
        keyPadButton.setKeyString("4");
        String result = keyPadButton.onKeyPress(expressionText.getText().toString());
        expressionText.setText(result);

    }

    public void buttonfive(View v) {

        KeyPadButton keyPadButton = new NumberKeyPad();
        keyPadButton.setKeyString("5");
        String result = keyPadButton.onKeyPress(expressionText.getText().toString());
        expressionText.setText(result);
    }

    public void buttonSix(View v) {

        KeyPadButton keyPadButton = new NumberKeyPad();
        keyPadButton.setKeyString("6");
        String result = keyPadButton.onKeyPress(expressionText.getText().toString());
        expressionText.setText(result);

    }

    public void buttonSeven(View v) {

        KeyPadButton keyPadButton = new NumberKeyPad();
        keyPadButton.setKeyString("7");
        String result = keyPadButton.onKeyPress(expressionText.getText().toString());
        expressionText.setText(result);

    }
    public void buttonEight(View v) {

        KeyPadButton keyPadButton = new NumberKeyPad();
        keyPadButton.setKeyString("8");
        String result = keyPadButton.onKeyPress(expressionText.getText().toString());
        expressionText.setText(result);

    }

    public void buttonNine(View v) {

        KeyPadButton keyPadButton = new NumberKeyPad();
        keyPadButton.setKeyString("9");
        String result = keyPadButton.onKeyPress(expressionText.getText().toString());
        expressionText.setText(result);
    }

    public void buttonZero(View v) {
        KeyPadButton keyPadButton = new KeyZero();
        keyPadButton.setKeyString("0");
        String result = keyPadButton.onKeyPress(expressionText.getText().toString());
        expressionText.setText(result);

    }

    public void buttonDecimalPoint(View v) {

        KeyPadButton keyPadButton = new DecimalPointKeypad();
        keyPadButton.setKeyString(".");
        String result = keyPadButton.onKeyPress(expressionText.getText().toString());
        expressionText.setText(result);

    }

    public void buttonPlus(View v) {
        KeyPadButton keyPadButton = new OperatorButton();
        keyPadButton.setKeyString("+");
        String result = keyPadButton.onKeyPress(expressionText.getText().toString());
        expressionText.setText(result);

    }

    public void buttonMinus(View v) {
        KeyPadButton keyPadButton = new OperatorButton();
        keyPadButton.setKeyString("-");
        String result = keyPadButton.onKeyPress(expressionText.getText().toString());
        expressionText.setText(result);

    }

    public void buttonMultiply(View v) {
        KeyPadButton keyPadButton = new OperatorButton();
        keyPadButton.setKeyString("*");
        String result = keyPadButton.onKeyPress(expressionText.getText().toString());
        expressionText.setText(result);

    }

    public void buttonDivide(View v) {
        KeyPadButton keyPadButton = new OperatorButton();
        keyPadButton.setKeyString("/");
        String result = keyPadButton.onKeyPress(expressionText.getText().toString());
        expressionText.setText(result);

    }

    public void buttonEqualTo(View v) {
        KeyPadButton keyPadButton = new EqualityKeyPad();
        String expression = keyPadButton.onKeyPress(expressionText.getText().toString());
        if (expression.equals("")){
            return;
        }
        else {
            double result = evaluate(expression);

            resultText.setText(String.valueOf(result));
        }
    }

    public void delete(View v) {
        KeyPadButton keyPadButton = new DeleteButton();
        String result = keyPadButton.onKeyPress(expressionText.getText().toString());
        expressionText.setText(result);

    }

    public void onKeyPressed(View v) {

    }

    public double evaluate(String expression) {

        Parser parser = new Parser();

        double d = 0.00000;

        try {

            ExpressionNode expressionNode = parser.parse(expression);

            d = expressionNode.getValue();

        } catch (ParserException e) {

            Log.d(TAG, e.getMessage());
        } catch (EvaluationException e) {

            Log.d(TAG, e.getMessage());
        }
        return convert(d);
    }
    public double convert(double val) {
        fetch(rate);
        double exRate = rate.getExchangeRate();
        Log.d(TAG, String.valueOf(rate.getExchangeRate()));
        return exRate * val;

    }
    public void clearButton(View v) {
        clearStacks();

    }
    public void clearStacks() {

        expressionText.setText("");

        resultText.setText("");

//        inputBuffer.clear();
//
//        operationBuffer.clear();


    }

    public void runInBackground(Context context){
        if (isConnected(context)){
            if (!beenDone(Once.THIS_APP_INSTALL, installDB)) {
                FetchTask fetchTask = new FetchTask(context);
                fetchTask.execute(context);
                Log.d(TAG, "this has been done");
                markDone(installDB);
            }
            else
                return;
        }


    }

    private boolean isConnected(Context context) {
        ConnectivityManager manager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        boolean isconnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isconnected;
    }

}
