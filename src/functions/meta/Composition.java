package functions.meta;

import functions.Function;

public class Composition implements Function {

    private Function func1;
    private Function func2;

    public Composition(Function func1, Function func2){
        this.func1 = func1;
        this.func2 = func2;
    }

    @Override
    public double getLeftDomainBorder() {
        return func1.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return func1.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return func1.getFunctionValue(func2.getFunctionValue(x));
    }
}
