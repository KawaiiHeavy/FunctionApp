package functions.meta;

import functions.Function;

public class Mult implements Function {

    Function func1;
    Function func2;

    public Mult(Function a, Function b){
        this.func1 = a;
        this.func2 = a;
    }

    @Override
    public double getLeftDomainBorder() {
        return Math.max(func1.getLeftDomainBorder(), func2.getLeftDomainBorder());
    }

    @Override
    public double getRightDomainBorder() {
        return Math.max(func1.getRightDomainBorder(), func2.getRightDomainBorder());
    }

    @Override
    public double getFunctionValue(double x) {
        return func1.getFunctionValue(x) * func2.getFunctionValue(x);
    }
}
