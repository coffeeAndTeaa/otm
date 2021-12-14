package com.jingyu.otm.ui;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jingyu.otm.db.Run;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter {
    //1. supporting the data
    private List<Run> runs = new ArrayList<>();

    public void setRuns(List<Run> newList) {
        runs.clear();
        runs.addAll(newList);
        notifyDataSetChanged();
    }



    //2. ViewHolder
    public static class RunsViewHolder extends RecyclerView.ViewHolder {



        public RunsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }



    //3. Adapter overrides:

}
