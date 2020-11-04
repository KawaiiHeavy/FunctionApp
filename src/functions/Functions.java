package functions;

import functions.meta.*;

public class Functions {
    private Functions(){}

    public static Function shift(Function func, double shiftX, double shiftY) { return new Shift(func, shiftX, shiftY); }
    public static Function scale(Function func, double scaleX, double scaleY) { return new Scale(func, scaleX, scaleY); }
    public static Function power(Function func, double pow) { return new Power(func, pow); }
    public static Function sum(Function func1, Function func2) { return new Sum(func1, func2); }
    public static Function mult(Function func1, Function func2) { return new Mult(func1, func2); }
    public static Function composition(Function func1, Function func2) { return new Composition(func1, func2); }
}
