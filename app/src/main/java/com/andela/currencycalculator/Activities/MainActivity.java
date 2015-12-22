package com.andela.currencycalculator.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andela.currencycalculator.R;
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
import com.andela.currencycalculator.parser.Parser;
import com.andela.currencycalculator.parser.exception.EvaluationException;
import com.andela.currencycalculator.parser.exception.ParserException;
import com.andela.currencycalculator.parser.expressionnodes.ExpressionNode;

import java.util.Stack;

import jonathanfinerty.once.Once;

import static jonathanfinerty.once.Once.beenDone;
import static jonathanfinerty.once.Once.markDone;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";

    private String installDB = "INSTALL DATABASE";

    private String updateDB = "UPDATE DATABASE";


    private Observer observer;



    private boolean resetInput, hasfinalResult, hasOperator, hasOperand = false;

    private TextView buttonZero, button_1, button_2, button_3, button_4,
            button_5, button_6, button_7, button_8, button_9, buttonDelete,
            buttonDivision, buttonMultiply, buttonMinus, buttonPlus, buttonDecimal, buttonEvaluator,
            buttonOpenBrace, buttonCloseBrace, buttonClear, targetCurrency, baseCurrency;;

    private TextView expressionText;
    private TextView resultText;
    private LinearLayout parentView;

    private Stack<String> inputBuffer;
    private Stack<String> operationBuffer;
    private Rate rate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rate = new Rate();
        observer = new Observer(rate);

        parentView = (LinearLayout) findViewById(R.id.parent_view);

        Once.initialise(this);
        initializeComponents();
        inputBuffer = new Stack<String>();
        operationBuffer = new Stack<>();

        runInBackground(getApplicationContext());
        rate = fetch(rate);

    }

    public Rate fetch(Rate rate) {

        SqlLiteDataAccess sqlLiteDataAccess = new SqlLiteDataAccess(getApplicationContext());
       double ex =  sqlLiteDataAccess.get(rate.getBaseCurrency(), rate.getTargetCurrency());
        rate.setExchangeRate(ex);
        return rate;

    }

    public void initializeComponents() {

        expressionText = (TextView) findViewById(R.id.expression_screen);
        resultText = (TextView) findViewById(R.id.evaluation_screen);
//        baseCurrency = (TextView) findViewById(R.id.base_curry_display);
//        targetCurrency = (TextView) findViewById(R.id.target_curry_display);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

        inputBuffer.clear();

        operationBuffer.clear();

        hasOperand = false;

        hasOperator = false;
    }
    public double retrieveData(Rate rate) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return (double) sharedPreferences.getFloat(rate.getExchangeRate() + "-"+ rate.getTargetCurrency(), 0f);
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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
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
