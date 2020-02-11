package com.example.goals.goal;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.goals.NewGoalActivity;
import com.example.goals.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import static android.app.Activity.RESULT_OK;

public class GoalFragment extends Fragment {

    private GoalViewModel goalViewModel;

    public static GoalFragment newInstance() {
        return new GoalFragment();
    }

    public static final int NEW_GOAL_ACTIVITY_REQUEST_CODE = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goal, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.re_goal);
        final GoalListAdapter adapter = new GoalListAdapter(view.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        goalViewModel = new ViewModelProvider(this).get(GoalViewModel.class);

        goalViewModel.getAllGoals().observe(getViewLifecycleOwner(), goals -> adapter.setGoals(goals));

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NewGoalActivity.class);
            startActivityForResult(intent, NEW_GOAL_ACTIVITY_REQUEST_CODE);
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_GOAL_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            Goal goal = new Goal(
                    data.getStringExtra("name"),
                    data.getIntExtra("target",10000));
            goalViewModel.insert(goal);
        } else {
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    R.string.error_no_value,
                    Toast.LENGTH_LONG)
                    .show();
        }
    }



}
