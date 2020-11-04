package functions.basic;

import functions.Function;

public class Log implements Function {

    private double base;

    public Log(double x){
        if (x > 0 && x != 1){
            this.base = x;
        }
    }

    @Override
    public double getLeftDomainBorder() {
        return Double.NEGATIVE_INFINITY;
    }

    @Override
    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.log(x);
    }
}
