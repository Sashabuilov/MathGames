package com.builov.mathgames.MathActions;

import java.util.Random;

public class MathRandomizer {

    private Random mRandom = new Random();

    private int frist;
    private int second;
    private int mathSignInt;
    private String mathSign;

    public MathRandomizer(int difficulty) {

        switch (difficulty){
            case 1:
                this.frist = mRandom.nextInt(5 + 1);
                this.second = mRandom.nextInt(5 + 1);
                this.mathSignInt = mRandom.nextInt(1+1);
                break;
            case 2:
                this.frist = mRandom.nextInt(10 + 1);
                this.second = mRandom.nextInt(10 + 1);
                this.mathSignInt = mRandom.nextInt(1+1);
                break;
            case 3:
                this.frist = mRandom.nextInt(5 + 1);
                this.second = mRandom.nextInt(5 + 1);
                this.mathSignInt = mRandom.nextInt(3+1);
                break;
            case 4:
                this.frist = mRandom.nextInt(10 + 1);
                this.second = mRandom.nextInt(10 + 1);
                this.mathSignInt = mRandom.nextInt(3+1);
                break;

        }
    }

    public int getFrist() {
        return frist;
    }

    public String getMathSign() {
        switch (mathSignInt) {
            case 0:
                mathSign = "+";
                break;
            case 1:
                mathSign = "-";
                break;
            case 2:
                mathSign = "*";
                break;
            case 3:
                mathSign = "/";
                break;
        }
        return mathSign;
    }

    public int getSecond() {
        return second;
    }

    public int getMathSignInt() {
        return mathSignInt;
    }
}
