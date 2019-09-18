package com.builov.mathgames;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.builov.mathgames.MathActions.MathCore;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout mConstraintLayout;

    TextView tvFirstMathNumber;
    TextView tvSecondMathNumber;
    TextView tvMathSign;
    TextView tvAnswer;
    TextView tvHintCount;
    TextView tvVersion;
    TextView tvEquals;
    TextView tvHintTitle;

    EditText mEditTextAnswer;

    Button mButtonOk;
    Button mButtonHelp;
    Button mButtonDelete;
    Button[] btn = new Button[10];

    private int firstNumber;
    private int secondNumber;
    private int intMathSign;
    private int tempAnswer;
    private int difficulty;
    private int hintCount;
    private int numberOfCorrectAnswers = 0;
    private Thread secondThread;
    private Handler mHandler = new Handler();
    private Boolean reset;
    MathCore mathCore = new MathCore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        String theme = getIntent().getStringExtra("theme");

        String VERSION = "0.6";
        reset = true;
        initUI();


        if (theme.equals("Black")) {
            setBlackTheme();
        } else {
            setWhiteTheme();
        }

        mHandler = new Handler() {

            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mEditTextAnswer.setText("");
            }
        };

        tvVersion.setText("v." + VERSION);
        difficulty = getIntent().getIntExtra("difficulty", 0);
        mathCore.mathRandomizer(difficulty);

        switch (difficulty) {
            case 1:
                hintCount = 10;
                tvHintCount.setText(Integer.toString(hintCount));
                break;
            case 2:
                hintCount = 5;
                tvHintCount.setText(Integer.toString(hintCount));
                break;
            case 3:
                hintCount = 3;
                tvHintCount.setText(Integer.toString(hintCount));
                break;
            case 4:
                hintCount = 0;
                tvHintCount.setText("ПОДСКАЗКИ НЕ ДОСТУПНЫ");
                break;
        }
        initMathActions();
        initButtonActions();
    }

    //методы получения чисел
    public void initMathActions() {

        Boolean needRandom;

        mathCore.mathRandomizer(difficulty);
        firstNumber = mathCore.getFrist();
        secondNumber = mathCore.getSecond();
        intMathSign = mathCore.getMathSignInt();
        String mathSign = mathCore.getMathSign();


        needRandom = MathCore.needRandom(firstNumber, secondNumber, intMathSign);
        if (needRandom) {
            initMathActions();
        } else {
            tvFirstMathNumber.setText(Integer.toString(firstNumber));
            tvSecondMathNumber.setText(Integer.toString(secondNumber));
            tvMathSign.setText(mathSign);
        }
    }

    //инициализация UI элементов
    private void initUI() {

        //"Связывание" UI элементов с обьектами кода
        tvFirstMathNumber = findViewById(R.id.tvFirstNumber);
        tvSecondMathNumber = findViewById(R.id.tvSecondNumber);
        tvMathSign = findViewById(R.id.tvMathSign);
        tvAnswer = findViewById(R.id.tvAnswer);
        tvVersion = findViewById(R.id.tvVersion);
        tvHintCount = findViewById(R.id.tvHintCount);

        mButtonOk = findViewById(R.id.buttonOK);
        mButtonHelp = findViewById(R.id.buttonHelp);
        mButtonDelete = findViewById(R.id.buttonDelete);
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

        //элементы для смены темы
        mConstraintLayout = findViewById(R.id.main_Activity_Constaint);
        tvEquals = findViewById(R.id.textView5);
        tvHintTitle = findViewById(R.id.tvHintTitle);
    }

    //обработка нажатия UI кнопок
    private void initButtonActions() {

        //кнопка ОК
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //если юзер пытается нажать ОК без введенного ответа
                if (tvAnswer.getText().toString().equals("?")) {
                    Snackbar.make(v, "ВВЕДИТЕ ОТВЕТ", Snackbar.LENGTH_SHORT).show();
                } else {
                    //получаем ответ от вычислений
                    tempAnswer = MathCore.calculate(firstNumber, secondNumber, intMathSign);
                    //сверяем ответ от вычислений с тем что ввел юзер
                    int answer = Integer.parseInt(tvAnswer.getText().toString());
                    //если ответы равны, значит введен правильный ответ....
                    if (answer == tempAnswer) {
                        mEditTextAnswer.setText("Правильно");
                        tvAnswer.setText("?");

                        numberOfCorrectAnswers++;
                        if (numberOfCorrectAnswers == 5) {
                            hintCount = hintCount + 1;
                            numberOfCorrectAnswers = 0;
                            Snackbar.make(v, "ПОДСКАЗКИ +1", Snackbar.LENGTH_SHORT).show();
                            tvHintCount.setText(Integer.toString(hintCount));
                        }

                        secondThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    TimeUnit.MILLISECONDS.sleep(700);
                                    mHandler.sendEmptyMessage(0);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        secondThread.start();
                        reset = true;
                        //....и запускается новый рандом и ожидается новый ввод ответа
                        initMathActions();
                    } else {

                        //если ответы от вычислений не равны, выводится сообщение что юзер ошибся
                        mEditTextAnswer.setText("Не правильно!");
                        numberOfCorrectAnswers = 0;
                        reset = true;
                    }
                }
            }
        });

        //метод обработки кнопок с цифрами
        for (int i = 0; i <= 9; i++) {
            onNumeralClick(btn[i]);
        }

        //кнопка УДАЛИТЬ
        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAnswer.setText("?");
                reset = true;
                mEditTextAnswer.setText("");
            }
        });

        //кнопка ПОДСКАЗКА
        mButtonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hintCount == 0) {
                    tvHintCount.setText("ПОДСКАЗКИ НЕ ДОСТУПНЫ");
                } else {
                    tempAnswer = MathCore.calculate(firstNumber, secondNumber, intMathSign);//calculation.getAnswer();
                    Snackbar.make(v, Integer.toString(tempAnswer), Snackbar.LENGTH_SHORT).show();
                    hintCount = hintCount - 1;
                    tvHintCount.setText(Integer.toString(hintCount));
                }
            }
        });
    }

    //обоаботка клика кнопок с цифрами
    private void onNumeralClick(final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(button.getText().toString());
                mEditTextAnswer.setText("Нажмите ОК");

                //проверка на ввод двух и более нулей (запрет ввода)
                if (tvAnswer.getText().toString().equals("0")) {
                    tvAnswer.setText(button.getText().toString());
                } else if (reset) {
                    tvAnswer.setText("");
                    tvAnswer.setText(Integer.toString(i));
                    reset = false;
                } else {
                    if (tvAnswer.length() <= 2) {
                        tvAnswer.setText(tvAnswer.getText() + button.getText().toString());
                    } else {
                        Snackbar.make(v, "СЛИШКОМ БОЛЬШОЕ ЧИСЛО", Snackbar.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    //обработка телефонной кнопки НАЗАД
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Выйти из приложения?").
                setPositiveButton("ДА", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                }).setNegativeButton("НЕТ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void setBlackTheme() {
        mConstraintLayout.setBackgroundColor(getResources().getColor(R.color.colorMaterialBlack));
        tvFirstMathNumber.setTextColor(Color.WHITE);
        tvMathSign.setTextColor(Color.WHITE);
        tvSecondMathNumber.setTextColor(Color.WHITE);
        mEditTextAnswer.setHintTextColor(Color.WHITE);
        mEditTextAnswer.setTextColor(Color.WHITE);
        tvEquals.setTextColor(Color.WHITE);
        tvHintTitle.setTextColor(Color.WHITE);
        tvHintCount.setTextColor(Color.WHITE);
        tvVersion.setTextColor(Color.WHITE);
    }

    private void setWhiteTheme() {
        mConstraintLayout.setBackgroundColor(Color.WHITE);
        tvFirstMathNumber.setTextColor(Color.BLACK);
        tvMathSign.setTextColor(Color.BLACK);
        tvSecondMathNumber.setTextColor(Color.BLACK);
        mEditTextAnswer.setHintTextColor(getResources().getColor(R.color.hintGray));
        mEditTextAnswer.setTextColor(Color.BLACK);
        tvEquals.setTextColor(Color.BLACK);
        tvHintTitle.setTextColor(Color.BLACK);
        tvHintCount.setTextColor(Color.BLACK);
        tvVersion.setTextColor(Color.BLACK);
    }
}
