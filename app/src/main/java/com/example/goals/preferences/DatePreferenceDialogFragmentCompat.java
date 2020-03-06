package com.example.goals.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import androidx.preference.DialogPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceDataStore;
import androidx.preference.PreferenceDialogFragmentCompat;
import androidx.preference.PreferenceManager;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.example.goals.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DatePreferenceDialogFragmentCompat extends PreferenceDialogFragmentCompat {

    private DatePicker datePicker;

    public static DatePreferenceDialogFragmentCompat newInstance(String key){
        final DatePreferenceDialogFragmentCompat fragment = new DatePreferenceDialogFragmentCompat();
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
            long currentTime = Calendar.getInstance().getTime().getTime();
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
        }
    }
}
