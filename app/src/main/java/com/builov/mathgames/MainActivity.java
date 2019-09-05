package com.builov.mathgames;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.builov.mathgames.MathActions.CheckRepet;
import com.builov.mathgames.MathActions.MathCalculation;
import com.builov.mathgames.MathActions.MathRandomizer;

public class MainActivity extends AppCompatActivity {

    TextView tvFirstMathNumber;
    TextView tvSecondMathNumber;
    TextView tvMathSign;
    TextView tvAnswer;
    EditText mEditTextAnswer;
    Button mButtonOk;
    Button mButtonHelp;
    Button mButtonDelete;
    Button[] btn = new Button[10];
    
    private int firstNumber = 0;
    private int secondNumber = 0;
    private int intMathSign = 0;
    private int tempAnswer = 0;
    private int difficulty;
    Boolean reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reset = true;
        initUI();
        difficulty = getIntent().getIntExtra("difficulty", 0);
        initMathActions();
        initButtonActions();
    }

    public void initMathActions() {

        Boolean needRandom;

        MathRandomizer randomizer = new MathRandomizer(difficulty);
        firstNumber = randomizer.getFrist();
        secondNumber = randomizer.getSecond();
        intMathSign = randomizer.getMathSignInt();
        String mathSign = randomizer.getMathSign();

        CheckRepet checkRepet = new CheckRepet(firstNumber, secondNumber, intMathSign);
        checkRepet.check();
        needRandom = checkRepet.getNeedRandom();
        if (needRandom) {
            initMathActions();
        } else {
            tvFirstMathNumber.setText(Integer.toString(firstNumber));
            tvSecondMathNumber.setText(Integer.toString(secondNumber));
            tvMathSign.setText(mathSign);
        }
    }

    private void initUI() {
        tvFirstMathNumber = findViewById(R.id.tvFirstNumber);
        tvSecondMathNumber = findViewById(R.id.tvSecondNumber);
        tvMathSign = findViewById(R.id.tvMathSign);
        //tvMotivation = findViewById(R.id.tvMotivation);
        tvAnswer = findViewById(R.id.tvAnswer);

        mButtonOk = findViewById(R.id.buttonOK);
        mButtonHelp = findViewById(R.id.buttonHelp);
        mButtonDelete = findViewById(R.id.buttonDelete);
        //mButtonMore = findViewById(R.id.buttonMore);
        mEditTextAnswer = findViewById(R.id.editText_Answer);
        btn[0] = findViewById(R.id.btn0);
        btn[1] = findViewById(R.id.btn1);
        btn[2] = findViewById(R.id.btn2);
        btn[3] = findViewById(R.id.btn3);
        btn[4] = findViewById(R.id.btn4);
        btn[5] = findViewById(R.id.btn5);
        btn[6] = findViewById(R.id.btn6);
        btn[7] = findViewById(R.id.btn7);
        btn[8] = findViewById(R.id.btn8);
        btn[9] = findViewById(R.id.btn9);
    }

    private void initButtonActions() {

        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvAnswer.getText().toString().equals("?")) {
                    Toast.makeText(getApplicationContext(), "Введите ответ", Toast.LENGTH_SHORT).show();
                } else {
                    MathCalculation calculation = new MathCalculation(firstNumber, secondNumber, intMathSign);
                    tempAnswer = calculation.getAnswer();
                    int answer = Integer.parseInt(tvAnswer.getText().toString());
                    if (answer == tempAnswer) {
                        mEditTextAnswer.setText("Правильно");
                        tvAnswer.setText("?");
                        reset = true;
                        initMathActions();
                    } else
                        mEditTextAnswer.setText("Не правильно!");
                    reset = true;
                }
            }
        });

        //метод ввода чисел
        for (int i = 0; i <= 9; i++) {
            onClick(btn[i]);
        }

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAnswer.setText("?");
                reset = true;
                mEditTextAnswer.setText("");
            }
        });

        mButtonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MathCalculation calculation = new MathCalculation(firstNumber, secondNumber, intMathSign);
                tempAnswer = calculation.getAnswer();
                Toast.makeText(getApplicationContext(),Integer.toString(tempAnswer),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClick(final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(button.getText().toString());
                mEditTextAnswer.setText("Нажмите ОК");

                if (reset) {
                    tvAnswer.setText("");
                    tvAnswer.setText(Integer.toString(i));
                    reset = false;
                } else {
                    tvAnswer.setText(tvAnswer.getText() + button.getText().toString());
                }

            }
        });
    }
}
