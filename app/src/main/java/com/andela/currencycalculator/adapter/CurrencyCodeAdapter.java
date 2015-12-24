package com.andela.currencycalculator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.andela.currencycalculator.ResourceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oluwatosin on 12/23/2015.
 */
public class CurrencyCodeAdapter extends RecyclerView.Adapter<CurrencyCodeAdapter.ViewHolder> {

    private Context context;
    private List<String> codes;
    private ResourceProvider resourceProvider;

    public CurrencyCodeAdapter(Context context) {
        this.context = context;
        codes = new ArrayList<>();
        resourceProvider = new ResourceProvider(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LinearLayout itemView =  holder.viewHolder;



    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout viewHolder;
        public ViewHolder(View itemView) {
            super(itemView);
            viewHolder = (LinearLayout) itemView;
        }
    }
}
