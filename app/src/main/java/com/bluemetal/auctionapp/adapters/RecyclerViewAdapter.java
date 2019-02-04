package com.bluemetal.auctionapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<T> items;

    public abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent);

    public abstract void onBindData(RecyclerView.ViewHolder holder, T val);

    public RecyclerViewAdapter(Context context, List<T> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = setViewHolder(viewGroup);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        onBindData(viewHolder, items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItems(List<T> savedCardItems){
        items = savedCardItems;
        this.notifyDataSetChanged();
    }

    public T getItem(int position){
        return items.get(position);
    }

    public void addFilteredList(List<T> filteredList) {
        items = filteredList;
        notifyDataSetChanged();
    }
}
