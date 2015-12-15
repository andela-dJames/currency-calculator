package com.andela.currencycalculator;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;


import com.andela.currencycalculator.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ActivityTests {
    String USD = "USD";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void listGoesOverTheFold() {
        onView(withId(R.id.parent_view));
    }
    @Test
    public void testClickZero() {
        onView(withId(R.id.key_zero)).perform(click());
        onView(withId(R.id.expression_screen)).check(matches(withText("0")));
    }
    @Test
    public void testClickOne() {
        onView(withId(R.id.key_one)).perform(click());
        onView(withId(R.id.expression_screen)).check(matches(withText("1")));
    }
    @Test
    public void testClickTwo() {
        onView(withId(R.id.key_two)).perform(click());
        onView(withId(R.id.expression_screen)).check(matches(withText("2")));
    }

    @Test
    public void testClickThree() {
        onView(withId(R.id.key_three)).perform(click());
        onView(withId(R.id.expression_screen)).check(matches(withText("3")));
    }

    @Test
    public void testClickFour() {
        onView(withId(R.id.key_four)).perform(click());
        onView(withId(R.id.expression_screen)).check(matches(withText("4")));
    }

    @Test
    public void testClickFive() {
        onView(withId(R.id.key_five)).perform(click());
        onView(withId(R.id.expression_screen)).check(matches(withText("5")));
    }

    @Test
    public void testClickSix() {
        onView(withId(R.id.key_six)).perform(click());
        onView(withId(R.id.expression_screen)).check(matches(withText("6")));
    }
    @Test
    public void testClickSeven() {
        onView(withId(R.id.key_seven)).perform(click());
        onView(withId(R.id.expression_screen)).check(matches(withText("7")));
    }

    @Test
    public void testClickEight() {
        onView(withId(R.id.key_eight)).perform(click());
        onView(withId(R.id.expression_screen)).check(matches(withText("8")));
    }

    @Test
    public void testClickNine() {
        onView(withId(R.id.key_nine)).perform(click());
        onView(withId(R.id.expression_screen)).check(matches(withText("9")));
    }

    @Test
    public void testClickDecimalPoint() {
        onView(withId(R.id.decimal_separator)).perform(click());
        onView(withId(R.id.expression_screen)).check(matches(withText(".")));
    }
    @Test
    public void testClickPlusOperatorDoesNothingWithoutAnOperand() {
        onView(withId(R.id.key_addition)).perform(click());
        onView(withId(R.id.expression_screen)).check(matches(withText(containsString(""))));
    }
    @Test
    public void testBaseSpinnerItemSelected() {
        onView(withId(R.id.base_curry));
        onData(allOf(is(instanceOf(String.class)), is(USD))).perform(click());
        onView(withId(R.id.base_curry_display)).check(matches(withText(containsString(USD))));
    }

    @Test
    public void testClickminusOperatorDoesNothingWithoutAnOperand() {
        onView(withId(R.id.key_subtraction)).perform(click());
        onView(withId(R.id.expression_screen)).check(matches(withText(containsString(""))));
    }

    @Test
    public void testClickdivisionOperatorDoesNothingWithoutAnOperand() {
        onView(withId(R.id.key_divide)).perform(click());
        onView(withId(R.id.expression_screen)).check(matches(withText(containsString(""))));
    }

    @Test
    public void testClickMultiplicationOperatorDoesNothingWithoutAnOperand() {
        onView(withId(R.id.key_multiply)).perform(click());
        onView(withId(R.id.expression_screen)).check(matches(withText(containsString(""))));
    }

    @Test
    public void testClickEqualityOperatorDoesNothingWithoutAnOperand() {
        onView(withId(R.id.key_equal)).perform(click());
        onView(withId(R.id.expression_screen)).check(matches(withText(containsString(""))));
    }

    @Test
    public void testClickPlusOperatorAddsAnOperand() {
        onView(withId(R.id.key_one)).perform(click());
        onView(withId(R.id.key_addition)).perform(click());
        onView(withId(R.id.key_two)).perform(click());
        onView(withId(R.id.evaluation_screen)).check(matches(withText(containsString("3"))));
    }
    @Test
    public void testClickminusOperatorSubtractsAnOperand() {
        onView(withId(R.id.key_one)).perform(click());
        onView(withId(R.id.key_subtraction)).perform(click());
        onView(withId(R.id.key_two)).perform(click());
        onView(withId(R.id.evaluation_screen)).check(matches(withText("-1.0 ")));
    }



}