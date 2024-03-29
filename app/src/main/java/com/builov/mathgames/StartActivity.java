package com.builov.mathgames;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Objects;
public class StartActivity extends AppCompatActivity {


    int difficulty = 1;
    Button mButtonOk;
    Switch mSwitch;
    private String theme;
    TextView tvHeader;

    RadioGroup mRadioGroup;
    RadioGroup mRadioGroup2;
    RadioGroup mRadioGroup3;
    RadioButton radioButtonEasy;
    RadioButton radioButtonNormal;
    RadioButton radioButtonHard;
    RadioButton radioButtonVeryHard;
    RadioButton radioButtonMultiplicationTable;

    ConstraintLayout mConstraintLayout;
    LinearLayout maintHolder;

    CardView mCardViewTop;
    CardView mCardViewBottom;

    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        Objects.requireNonNull(getSupportActionBar()).hide();
        initUI();
        initView();

        settings = getPreferences(MODE_PRIVATE);
        theme=settings.getString("themeSettings","White");

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    themeSwithcher(isChecked);
                    settings = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("themeSettings",theme);
                    editor.apply();
                } else {
                    themeSwithcher(isChecked);
                    settings = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("themeSettings",theme);
                    editor.apply();
                }
            }
        });
        if (theme.equals("Black")){mSwitch.setChecked(true);} else {mSwitch.setChecked(false);}
    }

    private void initUI() {
        mButtonOk = findViewById(R.id.buttonOK);

        radioButtonEasy = findViewById(R.id.rbEasy);
        radioButtonEasy.setChecked(true);
        radioButtonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = 1;
                mRadioGroup2.clearCheck();
                mRadioGroup3.clearCheck();

            }
        });

        radioButtonNormal = findViewById(R.id.rbNormal);
        radioButtonNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = 2;
                mRadioGroup2.clearCheck();
                mRadioGroup3.clearCheck();
            }
        });

        radioButtonHard = findViewById(R.id.rbHard);
        radioButtonHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = 3;
                mRadioGroup.clearCheck();
                mRadioGroup3.clearCheck();

            }
        });

        radioButtonVeryHard = findViewById(R.id.rbVeryHard);
        radioButtonVeryHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = 4;
                mRadioGroup.clearCheck();
                mRadioGroup3.clearCheck();
            }
        });

        radioButtonMultiplicationTable = findViewById(R.id.rbMultiplicationTable);
        radioButtonMultiplicationTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = 5;
                mRadioGroup.clearCheck();
                mRadioGroup2.clearCheck();
            }
        });

        mSwitch = findViewById(R.id.switch1);
        mConstraintLayout = findViewById(R.id.configBase);
        mRadioGroup = findViewById(R.id.radioGroup1);
        mRadioGroup2 = findViewById(R.id.radioGroup2);
        mRadioGroup3 = findViewById(R.id.radioGroup3);
        maintHolder = findViewById(R.id.main_Holder);
        mCardViewTop = findViewById(R.id.CardHolderTop);
        mCardViewBottom = findViewById(R.id.CardHolderBottom);
        tvHeader = findViewById(R.id.tvHeader);
    }

    private void initView() {
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                intent.putExtra("difficulty", difficulty);
                intent.putExtra("theme",theme);
                startActivity(intent);
            }
        });
    }

    private void themeSwithcher(Boolean isChecked) {
        if (isChecked) {
            mConstraintLayout.setBackgroundColor(getResources().getColor(R.color.colorMaterialBlack));
            mSwitch.setTextColor(Color.WHITE);
            tvHeader.setTextColor(Color.WHITE);
            mRadioGroup.setBackgroundColor(getResources().getColor(R.color.colorMaterialBlack));
            mCardViewTop.setBackgroundColor(getResources().getColor(R.color.colorMaterialBlack));
            mCardViewBottom.setBackgroundColor(getResources().getColor(R.color.colorMaterialBlack));
            radioButtonEasy.setTextColor(Color.WHITE);
            radioButtonNormal.setTextColor(Color.WHITE);
            radioButtonHard.setTextColor(Color.WHITE);
            radioButtonVeryHard.setTextColor(Color.WHITE);
            radioButtonMultiplicationTable.setTextColor(Color.WHITE);
            mButtonOk.setTextColor(Color.WHITE);
            theme = "Black";
        } else {
            mConstraintLayout.setBackgroundColor(Color.WHITE);
            mSwitch.setTextColor(Color.BLACK);
            tvHeader.setTextColor(Color.BLACK);
            mRadioGroup.setBackgroundColor(Color.WHITE);
            mCardViewTop.setBackgroundColor(Color.WHITE);
            mCardViewBottom.setBackgroundColor(Color.WHITE);
            radioButtonEasy.setTextColor(Color.BLACK);
            radioButtonNormal.setTextColor(Color.BLACK);
            radioButtonHard.setTextColor(Color.BLACK);
            radioButtonVeryHard.setTextColor(Color.BLACK);
            radioButtonMultiplicationTable.setTextColor(Color.BLACK);
            mButtonOk.setTextColor(Color.BLACK);

            theme = "White";
        }
    }
}
