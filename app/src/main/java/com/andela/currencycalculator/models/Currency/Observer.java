package com.andela.currencycalculator.models.Currency;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by Oluwatosin on 12/6/2015.
 */
public class Observer implements PropertyChangeListener {
    public Observer(Model model) {
        model.addChangeListeners(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        event.getPropertyName();
        event.getNewValue();

    }
}
