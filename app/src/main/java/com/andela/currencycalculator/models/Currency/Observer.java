package com.andela.currencycalculator.models.currency;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


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
