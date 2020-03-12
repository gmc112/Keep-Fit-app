package com.example.goals.goal;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.goals.NewGoalActivity;
import com.example.goals.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.app.Activity.RESULT_OK;

public class GoalFragment extends Fragment {

    private GoalViewModel goalViewModel;
    private GoalListAdapter adapter;
    public static GoalFragment newInstance() {
        return new GoalFragment();
    }

    public static final int NEW_GOAL_ACTIVITY_REQUEST_CODE = 1;

    public static final int UPDATE_GOAL_ACTIVITY_REQUEST_CODE = 2;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goal, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.re_goal);
        adapter = new GoalListAdapter(view.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        RecyclerView.ItemAnimator itemAnimator = recyclerView.getItemAnimator();
        if(itemAnimator instanceof SimpleItemAnimator)
            ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
        goalViewModel = new ViewModelProvider(this).get(GoalViewModel.class);

        goalViewModel.getAllGoals().observe(getViewLifecycleOwner(), adapter::setGoals);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NewGoalActivity.class);
            startActivityForResult(intent, NEW_GOAL_ACTIVITY_REQUEST_CODE);
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                        int pos = viewHolder.getAdapterPosition();
                        Goal goal = adapter.getGoalAtPosition(pos);
                        if (goal == null || goal.isActive()){
                            return 0;
                        }
                        return super.getSwipeDirs(recyclerView, viewHolder);
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Goal goal = adapter.getGoalAtPosition(position);
                        if(goal.isActive()){
//                            Toast.makeText(GoalFragment.this.getContext(), "Can't Remove Active Goal", Toast.LENGTH_LONG).show();
                        } else{
                            Toast.makeText(GoalFragment.this.getContext(), "Removed Goal", Toast.LENGTH_LONG).show();
                            goalViewModel.delete(goal);
                        }

                    }
                }
        );
        helper.attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new GoalListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(position == -1){
                    return;
                }
                Goal goal = adapter.getGoalAtPosition(position);
                if(!goal.isActive()){
                    goalViewModel.toggleGoal(goal);
                    Toast.makeText(
                            getContext(),
                            R.string.goal_update,
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){

            if (requestCode == NEW_GOAL_ACTIVITY_REQUEST_CODE ){
                Goal goal = new Goal(
                        data.getStringExtra("name"),
                        data.getIntExtra("target",10000),
                        false);
                if(!goalViewModel.insert(goal)){
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            R.string.error_goal_exists,
                            Toast.LENGTH_LONG
                    ).show();
                }
            } else if(requestCode == UPDATE_GOAL_ACTIVITY_REQUEST_CODE ){
                adapter.onActivityResult(data, goalViewModel);

            }
            return;
        } else {
            if(requestCode == NEW_GOAL_ACTIVITY_REQUEST_CODE){
                Toast.makeText(
                        getActivity().getApplicationContext(),
                        R.string.error_no_value,
                        Toast.LENGTH_LONG
                ).show();


            }
        }


    }

}
