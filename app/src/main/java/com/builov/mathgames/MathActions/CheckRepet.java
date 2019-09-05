package com.builov.mathgames.MathActions;

public class CheckRepet {

    private int first;
    private int second;
    private int intMathSign;

    private Boolean needRandom = false;

    public CheckRepet(int first, int second, int intMathSign) {
        this.first = first;
        this.second = second;
        this.intMathSign = intMathSign;
    }

    public Boolean getNeedRandom() {
        return needRandom;
    }

    public void check(){
        needRandom=false;
        if (first - second < 0 && intMathSign == 1) { needRandom = true;
        } else if (second == 0||first==0) { needRandom = true;
        } else if (first % second != 0 && intMathSign == 3) { needRandom = true;
        }
    }
}
