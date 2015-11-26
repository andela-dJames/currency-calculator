package com.andela.currency_calculator;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Oluwatosin on 11/26/2015.
 */
public class ContextProvider extends Application {

    private static Context context;
    private static ContextProvider ourInstance;
    private List<String> codes;

    public static ContextProvider getInstance() {
        if (ourInstance == null){
            ourInstance = new ContextProvider();
        }
        return ourInstance;
    }

    private ContextProvider() {
        context = getApplicationContext();
        codes = new ArrayList<String>();
    }

    public Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        ContextProvider.context = context;
    }
    public List<String> getCodes(){
        String[] strings = context.getResources().getStringArray(R.array.currency_code);
       return codes = Arrays.asList(strings);
    }
}
