package com.builov.mathgames.MathActions;

import java.util.Random;

public class MathCore {
    private Random mRandom = new Random();

    private int frist;
    private int second;
    private int mathSignInt;
    private int tempAnswer;
    private int difficulty;
    private int hintCount;
    private int numberOfCorrectAnswers = 0;
    private int the_number_of_correct_answers_for_hard_game=0;
    private Boolean newGame=false;

    private Boolean reset;

    public static int calculate(int first, int second, int mathSignInt) {
        switch (mathSignInt) {
            case 0:
                return first + second;
            case 1:
                return first - second;
            case 2:
                return first * second;
            case 3:
                return first / second;
        }
        return 0;
    }

   public static Boolean needRandom(int first, int second, int mathSign) {
        if (first - second < 0 && mathSign == 1) {
            return true;
        } else if (second == 0 || first == 0) {
            return true;
        } else if (first % second != 0 && mathSign == 3) {
            return true;
        }
        return false;
    }

    public String getMathSign() {
        switch (mathSignInt) {
            case 0:
                return "+";
            case 1:
                return "-";
            case 2:
                return "*";
            case 3:
                return "/";
        }
        return "";
    }

    public int getFrist() {
        return frist;
    }

    public int getSecond() {
        return second;
    }

    public int getMathSignInt() {
        return mathSignInt;
    }



    public int getTempAnswer() {
        return tempAnswer;
    }

    public void setTempAnswer(int tempAnswer) {
        this.tempAnswer = tempAnswer;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }


    public int getHintCount() {
        return hintCount;
    }

    public void setHintCount(int hintCount) {
        this.hintCount = hintCount;
    }


    public int getNumberOfCorrectAnswers() {
        return numberOfCorrectAnswers;
    }

    public void setNumberOfCorrectAnswers(int numberOfCorrectAnswers) {
        this.numberOfCorrectAnswers = numberOfCorrectAnswers;
    }

    public int getThe_number_of_correct_answers_for_hard_game() {
        return the_number_of_correct_answers_for_hard_game;
    }

    public void setThe_number_of_correct_answers_for_hard_game(int the_number_of_correct_answers_for_hard_game) {
        this.the_number_of_correct_answers_for_hard_game = the_number_of_correct_answers_for_hard_game;
    }

    public Boolean getNewGame() {
        return newGame;
    }

    public void setNewGame(Boolean newGame) {
        this.newGame = newGame;
    }

    public Boolean getReset() {
        return reset;
    }

    public void setReset(Boolean reset) {
        this.reset = reset;
    }


    public void mathRandomizer(int difficulty) {

        switch (difficulty) {
            case 1:
                this.frist = mRandom.nextInt(5 + 1);
                this.second = mRandom.nextInt(5 + 1);
                this.mathSignInt = mRandom.nextInt(1 + 1);
                break;
            case 2:
                this.frist = mRandom.nextInt(10 + 1);
                this.second = mRandom.nextInt(10 + 1);
                this.mathSignInt = mRandom.nextInt(1 + 1);
                break;
            case 3:
                this.frist = mRandom.nextInt(10 + 1);
                this.second = mRandom.nextInt(10 + 1);
                this.mathSignInt = mRandom.nextInt(3 + 1);
                break;
            case 4:
                this.frist = mRandom.nextInt(20 + 1);
                this.second = mRandom.nextInt(20 + 1);
                this.mathSignInt = mRandom.nextInt(3 + 1);
                break;
            case 5:
                this.frist = mRandom.nextInt(9+1);
                this.second = mRandom.nextInt(9+1);
                this.mathSignInt = 2;
                break;

        }
    }

}