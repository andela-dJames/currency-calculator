package com.andela.currencycalculator;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Created by Oluwatosin on 12/8/2015.
 */
public class FullTestSuits extends TestSuite {

    public static Test runSuit() {
        return new TestSuiteBuilder(ActivityTests.class)
                .includeAllPackagesUnderHere().build();

    }

    public FullTestSuits() {
        super();
    }
}
