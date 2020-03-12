package com.example.goals.preferences;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.preference.DialogPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.goals.R;
import com.example.goals.goal.DMY;

import java.util.Calendar;
import java.util.Date;

public class PreferencesFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
            Preference preference = getPreferenceScreen().getPreference(i);
            initSummary(preference);
        }
    }

    private void initSummary(Preference preference) {
        if (preference instanceof PreferenceCategory) {
            PreferenceCategory pc = (PreferenceCategory) preference;
            for (int i = 0; i < pc.getPreferenceCount(); i++) {
                initSummary(pc.getPreference(i));
            }
        } else if (preference instanceof DatePreference) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
            DMY dmy = new DMY(new Date(sp.getLong("date", Calendar.getInstance().getTimeInMillis())));
            DatePreference datePref = (DatePreference) preference;
            datePref.setSummary("Date: " + dmy);
        }
    }


    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        DialogFragment dialogFragment = null;

        if (preference instanceof DatePreference) {
            dialogFragment = DateDialogFragmentCompat.newInstance(preference.getKey());
        } else if (preference instanceof DeleteHistoryPreference) {
            dialogFragment = DeleteHistoryDialogFragmentCompat.newInstance(preference.getKey());
        }
        if (dialogFragment != null) {
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(this.getParentFragmentManager(), null);
        } else {
            super.onDisplayPreferenceDialog(preference);
        }

    }


}
