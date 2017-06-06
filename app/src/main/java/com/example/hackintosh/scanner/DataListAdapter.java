package com.example.hackintosh.scanner;

import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hackintosh on 6/6/17.
 */

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.ViewHolder> {

    private ViewHolder viewHolder;
    private List<String> scans;

    public DataListAdapter(List<String> receivers) {
        this.scans = receivers;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView mResult;
        public ViewHolder(View v) {
            super(v);
            mResult = (TextView) v.findViewById(R.id.result);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scans_model,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        this.viewHolder = viewHolder;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mResult.setText(scans.get(position));
    }


    @Override
    public int getItemCount() {
        return scans.size();
    }
}
