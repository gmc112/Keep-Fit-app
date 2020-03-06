package com.example.goals.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.example.goals.R;
import com.example.goals.UpdateHomeActivity;
import com.example.goals.goal.Goal;
import com.example.goals.goal.GoalViewModel;
import com.example.goals.history.History;
import com.example.goals.history.HistoryGoals;
import com.example.goals.history.HistoryListAdapter;
import com.example.goals.history.HistoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private HistoryViewModel historyViewModel;
    private GoalViewModel goalViewModel;
    private View view;

    private ProgressBar progressBar;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public static final int UPDATE_HOME_ACTIVITY_REQUEST_CODE = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        goalViewModel = new ViewModelProvider(this).get(GoalViewModel.class);
        if (homeViewModel.getGoal() == null){
            homeViewModel.setGoal(new Goal("Default", 10000));  // ToDo: make dynamic
            homeViewModel.setSteps(0);
        }


        update();

        FloatingActionButton fab = view.findViewById(R.id.fab_home);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UpdateHomeActivity.class);
            startActivityForResult(intent, UPDATE_HOME_ACTIVITY_REQUEST_CODE);
        });
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_HOME_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            homeViewModel.addSteps(data.getIntExtra("steps",1));
            update();
        } else {
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    R.string.error_no_value,
                    Toast.LENGTH_LONG)
                    .show();
        }
    }


    /*
    mode is true if historical mode is active
     */
    private void update(){
          // ToDo: make dynamic
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        boolean mode = sp.getBoolean("historical", false);
        Goal goal = homeViewModel.getGoal();
        History history;
        if(mode){
            history = new History(new Date(sp.getLong("date", 1583385277578L)), goal.getId());
        } else{
            history = new History(Calendar.getInstance().getTime(), goal.getId());

        }

        List<HistoryGoals> historyGoals = goalViewModel.getHistoryGoals();
        for (HistoryGoals hg: historyGoals){
            if(hg.histories.contains(history)){
                goal =hg.goal;
                break;
            }
        }
        homeViewModel.setSteps(history.getSteps());
        homeViewModel.setGoal(goal);



        TextView textViewTitle = view.findViewById(R.id.tv_home_title);
        textViewTitle.setText(goal.getName());

        double steps = homeViewModel.getSteps();
        double goal_val = goal.getTarget();
        double progress = (steps/goal_val)*100;
        int val = (int) Math.round(progress);
        progressBar = view.findViewById(R.id.pb_home);
        progressBar.setProgress(val);

        TextView textViewPercent = view.findViewById(R.id.tv_home_percent);
        textViewPercent.setText(val + "%");
        TextView textViewProgress = view.findViewById(R.id.tv_home_display);
        String msg = "Current: " + (int) Math.round(steps) + "\n\nTarget: " + goal.getTarget();
        textViewProgress.setText(msg);

    }


//    private void init(){
//
//    }
//
//
//    @Override
//    public void onResume() {      Todo make this work
//        super.onResume();
//        init();
//
//    }
}
