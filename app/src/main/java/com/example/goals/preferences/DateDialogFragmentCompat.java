package com.example.goals.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.preference.DialogPreference;
import androidx.preference.PreferenceDialogFragmentCompat;
import androidx.preference.PreferenceManager;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.example.goals.R;
import com.example.goals.goal.DMY;

public class DateDialogFragmentCompat extends PreferenceDialogFragmentCompat {

    private DatePicker datePicker;

    public static DateDialogFragmentCompat newInstance(String key){
        final DateDialogFragmentCompat fragment = new DateDialogFragmentCompat();
        final Bundle bundle = new Bundle(1);
        bundle.putString(ARG_KEY, key);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected View onCreateDialogView(Context context) {
        return super.onCreateDialogView(context);
    }

    @Override
    public void onBindDialogView(View view) {
        super.onBindDialogView(view);
        datePicker = view.findViewById(R.id.dp_pref_dialog);
        Long date = null;
        DialogPreference preference = getPreference();

        if(preference instanceof DatePreference) {
            date = ((DatePreference) preference).getDate();
        }
        if(date != null) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            datePicker.setMaxDate(currentTime);

        }
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if(positiveResult) {
            Calendar c = GregorianCalendar.getInstance();
            c.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
            long date = c.getTime().getTime();
            DialogPreference preference = getPreference();
            if(preference instanceof DatePreference) {
                DatePreference datePreference = (DatePreference) preference;
                datePreference.setDate(date);
            }
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
            DMY dmy = new DMY(new Date(sp.getLong("date", Calendar.getInstance().getTimeInMillis())));
            preference.setSummary("Date: " + dmy);
        }
    }
}
