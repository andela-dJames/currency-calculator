package com.andela.currencycalculator;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oluwatosin on 12/18/2015.
 */
public class ResourceProviderTest extends AndroidTestCase {
    List<String> codes;
    ResourceProvider resourceProvider = new ResourceProvider(mContext);

    public void testGetCurrencyCodesFromResource() throws Exception {
        codes = new ArrayList<String>();
        codes = resourceProvider.getCurrencyCodesFromResource();
        assertEquals(10, codes.size());

    }
}