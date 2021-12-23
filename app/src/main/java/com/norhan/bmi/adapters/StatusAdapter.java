package com.norhan.bmi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.norhan.bmi.R;
import com.norhan.bmi.models.Status;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder> {

    private List<Status> mStatuses;

    public StatusAdapter(List<Status> statuses) {
        mStatuses = statuses;
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View statusView = inflater.inflate(R.layout.status_item, parent, false);

        return new StatusViewHolder(statusView);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {
        Status status = mStatuses.get(position);

        TextView dateTextView = holder.dateTextView;
        dateTextView.setText(status.getmDate());

        TextView weightTextView = holder.weightTextView;
        weightTextView.setText(status.getmWeight() + "");

        TextView heightTextView = holder.heightTextView;
        heightTextView.setText(status.getmHeight() + "");

        TextView statusTextView = holder.statusTextView;
        statusTextView.setText(status.isNormal() ? "Normal" : "Not normal");
    }

    @Override
    public int getItemCount() {
        return mStatuses.size();
    }

    public static class StatusViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView, weightTextView, statusTextView, heightTextView;

        public StatusViewHolder(View itemView) {
            super(itemView);

            dateTextView = itemView.findViewById(R.id.status_date);
            weightTextView = itemView.findViewById(R.id.status_weight);
            statusTextView = itemView.findViewById(R.id.status_normal);
            heightTextView = itemView.findViewById(R.id.status_height);
        }
    }
}