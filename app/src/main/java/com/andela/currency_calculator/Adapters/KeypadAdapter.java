package com.andela.currency_calculator.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.andela.currency_calculator.models.KeyPad.KeypadButton;

/**
 * A class that handles how buttons are added to the keypad
 */
public class KeypadAdapter extends BaseAdapter {

    private Context context;

    private KeypadButton[] buttons = {
            KeypadButton.MC, KeypadButton.MR, KeypadButton.C
            , KeypadButton.BACKSPACE, KeypadButton.SEVEN, KeypadButton.EIGHT
            , KeypadButton.NINE,  KeypadButton.PLUS, KeypadButton.FOUR, KeypadButton.FIVE, KeypadButton.SIX, KeypadButton.BACKSPACE.MINUS
            , KeypadButton.BACKSPACE.ONE, KeypadButton.BACKSPACE.TWO, KeypadButton.THREE
            , KeypadButton.MULTIPLY, KeypadButton.DECIMAL_SEP, KeypadButton.ZERO
            , KeypadButton.CALCULATE
    };

    public KeypadAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
