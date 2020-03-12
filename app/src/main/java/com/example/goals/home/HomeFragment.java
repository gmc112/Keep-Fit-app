package com.example.goals.home;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.example.goals.R;
import com.example.goals.UpdateHomeActivity;
import com.example.goals.goal.DMY;
import com.example.goals.goal.Goal;
import com.example.goals.goal.GoalViewModel;
import com.example.goals.goal.History;
import com.example.goals.history.HistoryListAdapter;
import com.example.goals.history.HistoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.BIND_IMPORTANT;

public class HomeFragment extends Fragment {
    private static final int PROGRESS_THRESHOLD = 75;
    private HomeViewModel homeViewModel;
    private GoalViewModel goalViewModel;
    private HistoryViewModel historyViewModel;
    private View view;
    private SharedPreferences preferences;
    private String prefFile = "com.example.goals.homesharedprefs";

    private ProgressBar progressBar;
    private static String CHANNEL_ID = "keep fit";
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public static final int UPDATE_HOME_ACTIVITY_REQUEST_CODE = 1;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        createNotificationChannel();
        preferences = getActivity().getSharedPreferences(prefFile, Context.MODE_PRIVATE);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        goalViewModel = new ViewModelProvider(this).get(GoalViewModel.class);

        restore();
        update();
        initialiseNotifications();
        initialiseStepDetector();

        FloatingActionButton fab = view.findViewById(R.id.fab_home);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), UpdateHomeActivity.class);
            startActivityForResult(intent, UPDATE_HOME_ACTIVITY_REQUEST_CODE);
        });
        return view;
    }


    private void initialiseStepDetector(){
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);


        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if(sensor!=null){
            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    homeViewModel.addSteps(1);
                    update();
                    initialiseNotifications();
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }
            },sensor, SensorManager.SENSOR_DELAY_FASTEST);
        }

    }


    private void initialiseNotifications() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        boolean enabled = sp.getBoolean("notifications", false);
        if(enabled){
            double steps = homeViewModel.getSteps();
            double goal_val = homeViewModel.getGoal().getTarget();
            double progress = (steps/goal_val)*100;
            int val = (int) Math.round(progress);
            if(val >= 100){
                DMY date = homeViewModel.getDate();
                if(!homeViewModel.isNotifySuccess()) {
                    homeViewModel.setNotifySuccess(true);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(view.getContext(), CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_check_circle_black_24dp)
                            .setContentTitle("Step Target Reached")
                            .setContentText("You have reached " +
                                    homeViewModel.getSteps() + " steps towards your " +
                                    homeViewModel.getGoal().getTarget() + " step goal!")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setAutoCancel(true)
                            .setOnlyAlertOnce(true);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(view.getContext());
                    notificationManager.notify((int) date.getTimestamp(), builder.build());
                }else{
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(view.getContext());
                    notificationManager.cancel((int)date.getTimestamp());
                }
            } else if(progress>PROGRESS_THRESHOLD){
                DMY date = homeViewModel.getDate();
                if(!homeViewModel.isNotifyClose()) {
                    homeViewModel.setNotifyClose(true);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(view.getContext(), CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_timeline_black_24dp)
                            .setContentTitle("Almost There!")
                            .setContentText("You are currently " +
                                    val + "% towards your " +
                                    homeViewModel.getGoal().getTarget() + " step goal!")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setAutoCancel(true)
                            .setOnlyAlertOnce(true);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(view.getContext());
                    notificationManager.notify((int) date.getTimestamp(), builder.build());
                }else{
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(view.getContext());
                    notificationManager.cancel((int)date.getTimestamp());
                }
            } else{
                homeViewModel.clearNotifications();
            }


        }
    }

    private void createNotificationChannel() {
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        Goal goal = homeViewModel.getGoal();
        editor.putInt("target", goal.getTarget());
        editor.putString("name", goal.getName());
        editor.putInt("steps", homeViewModel.getSteps());
        editor.putBoolean("success", homeViewModel.isNotifySuccess());
        editor.putBoolean("close", homeViewModel.isNotifyClose());
        DMY date = homeViewModel.getDate();
        editor.putInt("day", date.getDay());
        editor.putInt("month", date.getMonth());
        editor.putInt("year", date.getYear());
        editor.apply();
    }

    private void restore(){
        Goal goal = new Goal(
                preferences.getString("name", "Default"),
                preferences.getInt("target", 5000),
                true);
        homeViewModel.setGoal(goal);
        homeViewModel.setSteps(preferences.getInt("steps", 0));

        homeViewModel.setNotifySuccess(preferences.getBoolean("success", false));
        homeViewModel.setNotifyClose(preferences.getBoolean("close", false));
        DMY currentDate = new DMY(Calendar.getInstance().getTime());
        DMY date = new DMY(
                preferences.getInt("day", currentDate.getDay()),
                preferences.getInt("month", currentDate.getMonth()),
                preferences.getInt("year", currentDate.getYear()));
        homeViewModel.setDate(date);

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_HOME_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            homeViewModel.addSteps(data.getIntExtra("steps",1));
            update();
            initialiseNotifications();
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
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        boolean clear = sp.getBoolean("delete", false);
        if(clear){
            historyViewModel.deleteAll();
            sp.edit().putBoolean("delete", false).apply();
            homeViewModel.setSteps(0);
        }

        boolean mode = sp.getBoolean("historical", false);
        Goal activeGoal = goalViewModel.getActiveGoal();

        if(activeGoal != null){
            homeViewModel.setGoal(activeGoal);
        }
        Goal goal = homeViewModel.getGoal();

        History history;
        DMY date = new DMY(new Date(sp.getLong("date", Calendar.getInstance().getTimeInMillis())));

        if(mode){
            if (!homeViewModel.getDate().equals(date)){
                History temp = historyViewModel.getHistoryByDate(date);
                if(temp != null){
                    homeViewModel.setSteps(temp.getSteps());
                } else{
                    homeViewModel.setSteps(0);
                }
                homeViewModel.setDate(date);
            }
            history = new History(date, homeViewModel.getSteps(), goal);
        } else{
            date = new DMY(Calendar.getInstance().getTime());
            if (!homeViewModel.getDate().equals(date)){
                homeViewModel.setSteps(0);
                homeViewModel.setDate(date);
            }
            history = new History(Calendar.getInstance().getTime(), homeViewModel.getSteps(), goal);


        }

        historyViewModel.update(history);


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


}
