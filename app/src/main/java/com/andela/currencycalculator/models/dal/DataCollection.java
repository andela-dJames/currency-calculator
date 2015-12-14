package com.andela.currencycalculator.models.dal;


import java.util.List;

public interface DataCollection<T> {
    public void save(T data, DataCallback callback);

    public void get(T rate, DataCallback<List<T>> callback);

    public void getAll(DataCollection<List<T>> callback);

    public void update(T data, DataCallback callback);
}
