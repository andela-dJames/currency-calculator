package com.andela.currency_calculator.models.Currency;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oluwatosin on 12/6/2015.
 */
public class Model {

    public static final String BASE_CURRENCY = "baseCurrency";
    
    public static final String TARGET_CURRENCY = "targetCurrency";

    public static final String EXCHANGE_RATE = "exchangeRate";

    private List<PropertyChangeListener> listeners;

    private List<Rate> rates;

    public Model() {
        listeners = new ArrayList<PropertyChangeListener>();
    }

    public void notifyChangeListeners(Object object, String property, String oldValue, String newValue) {
        for (PropertyChangeListener name : listeners) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }
    public void notifyChangeListeners(Object object, String property, double oldValue, double newValue) {
        for (PropertyChangeListener name : listeners) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

        public void addChangeListeners(PropertyChangeListener listner) {
        listeners.add(listner);

    }

}
