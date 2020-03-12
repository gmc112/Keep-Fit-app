package com.example.goals.preferences;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceDialogFragmentCompat;
import androidx.preference.PreferenceManager;

public class DeleteHistoryDialogFragmentCompat extends PreferenceDialogFragmentCompat {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    public static DeleteHistoryDialogFragmentCompat newInstance(String key){
        final DeleteHistoryDialogFragmentCompat fragment = new DeleteHistoryDialogFragmentCompat();
        final Bundle bundle = new Bundle(1);
        bundle.putString(ARG_KEY, key);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onDialogClosed(boolean positiveResult) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("delete", positiveResult);
        editor.apply();
    }
}
