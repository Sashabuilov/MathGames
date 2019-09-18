package com.builov.mathgames.MathActions;

import java.util.Random;

public class MathCore {
    private Random mRandom = new Random();

    private int frist;
    private int second;
    private int mathSignInt;

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
                this.frist = mRandom.nextInt(5 + 1);
                this.second = mRandom.nextInt(5 + 1);
                this.mathSignInt = mRandom.nextInt(3 + 1);
                break;
            case 4:
                this.frist = mRandom.nextInt(10 + 1);
                this.second = mRandom.nextInt(10 + 1);
                this.mathSignInt = mRandom.nextInt(3 + 1);
                break;
        }
    }

}