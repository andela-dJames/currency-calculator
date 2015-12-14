package com.andela.currencycalculator.models.KeyPad;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.andela.currencycalculator.R;

/**
 * Created by Oluwatosin on 11/29/2015.
 */
public class KeypadHandler extends View {
    private TextView buttonZero, button_1, button_2,button_3,button_4,
                button_5, button_6, button_7, button_8, button_9, buttonDelete,
                buttonDivision, buttonMultiply, buttonMinus, buttonPlus, buttonDecimal, buttonEvaluator;
    public KeypadHandler(Context context) {
        super(context);
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
        buttonDivision = (TextView) findViewById(R.id.key_divide);
        buttonMultiply = (TextView) findViewById(R.id.key_multiply);
        buttonMinus = (TextView) findViewById(R.id.key_subtraction);
        buttonPlus = (TextView) findViewById(R.id.key_addition);
        buttonDecimal = (TextView) findViewById(R.id.decimal_separator);
        buttonEvaluator = (TextView) findViewById(R.id.key_equal);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        try {
            buttonZero.setOnClickListener(l);
            button_1.setOnClickListener(l);
            button_2.setOnClickListener(l);
            button_4.setOnClickListener(l);
            button_5.setOnClickListener(l);
            button_6.setOnClickListener(l);
            button_7.setOnClickListener(l);
            button_8.setOnClickListener(l);
            button_9.setOnClickListener(l);
            buttonEvaluator.setOnClickListener(l);
            buttonDecimal.setOnClickListener(l);
            buttonDelete.setOnClickListener(l);
            buttonDivision.setOnClickListener(l);
            buttonMultiply.setOnClickListener(l);
            buttonPlus.setOnClickListener(l);

        }
        catch (Exception e){
            Log.e("BUTON PRESS", e.getMessage());
        }
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

//   public String onClick(View v, String val){
//       String str = getKeyString(v);
////       if (str == buttonDelete.getText().toString() || str == buttonEvaluator.getText().toString()){
////           return str;
////       }
////       else
//           return val.concat(str);
//
//   }
}
