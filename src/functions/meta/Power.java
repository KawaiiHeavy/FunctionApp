package functions.meta;

import functions.Function;

public class Power implements Function {

    Function func;
    double pow;

    public Power(Function func, double pow){
        this.func = func;
        this.pow = pow;
    }

    @Override
    public double getLeftDomainBorder() {
        return func.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return func.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.pow(func.getFunctionValue(x), pow);
    }
}
