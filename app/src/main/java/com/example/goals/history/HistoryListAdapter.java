package com.example.goals.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.goals.R;
import com.example.goals.goal.Goal;

import java.util.List;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryViewHolder> {

    class HistoryViewHolder extends ViewHolder{
        private final TextView textView;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_history_item);
        }

        void setBackgroundColor(int color){
            textView.setBackgroundColor(color);
        }

        void setText(String msg){
            textView.setText(msg);
        }
    }

    private final LayoutInflater inflater;

    private List<History> histories;

    private final Context context;

    HistoryListAdapter (Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recycler_history_item, parent, false);
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        if(histories != null) {
            if (position % 2 == 0){
                holder.setBackgroundColor(ContextCompat.getColor(context, R.color.recyclerItem1));
            } else {
                holder.setBackgroundColor(ContextCompat.getColor(context, R.color.recyclerItem2));
            }
            History current = histories.get(position);
            Goal goal = current.getGoal();
            String msg = "Date: "+ current.getDate() + "\n"
                    + current.getSteps() + " / " + goal.getTarget();
            holder.setText(msg);
        }
    }

    @Override
    public int getItemCount() {
        if (histories != null)
            return histories.size();
        return 0;
    }

    public void setHistories(List<History> histories){
        this.histories = histories;
        notifyDataSetChanged();
    }



}
