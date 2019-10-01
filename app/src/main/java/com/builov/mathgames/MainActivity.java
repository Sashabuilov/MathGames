package com.builov.mathgames;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.CountDownTimer;
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
    TextView tvTimer;

    EditText mEditTextAnswer;

    Button mButtonOk;
    Button mButtonHelp;
    Button mButtonDelete;
    Button[] buttonWithNumbers = new Button[10];

    private Thread secondThread;
    private Handler mHandler = new Handler();
    private MathCore mathCore = new MathCore();

    //таймер и переменная для сброса таймера, для очень сложного режима
    private CountDownTimer mCountDownTimer;
    private long tempMilliSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //запускаем в фулскрине

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //прячем экшонбар
        Objects.requireNonNull(getSupportActionBar()).hide();

        //получаем от первой активности тему
        final String theme = getIntent().getStringExtra("theme");


        String VERSION = "0.7.2";
        mathCore.setReset(true);
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
        mathCore.setDifficulty(getIntent().getIntExtra("difficulty", 0));

        //difficulty = getIntent().getIntExtra("difficulty", 0);

        //запуск новой игры
        startNewGame();
    }

    //инициализация UI элементов
    private void initUI() {

        //TextView
        tvFirstMathNumber = findViewById(R.id.tvFirstNumber);
        tvSecondMathNumber = findViewById(R.id.tvSecondNumber);
        tvMathSign = findViewById(R.id.tvMathSign);
        tvAnswer = findViewById(R.id.tvAnswer);
        tvVersion = findViewById(R.id.tvVersion);
        tvHintCount = findViewById(R.id.tvHintCount);
        tvTimer = findViewById(R.id.tv_Timer);

        //Button
        mButtonOk = findViewById(R.id.buttonOK);
        mButtonHelp = findViewById(R.id.buttonHelp);
        mButtonDelete = findViewById(R.id.buttonDelete);
        mEditTextAnswer = findViewById(R.id.editText_Answer);
        buttonWithNumbers[0] = findViewById(R.id.btn0);
        buttonWithNumbers[1] = findViewById(R.id.btn1);
        buttonWithNumbers[2] = findViewById(R.id.btn2);
        buttonWithNumbers[3] = findViewById(R.id.btn3);
        buttonWithNumbers[4] = findViewById(R.id.btn4);
        buttonWithNumbers[5] = findViewById(R.id.btn5);
        buttonWithNumbers[6] = findViewById(R.id.btn6);
        buttonWithNumbers[7] = findViewById(R.id.btn7);
        buttonWithNumbers[8] = findViewById(R.id.btn8);
        buttonWithNumbers[9] = findViewById(R.id.btn9);

        //элементы для смены темы
        mConstraintLayout = findViewById(R.id.main_Activity_Constaint);
        tvEquals = findViewById(R.id.textView5);
        tvHintTitle = findViewById(R.id.tvHintTitle);
    }

    //запуск новой игры
    private void startNewGame() {
        mathCore.mathRandomizer(mathCore.getDifficulty());

        long timerMilliseconds = 30000;
        switch (mathCore.getDifficulty()) {
            case 1:
                mathCore.setHintCount(10);//hintCount = 10;
                tvHintCount.setText(Integer.toString(mathCore.getHintCount()));
                break;
            case 2:
                mathCore.setHintCount(5);//hintCount = 5;
                tvHintCount.setText(Integer.toString(mathCore.getHintCount()));
                break;
            case 3:
                mathCore.setHintCount(3);//hintCount = 3;
                tvHintCount.setText(Integer.toString(mathCore.getHintCount()));
                break;
            case 4:
                mathCore.setHintCount(0);//hintCount = 0;
                tvHintCount.setText(getString(R.string.noHintsAvailable));
                newTimer(timerMilliseconds);
                break;

            //таблица умножения
            case 5:
                mathCore.setHintCount(3);//hintCount = 3;
                tvHintCount.setText(Integer.toString(mathCore.getHintCount()));
                break;
        }
        initMathActions();
        initButtonActions();


    }

    //методы получения чисел
    public void initMathActions() {

        Boolean needRandom;

        mathCore.mathRandomizer(mathCore.getDifficulty());

        //String mathSign = mathCore.getMathSign();
        needRandom = mathCore.needRandom(mathCore.getFrist(), mathCore.getSecond(), mathCore.getMathSignInt());
        if (needRandom) {
            initMathActions();
        } else {

            tvFirstMathNumber.setText(Integer.toString(mathCore.getFrist()));
            tvSecondMathNumber.setText(Integer.toString(mathCore.getSecond()));
            tvMathSign.setText(mathCore.getMathSign());
            //tvFirstMathNumber.setText(Integer.toString(firstNumber));
            //tvSecondMathNumber.setText(Integer.toString(secondNumber));
            //tvMathSign.setText(mathSign);
        }
    }

    //обработка кнопок (ОК, УДАЛИТЬ, ПОДСКАЗКА)
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
                    mathCore.setTempAnswer(mathCore.calculate(mathCore.getFrist(), mathCore.getSecond(), mathCore.getMathSignInt()));//tempAnswer = MathCore.calculate(mathCore.getFrist(), mathCore.getSecond(), mathCore.getMathSignInt());

                    //сверяем ответ от вычислений с тем что ввел юзер
                    int answer = Integer.parseInt(tvAnswer.getText().toString());

                    //если ответы равны, значит введен правильный ответ....
                    if (answer == mathCore.getTempAnswer()) {//tempAnswer) {
                        mEditTextAnswer.setText("Правильно");
                        tvAnswer.setText("?");

                        //подсчет правильных ответов для очень сложного режима
                        if (mathCore.getDifficulty() == 4) {
                            mathCore.setThe_number_of_correct_answers_for_hard_game(mathCore.getNumberOfCorrectAnswers() + 1);//the_number_of_correct_answers_for_hard_game++;
                        }

                        //подсчет правильных ответов для получения бонусов на всех уровнях сложности
                        mathCore.setNumberOfCorrectAnswers(mathCore.getNumberOfCorrectAnswers() + 1);//numberOfCorrectAnswers++;

                        //если пользователь ответил несколько раз правильно, он получает бонус
                        if (mathCore.getNumberOfCorrectAnswers() == 4) {

                            //плюс 1 подсказка....
                            mathCore.setHintCount(mathCore.getHintCount() + 1);//hintCount = hintCount + 1;
                            mathCore.setNumberOfCorrectAnswers(0);//numberOfCorrectAnswers = 0;

                            if (mathCore.getDifficulty() == 4) {

                                mCountDownTimer.cancel();

                                //.... и плюс 10 секунд на очень сложном режиме
                                newTimer(tempMilliSeconds + 10000);
                            }

                            if (mathCore.getDifficulty() == 4) {
                                Snackbar.make(v, "ПОДСКАЗКИ +1 || Время + 10сек", Snackbar.LENGTH_SHORT).show();
                            } else {
                                Snackbar.make(v, "ПОДСКАЗКИ +1", Snackbar.LENGTH_SHORT).show();
                                tvHintCount.setText(Integer.toString(mathCore.getHintCount()));//hintCount));
                            }
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
                        mathCore.setReset(true);//reset = true;
                        //....и запускается новый рандом и ожидается новый ввод ответа
                        initMathActions();
                    } else {

                        //если ответы от вычислений не равны, выводится сообщение что юзер ошибся
                        mEditTextAnswer.setText("Не правильно!");
                        mathCore.setNumberOfCorrectAnswers(0);//numberOfCorrectAnswers = 0;
                        mathCore.setReset(true);//reset = true;
                    }
                }
            }
        });

        //метод обработки кнопок с цифрами
        for (int i = 0; i <= 9; i++) {
            onNumeralButtonClick(buttonWithNumbers[i]);
        }

        //кнопка УДАЛИТЬ
        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAnswer.setText("?");
                mathCore.setReset(true);//reset = true;
                mEditTextAnswer.setText("");
            }
        });

        //кнопка ПОДСКАЗКА
        mButtonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mathCore.getHintCount() == 0) {
                    tvHintCount.setText("ПОДСКАЗКИ НЕ ДОСТУПНЫ");
                } else {
                    mathCore.setTempAnswer(mathCore.calculate(mathCore.getFrist(), mathCore.getSecond(), mathCore.getMathSignInt()));//tempAnswer = MathCore.calculate(mathCore.getFrist(), mathCore.getSecond(), mathCore.getMathSignInt());//calculation.getAnswer();
                    Snackbar.make(v, Integer.toString(mathCore.getTempAnswer()), Snackbar.LENGTH_SHORT).show();
                    mathCore.setHintCount(mathCore.getHintCount() - 1);// hintCount = hintCount - 1;
                    tvHintCount.setText(Integer.toString(mathCore.getHintCount()));//hintCount));
                }
            }
        });
    }

    //обработка клика кнопок с цифрами
    private void onNumeralButtonClick(final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mathCore.getNewGame()) {
                    enable_disable_Buttons(true);
                    buttonWithNumbers[0].setText("0");
                    mathCore.setNewGame(false);//newGame=false;
                    startNewGame();
                } else {


                    int i = Integer.parseInt(button.getText().toString());
                    mEditTextAnswer.setText("Нажмите ОК");

                    //проверка на ввод двух и более нулей (запрет ввода)
                    if (tvAnswer.getText().toString().equals("0")) {
                        tvAnswer.setText(button.getText().toString());
                    } else if (mathCore.getReset()) {
                        tvAnswer.setText("");
                        tvAnswer.setText(Integer.toString(i));
                        mathCore.setReset(false);//reset = false;
                    } else {
                        if (tvAnswer.length() <= 2) {
                            tvAnswer.setText(tvAnswer.getText() + button.getText().toString());
                        } else {
                            Snackbar.make(v, "СЛИШКОМ БОЛЬШОЕ ЧИСЛО", Snackbar.LENGTH_SHORT).show();
                        }
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

    //запуск таймера
    private void newTimer(long miliseconds) {

        mCountDownTimer = new CountDownTimer(miliseconds, 1000) {

            public void onTick(long millisUntilFinished) {
                tvTimer.setText(Long.toString(millisUntilFinished / 1000));
                tempMilliSeconds = millisUntilFinished;
            }

            public void onFinish() {
                tvTimer.setText("ИГРА ОКОНЧЕНА");
                mEditTextAnswer.setText("Правильных ответов: " + Integer.toString(mathCore.getThe_number_of_correct_answers_for_hard_game()));
                enable_disable_Buttons(false);
                buttonWithNumbers[0].setEnabled(true);
                buttonWithNumbers[0].setText(R.string.letsPlay);
                mathCore.setNewGame(true);//newGame=true;
            }

        }.start();

    }

    //включение/отключение кнопок
    private void enable_disable_Buttons(Boolean status) {
        for (int i = 1; i <= 9; i++) {
            buttonWithNumbers[i].setEnabled(status);
        }
        mButtonOk.setEnabled(status);
        mButtonDelete.setEnabled(status);
        mButtonHelp.setEnabled(status);
    }

    //установка черной темы
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

    //установка белой темы
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
