package main;

public class Main {

    double result;
    int finished;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        double a = 0;
        double b = Math.PI;
        int n = 100_000_000;
//        IntegralCalculator calculator = new IntegralCalculator(a, b, n, MyFunction::f);
//        long start = System.currentTimeMillis();
//        double res = calculator.calc();
//        long finish = System.currentTimeMillis();
//        System.out.println(res);
//        System.out.println(finish-start);
        int nThreads = 50;
        result = 0;
        finished = 0;
        long start = System.currentTimeMillis();
        double delta = (b-a)/nThreads;
        for (int i = 0; i < nThreads; i++) {
            ThreadedCalculator calculator = new ThreadedCalculator(a + i * delta, a + (i + 1) * delta, n / nThreads, MyFunction::f, this);
            calculator.start();
        }
        synchronized (this) {
            try {
                while (finished < nThreads) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long finish = System.currentTimeMillis();
        System.out.println(result);
        System.out.println(finish-start);
    }

    public synchronized void sendResult(double res) {
        result += res;
        finished++;
        notify();
    }
}
