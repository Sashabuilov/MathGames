package com.builov.mathgames;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class SettingsActivity extends AppCompatActivity {


    int difficulty = 1;
    Button mButtonOk;
    View.OnClickListener radioButtonClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initUI();
        initView();

    }

    private void initUI() {
        mButtonOk = findViewById(R.id.buttonOK);

        RadioButton radioButtonEasy = findViewById(R.id.rbEasy);
        radioButtonEasy.setChecked(true);
        radioButtonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = 1;
            }
        });

        RadioButton radioButtonNormal = findViewById(R.id.rbNormal);
        radioButtonNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = 2;
            }
        });

        RadioButton radioButtonHard = findViewById(R.id.rbHard);
        radioButtonHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = 3;
            }
        });

        RadioButton radioButtonVeryHard = findViewById(R.id.rbVeryHard);
        radioButtonVeryHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty=4;
            }
        });
    }


    private void initView() {
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.putExtra("difficulty", difficulty);
                startActivity(intent);
            }
        });
    }
}
