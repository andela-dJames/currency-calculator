package com.andela.currency_calculator.models.dal;

/**
 * Created by Oluwatosin on 11/19/2015.
 */
public interface DataCallback<T> {
    public void onSuccess(T data);
    public void onFailure(String errormessage);
}
