package com.example.goals.goal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goals.EditGoalActivity;
import com.example.goals.MainActivity;
import com.example.goals.R;

import java.util.List;

import static com.example.goals.goal.GoalFragment.UPDATE_GOAL_ACTIVITY_REQUEST_CODE;

public class GoalListAdapter extends RecyclerView.Adapter<GoalListAdapter.GoalViewHolder> {

    private final LayoutInflater inflater;

    private List<Goal> goals;

    private final Context context;

    private Goal activeGoal;

    private OnItemClickListener listener;

    GoalListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public GoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recycler_goal_item, parent, false);
        return new GoalViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(GoalViewHolder holder, int position) {

        if (goals != null){
            if (position % 2 == 0){
                holder.setBackgroundColor(
                        ContextCompat.getColor(context, R.color.recyclerItem1),
                        ContextCompat.getColor(context, R.color.recyclerItem2)
                        );
            } else {
                holder.setBackgroundColor(
                        ContextCompat.getColor(context, R.color.recyclerItem2),
                        ContextCompat.getColor(context, R.color.recyclerItem1));
            }
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            boolean editable = sp.getBoolean("edit", true);
            Goal current = goals.get(position);
            if(current.isActive()){
                holder.editButton.setVisibility(View.INVISIBLE);
                holder.activeButton.setVisibility(View.INVISIBLE);
                holder.setBackgroundColor(ContextCompat.getColor(context, R.color.recyclerActive), 0);
            } else if(!editable){
                holder.editButton.setVisibility(View.INVISIBLE);
            } else {
                holder.editButton.setVisibility(View.VISIBLE);
                holder.editButton.setOnClickListener(v -> {
                    Intent intent = new Intent(context, EditGoalActivity.class);
                    intent.putExtra("position", position);
                    Fragment fragment = ((MainActivity) context).fragmentManager.findFragmentById(R.id.fr_goal);
                    fragment.startActivityForResult(intent, UPDATE_GOAL_ACTIVITY_REQUEST_CODE);
                });
            }
            holder.setText(current.getName(), "Target: " + current.getTarget() + " Steps");
        } else{
            holder.setText("Empty", "Empty");
        }

    }

    @Override
    public int getItemCount() {
        if (goals != null)
            return goals.size();
        return 0;
    }

    public void setGoals(List<Goal> goals){
        for(Goal goal: goals)
            if(goal.isActive())
                activeGoal = goal;
        this.goals = goals;         // Todo: Change to deep copy??
        notifyDataSetChanged();
    }

    public Goal getGoalAtPosition(int pos){
        if(pos >= goals.size())
            return null;
        return goals.get(pos);
    }

    public void onActivityResult(Intent data, GoalViewModel viewModel) {
        String name = data.getStringExtra("name");
        int target = data.getIntExtra("target", 0);
        int position = data.getIntExtra("position", -1);
        if(position == -1){
            Toast.makeText(
                    context,
                    "An error occured",
                    Toast.LENGTH_LONG
            ).show();
            return;
        }
        Goal current = getGoalAtPosition(position);

        if (name == null || name.equals("")){
            name = current.getName();
        }
        if(target==0){
            target = current.getTarget();
        }
        if(name.equals(current.getName())){
            current.setTarget(target);
            viewModel.update(current);
            Toast.makeText(
                    context,
                    R.string.goal_update,
                    Toast.LENGTH_LONG
            ).show();
        }else{

            if(!viewModel.insert(new Goal(name, target, false))){
                Toast.makeText(
                        context,
                        R.string.error_goal_exists,
                        Toast.LENGTH_LONG
                ).show();
            } else{
                viewModel.delete(current);
                Toast.makeText(
                        context,
                        R.string.goal_update,
                        Toast.LENGTH_LONG
                ).show();
            }
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    class GoalViewHolder extends RecyclerView.ViewHolder {
        private final TextView goalTextViewTitle;
        private final ImageButton editButton;
        private final TextView goalTextViewValue;
        private final ImageButton activeButton;
        private GoalViewHolder(View itemView, OnItemClickListener listener){
            super(itemView);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(listener!= null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                    return false;
                }
            });
            goalTextViewTitle = itemView.findViewById(R.id.tv_goal_item_title);
            editButton = itemView.findViewById(R.id.ib_goal_edit);
            goalTextViewValue = itemView.findViewById(R.id.tv_goal_item_value);
            activeButton = itemView.findViewById(R.id.ib_goal_active);
            activeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        void setBackgroundColor(int tvColor, int btColor){
            goalTextViewTitle.setBackgroundColor(tvColor);
            goalTextViewValue.setBackgroundColor(tvColor);
//            ImageViewCompat.setImageTintList(editButton, ColorStateList.valueOf(btColor));
//            ImageViewCompat.setImageTintList(activeButton, ColorStateList.valueOf(btColor));
        }

        void setText(String title, String value) {
            goalTextViewTitle.setText(title);
            goalTextViewValue.setText(value);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
