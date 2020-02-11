package com.example.goals;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateHomeActivity extends AppCompatActivity {
    private EditText editTextTarget;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_home);

        editTextTarget = findViewById(R.id.et_home_update);
        button = findViewById(R.id.bt_home_update);

        button.setOnClickListener(v -> {
            Intent reply = new Intent();
            Editable steps = editTextTarget.getText();
            if (TextUtils.isEmpty(steps)){
                setResult(RESULT_CANCELED, reply);
            } else {
                int value = Integer.parseInt(steps.toString());
                reply.putExtra("steps", value);
                setResult(RESULT_OK, reply);
            }
            finish();
        });
    }
}
