package main;

import java.util.function.DoubleUnaryOperator;

public class ThreadedCalculator extends Thread {

    private IntegralCalculator calculator;
    private Main main;

    public ThreadedCalculator(double a, double b, int n, DoubleUnaryOperator f, Main main) {
        calculator = new IntegralCalculator(a,b,n,f);
        this.main = main;
    }

    public void run() {
        double res = calculator.calc();
        main.sendResult(res);
    }
}
