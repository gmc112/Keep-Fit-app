package com.example.goals.preferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.preference.DialogPreference;

import com.example.goals.R;


public class DatePreference extends DialogPreference {
    private long date;
    private int dialogLayoutResource = R.layout.preference_dialog_date;

    public DatePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public DatePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DatePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DatePreference(Context context) {
        super(context);
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
        persistLong(date);

    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index){
        String def = a.getString(index);
        long value = -1;
        try {
            value = Long.parseLong(def);
        }catch (NumberFormatException e){
            return value;
        }
        return value;
    }
    @Override
    protected void onSetInitialValue(Object defaultValue){
        long val = getPersistedLong(1583385277578L);// ToDo don't hard code this use like (long) defaultValue
        setDate(val);
    }

    @Override
    public int getDialogLayoutResource(){
        return dialogLayoutResource;
    }
}
