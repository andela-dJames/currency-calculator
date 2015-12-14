package com.andela.currencycalculator.activities;

import android.content.Context;
import android.os.Bundle;
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

import java.util.Stack;

import com.andela.currencycalculator.R;
import com.andela.currencycalculator.models.Currency.Observer;
import com.andela.currencycalculator.models.Currency.Rate;
import com.andela.currencycalculator.models.dal.FetchTask;
import com.andela.currencycalculator.models.dal.SqlLiteDataAccess;
import com.andela.currencycalculator.parser.Parser;
import com.andela.currencycalculator.parser.exception.EvaluationException;
import com.andela.currencycalculator.parser.exception.ParserException;
import com.andela.currencycalculator.parser.expressionnodes.ExpressionNode;
import com.andela.keypadcontroller.KeyPadButton;
import com.andela.keypadcontroller.NumberKeyPad;




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
            buttonDivision, buttonMultiply, buttonMinus, buttonPlus, buttonDecimal, buttonEvaluator,
            buttonOpenBrace, buttonCloseBrace, buttonClear, targetCurrency, baseCurrency;

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

//        Once.initialise(this);
        initializeComponents();
        initializeBaseSpinner();

        intializeTargetSpinner();
        inputBuffer = new Stack<String>();
        operationBuffer = new Stack<>();
        //runInBackground(getApplicationContext());

//        if (!beenDone(Once.THIS_APP_INSTALL, installDB)) {
//            runInBackground(getApplicationContext());
//            Log.d(TAG, "this has been done");
//            markDone(installDB);}

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
        baseCurrency = (TextView) findViewById(R.id.base_curry_display);
        targetCurrency = (TextView) findViewById(R.id.target_curry_display);
        buttonZero = (TextView) findViewById(R.id.key_zero);
        button_1 = (TextView) findViewById(R.id.key_one);
        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                KeyPadButton keyPadButton = new NumberKeyPad();
                keyPadButton.onKeyPress(expressionText.getText().toString());
            }
        });
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
        buttonOpenBrace = (TextView) findViewById(R.id.key_openbrace);
        buttonCloseBrace = (TextView) findViewById(R.id.key_closebrace);
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

            case R.id.key_delete:
                return "del";

            case R.id.key_clear:
                return "C";

            case R.id.key_openbrace:
                return "(";

            case R.id.key_closebrace:
                return ")";

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

    /**
     * Intitialize Base currency spinner
     */
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
                baseCurrency.setText(rate.getBaseCurrency());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Initialize Target currency Spinner
     */
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
                targetCurrency.setText(rate.getTargetCurrency());

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

    public void onKeyPressed(View v) {
        String currentInput = getKeyString(v);
        String screenText = expressionText.getText().toString();
        String viewText = resultText.getText().toString();
        if (currentInput.equals("(") || currentInput.equals(")")){
            return;
        }

        else if (currentInput.equals("C")) {
            clearStacks();
        }

        else if (currentInput.equals("del")) {
            int endindex = screenText.length() - 1;
            if (endindex < 1) {
                expressionText.setText("");

            }
             else {
                expressionText.setText(screenText.subSequence(0, endindex));

            }
        } else if (currentInput.equals(".")) {
            if (hasfinalResult || resetInput) {
                expressionText.setText(0 + ".");
                hasfinalResult = false;
                resetInput = false;
            } else if (screenText.contains(".")) {
                return;

            } else {
                expressionText.append(".");
            }
        } else if (currentInput.equals("+") || currentInput.equals("-") || currentInput.equals("/") || currentInput.equals("*")) {
            if (resetInput) {
                return;
            } else {
                if (hasOperator) {
                    return;
                } else if (hasOperand) {
                    expressionText.append(currentInput);
                    hasOperator = true;
                }
            }
        } else if (currentInput.equals("=")) {
            if (hasfinalResult) {

                Log.d(TAG, resultText.getText().toString());
                String i = String.valueOf(evaluate(expressionText.getText().toString()));

                resultText.setText(i.toString());
            } else {
                return;
            }
        } else {
            expressionText.append(currentInput);
            hasfinalResult = true;
            hasOperand = true;
            hasOperator = false;
        }
    }

    /**
     * Evaluate an expresion
     * @param expression
     * @return
     */
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

    /**
     * Convert to target Currency
     * @param val
     * @return
     */
    public double convert(double val) {

        double exRate = rate.getExchangeRate();
        Log.d(TAG, String.valueOf(rate.getExchangeRate()));
        return exRate * val;

    }

    /**
     * clear all stacks
     */
    public void clearStacks() {

        expressionText.setText("");

        resultText.setText("");

        inputBuffer.clear();

        operationBuffer.clear();

        hasOperand = false;

        hasOperator = false;
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

    /**
     * A task to be run in the background
     * @param context
     */
    public void runInBackground(Context context){
        FetchTask fetchTask = new FetchTask(context);
        fetchTask.execute(context);
    }

}
