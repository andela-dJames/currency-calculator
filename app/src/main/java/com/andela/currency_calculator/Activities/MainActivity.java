package com.andela.currency_calculator.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.currency_calculator.R;
import com.andela.currency_calculator.models.Currency.Rate;
import com.andela.currency_calculator.models.dal.ExchangeRateAPICollection;
import com.andela.currency_calculator.parcer.Parser;
import com.andela.currency_calculator.parcer.exception.EvaluationException;
import com.andela.currency_calculator.parcer.exception.ParserException;
import com.andela.currency_calculator.parcer.expressionnodes.ExpressionNode;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main Activity" ;
    private Spinner baseSpinner;
    private Spinner targetSpinner;
    private List<String> curr_codes;
    private boolean resetInput, hasfinalResult, hasOperator, hasOperand = false;
    private TextView buttonZero, button_1, button_2,button_3,button_4,
            button_5, button_6, button_7, button_8, button_9, buttonDelete,
            buttonDivision, buttonMultiply, buttonMinus, buttonPlus, buttonDecimal, buttonEvaluator;
    private TextView expressionText;
    private TextView resultText;
    private RelativeLayout parentView;
    private Stack<String> inputBuffer;
    private Stack<String> operationBuffer;
    private Rate rate;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ON CREATE", "what happens");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rate = new Rate();
        parentView = (RelativeLayout) findViewById(R.id.parent_view);

        initializeBaseSpinner();
        intializeTargetSpinner();
        String[] array = getApplicationContext().getResources().getStringArray(R.array.currency_code);
        curr_codes = Arrays.asList(array);
        initializeComponents();
        inputBuffer = new Stack<String>();
        operationBuffer = new Stack<>();
        ExchangeRateAPICollection collection = new ExchangeRateAPICollection();
        collection.execute(curr_codes);

    }

    public void initializeComponents(){
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

    public String getKeyString(View v){
        switch (v.getId()){
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currency_code, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        baseSpinner.setAdapter(adapter);
        baseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getSelectedItem().toString();
//                Toast.makeText(parent.getContext(),
//                        "On Item Select : \n" + item,
//                        Toast.LENGTH_LONG).show();
                rate.setBaseCurrency(item);
                showRate();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void showRate() {
        resultText.setText(rate.getBaseCurrency());
    }
    public void intializeTargetSpinner() {
         targetSpinner = (Spinner) findViewById(R.id.target_currency_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currency_code, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        targetSpinner.setAdapter(adapter);
        targetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getSelectedItem().toString();
                rate.setTargetCurrency(item);
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

        if (currentInput.equals("DEL")){
            int endindex = screenText.length()-1;
            int index = resultText.length() - 1;
            if(endindex < 1 ){
                expressionText.setText("");

            }
            if(index < 1){
                resultText.setText("");
            }
            else {
                expressionText.setText(screenText.subSequence(0, endindex));
                resultText.setText(screenText.subSequence(0, index));
            }
        }
        else if (currentInput.equals(".")){
           if (hasfinalResult || resetInput){
               expressionText.setText(0+".");
               hasfinalResult = false;
               resetInput = false;
           }
            else if (screenText.contains(".")){
               return;

           }
            else {
               expressionText.append(".");
           }
        }
        else if (currentInput.equals("+") || currentInput.equals("-") || currentInput.equals("/") || currentInput.equals("*"))
        {
            if (resetInput){
                return;
            }
            else {
                if (hasOperator){
                    return;
                }
                else if (hasOperand){
                    expressionText.append(currentInput);
                    hasOperator = true;
                }
            }
//           if (resetInput){
//               inputBuffer.pop();
//               operationBuffer.pop();
//
//           }
//            else { if (currentInput.charAt(0) == '-'){
//               inputBuffer.add("(" + screenText + ")");
//                }
//               else {
//               inputBuffer.add(screenText);
//                }
//               operationBuffer.add(screenText);
//           }
//            inputBuffer.add(currentInput);
//            operationBuffer.add(currentInput);
//
//            Log.d(TAG, String.valueOf(resetInput));
//                resetInput = true;
        }
        else if (currentInput.equals("="))
        {if (hasfinalResult) {

            Log.d(TAG, resultText.getText().toString());
            String i = String.valueOf(evaluate(expressionText.getText().toString()));

            resultText.setText(i.toString());
        }
            else {
            return;
        }
        }
        else {
            expressionText.append(currentInput);
            hasfinalResult = true;
            hasOperand = true;
            hasOperator = false;
        }
    }
    public void dumpInput(){
        expressionText.setText("");
        Iterator<String> it = inputBuffer.iterator();
        StringBuilder sb = new StringBuilder();

        while (it.hasNext()) {
            CharSequence iValue = it.next();
            sb.append(iValue);

        }

        resultText.setText(sb.toString());
    }
    public double evaluate(String expression){
        Parser parser = new Parser();
        double d = 0.0;
        try {
            ExpressionNode expressionNode = parser.parse(expression);
            d = expressionNode.getValue();
            Log.d(TAG, String.valueOf(d));
        }
        catch (ParserException e){
            Log.d(TAG, e.getMessage());
        }
        catch (EvaluationException e){
            Log.d(TAG, e.getMessage());
        }
        return d;
    }

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
