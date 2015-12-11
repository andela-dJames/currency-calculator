package com.andela.currencycalculator;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;


import com.andela.currencycalculator.activities.MainActivity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class ResourceProviderTest extends ActivityInstrumentationTestCase2<MainActivity> {
   
    List<String>codes;
    MainActivity mainActivity;
    private Context context;

    public ResourceProviderTest(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    @Override
    public void setUp() throws Exception {
        mainActivity = getActivity();
         context = mainActivity.getApplicationContext();
        codes = new ArrayList<>();
    }
@Test
    public void testGetCurrencyCodesFromResource() throws Exception {
        ResourceProvider resourceProvider = new ResourceProvider(context);
        codes = resourceProvider.getCurrencyCodesFromResource();
        assertEquals(26, codes.size());

    }
}