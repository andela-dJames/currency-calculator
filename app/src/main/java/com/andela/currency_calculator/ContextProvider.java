package com.andela.currency_calculator;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Oluwatosin on 11/26/2015.
 */
public class ContextProvider extends Application {

    private static ContextProvider ourInstance;
    private List<String> codes;
    private Resources resources;
    private Context context;

    public static ContextProvider getInstance() {
        if (ourInstance == null){
            ourInstance = new ContextProvider();
        }
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    private ContextProvider() {
        codes = new ArrayList<String>();
    }

    public List<String> getCodes() {

        String[] list = context.getResources().getStringArray(R.array.currency_code);
        codes = Arrays.asList(list);
        return codes;
    }
}
