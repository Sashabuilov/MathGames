package com.builov.mathgames.MathActions;

public class MathCalculation {
    private int answer;
    private int first;
    private int second;
    private int mathSign;

    public MathCalculation(int first, int second, int mathSign) {
        this.first = first;
        this.second = second;
        this.mathSign = mathSign;
    }

    public int getAnswer() {


        switch (mathSign) {

            case 0:
                answer = first + second;
                break;
            case 1:
                answer = first - second;
                break;
            case 2:
                answer = first * second;
                break;
            case 3:
                answer = first / second;
        }
        return answer;
    }


}
