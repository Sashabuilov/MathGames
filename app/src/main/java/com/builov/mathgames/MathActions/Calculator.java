package com.builov.mathgames.MathActions;

public class Calculator {
    static int calculate(int first, int second, int mathSign) {

        switch (mathSign) {
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
}

//public class MathCalculation {
//    private int answer;
//    private int first;
//    private int second;
//    private int mathSign;
//
//    public MathCalculation(int first, int second, int mathSign) {
//        this.first = first;
//        this.second = second;
//        this.mathSign = mathSign;
//    }
//
//    public int getAnswer() {
//
//
//        switch (mathSign) {
//
//            case 0:
//                answer = first + second;
//                break;
//            case 1:
//                answer = first - second;
//                break;
//            case 2:
//                answer = first * second;
//                break;
//            case 3:
//                answer = first / second;
//        }
//        return answer;
//    }
//}
