package com.example.goals.goal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goals.R;

import java.util.List;

public class GoalListAdapter extends RecyclerView.Adapter<GoalListAdapter.GoalViewHolder> {

    class GoalViewHolder extends RecyclerView.ViewHolder {
        private final TextView goalTextView;
        private final ImageButton imageButton;
        private GoalViewHolder(View itemView){
            super(itemView);
            goalTextView = itemView.findViewById(R.id.tv_goal_item);
            imageButton = itemView.findViewById(R.id.ib_goal_item);
        }

        void setBackgroundColor(int color){
            imageButton.setBackgroundColor(color);      // ToDo: maybe remove
            goalTextView.setBackgroundColor(color);
        }

        void setText(String msg) {
            goalTextView.setText(msg);
        }
    }

    private final LayoutInflater inflater;

    private List<Goal> goals;

    private final Context context;

    GoalListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recycler_goal_item, parent, false);
        return new GoalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GoalViewHolder holder, int position) {

        if (goals != null){
            if (position % 2 == 0){
                holder.setBackgroundColor(ContextCompat.getColor(context, R.color.goalItem1));
            } else {
                holder.setBackgroundColor(ContextCompat.getColor(context, R.color.goalItem2));
            }
            Goal current = goals.get(position);
            String msg = current.getName()+ " "+ current.getTarget();
            holder.setText(msg);
        } else{
            holder.setText("Empty");
        }

    }

    @Override
    public int getItemCount() {
        if (goals != null)
            return goals.size();
        return 0;
    }

    public void setGoals(List<Goal> goals){
        this.goals = goals;         // Todo: Change to deep copy??
        notifyDataSetChanged();
    }
}
