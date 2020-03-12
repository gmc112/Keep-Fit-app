package com.example.goals.preferences;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.preference.DialogPreference;

import com.example.goals.R;

public class DeleteHistoryPreference extends DialogPreference {

    public DeleteHistoryPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public DeleteHistoryPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DeleteHistoryPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DeleteHistoryPreference(Context context) {
        super(context);
    }


}
