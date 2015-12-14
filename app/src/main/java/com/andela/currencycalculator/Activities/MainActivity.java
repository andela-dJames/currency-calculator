package com.andela.currencycalculator.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Iterator;

import java.util.Stack;

import com.andela.currencycalculator.R;
import com.andela.currencycalculator.models.currency.Observer;
import com.andela.currencycalculator.models.currency.Rate;
import com.andela.currencycalculator.models.dal.FetchTask;
import com.andela.currencycalculator.models.dal.SqlLiteDataAccess;
import com.andela.currencycalculator.parcer.Parser;
import com.andela.currencycalculator.parcer.exception.EvaluationException;
import com.andela.currencycalculator.parcer.exception.ParserException;
import com.andela.currencycalculator.parcer.expressionnodes.ExpressionNode;

import jonathanfinerty.once.Once;

import static jonathanfinerty.once.Once.beenDone;
import static jonathanfinerty.once.Once.markDone;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";

    private String installDB = "INSTALL DATABASE";

    private String updateDB = "UPDATE DATABASE";


    private Observer observer;

    private Spinner baseSpinner;

    private Spinner targetSpinner;

    private boolean resetInput, hasfinalResult, hasOperator, hasOperand = false;

    private TextView buttonZero, button_1, button_2, button_3, button_4,
            button_5, button_6, button_7, button_8, button_9, buttonDelete,
            buttonDivision, buttonMultiply, buttonMinus, buttonPlus, buttonDecimal, buttonEvaluator;

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
        initializeBaseSpinner();

        intializeTargetSpinner();
        inputBuffer = new Stack<String>();
        operationBuffer = new Stack<>();

        if (!beenDone(Once.THIS_APP_INSTALL, installDB)) {
            runInBackground(getApplicationContext());
            Log.d(TAG, "this has been done");
            markDone(installDB);}

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
        buttonDelete = (TextView) findViewById(R.id.key_clear);
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

    public String getKeyString(View v) {
        switch (v.getId()) {
            case R.id.key_zero:
                return buttonZero.getText().toString();

            case R.id.key_one:
                return button_1.getText().toString();

            case R.id.key_two:
                return button_2.getText().toString();
            case R.id.key_three:
                return button_3.getText().toString();

            case R.id.key_four:
                return button_4.getText().toString();

            case R.id.key_five:
                return button_5.getText().toString();

            case R.id.key_six:
                return button_6.getText().toString();

            case R.id.key_seven:
                return button_7.getText().toString();

            case R.id.key_eight:
                return button_8.getText().toString();
            case R.id.key_nine:
                return button_9.getText().toString();

            case R.id.key_clear:
                return "DEL";

            case R.id.key_divide:
                return buttonDivision.getText().toString();
            case R.id.key_multiply:
                return buttonMultiply.getText().toString();

            case R.id.key_subtraction:
                return buttonMinus.getText().toString();

            case R.id.key_addition:
                return buttonPlus.getText().toString();

            case R.id.decimal_separator:
                return buttonDecimal.getText().toString();

            case R.id.key_equal:
                return buttonEvaluator.getText().toString();
            default:
                return null;
        }

    }

    public void initializeBaseSpinner() {

        baseSpinner = (Spinner) findViewById(R.id.base_curry);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_code, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        baseSpinner.setAdapter(adapter);

        rate.setBaseCurrency((String) adapter.getItem(0));

        baseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getSelectedItem().toString();
                rate.setBaseCurrency(item);
                rate = fetch(rate);

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

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        targetSpinner.setAdapter(adapter);

        rate.setTargetCurrency(((String) adapter.getItem(0)));

        targetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = (String) parent.getSelectedItem().toString();

                rate.setTargetCurrency(item);
                rate = fetch(rate);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    public void onKeyPressed(View v) {

//        String currentInput = getKeyString(v);
//        String screenText = expressionText.getText().toString();
//        String viewText = resultText.getText().toString();
//
//        if (currentInput.equals("DEL")) {
//            int endindex = screenText.length() - 1;
//            if (endindex < 1) {
//                expressionText.setText("");
//
//            }
//             else {
//                expressionText.setText(screenText.subSequence(0, endindex));
//
//            }
//        } else if (currentInput.equals(".")) {
//            if (hasfinalResult || resetInput) {
//                expressionText.setText(0 + ".");
//                hasfinalResult = false;
//                resetInput = false;
//            } else if (screenText.contains(".")) {
//                return;
//
//            } else {
//                expressionText.append(".");
//            }
//        } else if (currentInput.equals("+") || currentInput.equals("-") || currentInput.equals("/") || currentInput.equals("*")) {
//            if (resetInput) {
//                return;
//            } else {
//                if (hasOperator) {
//                    return;
//                } else if (hasOperand) {
//                    expressionText.append(currentInput);
//                    hasOperator = true;
//                }
//            }
//        } else if (currentInput.equals("=")) {
//            if (hasfinalResult) {
//
//                Log.d(TAG, resultText.getText().toString());
//                String i = String.valueOf(evaluate(expressionText.getText().toString()));
//
//                resultText.setText(i.toString() + " "+ rate.getTargetCurrency());
//            } else {
//                return;
//            }
//        } else {
//            expressionText.append(currentInput);
//            hasfinalResult = true;
//            hasOperand = true;
//            hasOperator = false;
//        }
//    }
//
//    public void dumpInput() {
//        expressionText.setText("");
//        Iterator<String> it = inputBuffer.iterator();
//        StringBuilder sb = new StringBuilder();
//
//        while (it.hasNext()) {
//            CharSequence iValue = it.next();
//            sb.append(iValue);
//
//        }
//
//        resultText.setText(sb.toString());
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

        double exRate = rate.getExchangeRate();
        Log.d(TAG, String.valueOf(rate.getExchangeRate()));
        return exRate * val;

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
        FetchTask fetchTask = new FetchTask();
        fetchTask.execute(context);
    }
}
