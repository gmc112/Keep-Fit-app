package com.example.goals.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goals.NewGoalActivity;
import com.example.goals.R;
import com.example.goals.UpdateHomeActivity;
import com.example.goals.goal.Goal;
import com.example.goals.goal.GoalListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;

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

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        if (viewModel.getGoal() == null){
            viewModel.setGoal(new Goal("Default", 10000));  // ToDo: make dynamic
            viewModel.setSteps(1000);
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
            viewModel.addSteps(data.getIntExtra("steps",1));
            update();
        } else {
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    R.string.error_no_value,
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void update(){
          // ToDo: make dynamic

        Goal goal = viewModel.getGoal();
        TextView textViewTitle = view.findViewById(R.id.tv_home_title);
        textViewTitle.setText(goal.getName());

        double steps = viewModel.getSteps();
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
}
