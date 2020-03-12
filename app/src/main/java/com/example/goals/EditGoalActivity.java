package com.example.goals;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditGoalActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextTarget;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);
        editTextName = findViewById(R.id.et_new_goal_name);
        editTextTarget = findViewById(R.id.et_new_goal_target);
        button = findViewById(R.id.bt_new_goal);

        button.setOnClickListener(v -> {
            Intent reply = new Intent();
            Editable name = editTextName.getText();
            Editable target = editTextTarget.getText();
            if (TextUtils.isEmpty(name) && TextUtils.isEmpty(target)){ // ToDo: Check for duplicate names
                setResult(RESULT_CANCELED, reply);
            } else {
                reply.putExtra("name", name.toString());
                try {
                    int num = Integer.parseInt(target.toString());
                    reply.putExtra("target", num);
                }catch (NumberFormatException e){
                    reply.putExtra("target", 0);
                }
                int pos = getIntent().getIntExtra("position", -1);
                reply.putExtra("position", pos);
                setResult(RESULT_OK, reply);
            }
            finish();
        });

    }
}

