package com.jingyu.otm.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jingyu.otm.R;
import com.jingyu.otm.databinding.FragmentRunBinding;
import com.jingyu.otm.databinding.RunItemBinding;
import com.jingyu.otm.db.Run;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.RunsViewHolder>{
    //1. supporting the data
    private List<Run> runs = new ArrayList<>();

    public void setRuns(List<Run> newList) {
        runs.clear();
        runs.addAll(newList);
        notifyDataSetChanged();
    }

    //2. ViewHolder
    public static class RunsViewHolder extends RecyclerView.ViewHolder {
        TextView runNameView;
        TextView runStepView;
        TextView runDurationView;
        TextView caloriesView;

        public RunsViewHolder(@NonNull View itemView) {
            super(itemView);
            RunItemBinding binding = RunItemBinding.bind(itemView);
            runNameView = binding.runName;
            runStepView = binding.runSteps;
            runDurationView = binding.runDuration;
            caloriesView = binding.caloriesBurned;
        }
    }



    //3. Adapter overrides:

    @NonNull
    @Override
    public RunsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.run_item, parent, false);
        return new RunsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RunsViewHolder holder, int position) {
        Run currentRun = runs.get(position);
        holder.runNameView.setText("Running name " + currentRun.runName); // TODO: Fix this runName not populating properly.
        holder.runDurationView.setText("Running duration " + currentRun.seconds + " s");
        holder.runStepView.setText("Running steps" + currentRun.steps.toString());
        Double cal = currentRun.steps * 0.04;
        holder.caloriesView.setText("Burned cal " + cal.toString() + " cal");
    }

    @Override
    public int getItemCount() {
        return runs.size();
    }


}
