package functions.meta;

import functions.Function;

public class Scale implements Function {
    Function func;
    double coefX;
    double coefY;

    public Scale(Function func, double coefX, double coefY){
        this.func = func;
        this.coefX = coefX;
        this.coefY = coefY;
    }

    @Override
    public double getLeftDomainBorder() {
        return func.getLeftDomainBorder() * coefX;
    }

    @Override
    public double getRightDomainBorder() {
        return func.getRightDomainBorder() * coefY;
    }

    @Override
    public double getFunctionValue(double x) {
        return func.getFunctionValue(x / coefX) * coefY;
    }
}
