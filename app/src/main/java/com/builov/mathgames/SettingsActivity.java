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

    private void initUI(){
        mButtonOk = findViewById(R.id.buttonOK);
        RadioButton radioButtonEasy = findViewById(R.id.rbEasy);
        radioButtonEasy.setOnClickListener(radioButtonClickListener);
        radioButtonEasy.setChecked(true);
        RadioButton radioButtonNormal = findViewById(R.id.rbNormal);
        radioButtonNormal.setOnClickListener(radioButtonClickListener);
        RadioButton radioButtonHard = findViewById(R.id.rbHard);
        radioButtonHard.setOnClickListener(radioButtonClickListener);
        RadioButton radioButtonVeryHard = findViewById(R.id.rbVeryHard);
        radioButtonVeryHard.setOnClickListener(radioButtonClickListener);
    }
    private void initView(){
        radioButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton radioButton = (RadioButton) v;
                switch (radioButton.getId()) {
                    case R.id.rbEasy:
                        difficulty = 1;
                        break;
                    case R.id.rbNormal:
                        difficulty = 2;
                        break;
                    case R.id.rbHard:
                        difficulty = 3;
                        break;
                    case R.id.rbVeryHard:
                        difficulty = 4;
                        break;
                    default:
                        break;
                }
            }
        };

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
