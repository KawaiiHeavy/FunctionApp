import functions.FunctionPoint;
import functions.InappropriateFunctionPointException;

import functions.LinkedListTabulatedFunction;
import functions.ArrayTabulatedFunction;

public class Main {
    public static void main(String[] args) throws InappropriateFunctionPointException {
        ArrayTabulatedFunction func;
        double[] values = {0, -1, 1};

        func = new ArrayTabulatedFunction(-5, 5, values);

        func.printFunction();
        System.out.println(func.getFunctionValue(4));


        System.out.println("Left border " + func.getLeftDomainBorder());
        System.out.println("Right border " + func.getRightDomainBorder());

        func.printFunction();
        System.out.println();

        System.out.println("Y = " + func.getFunctionValue(2.5));
        System.out.println("Points count: " + func.getPointsCount());

        FunctionPoint p1 = new FunctionPoint(2.5, 5.0);
        func.addPoint(p1);
        System.out.println("Adding new point with coords (2.5 , 5.0)");
        System.out.println("Points count: " + func.getPointsCount());

        func.printFunction();
        System.out.println();

        FunctionPoint p2 = new FunctionPoint(1.5, 5.0);
        System.out.println("Adding new point with coords (1.5 , 5.0)");
        func.addPoint(p2);
        System.out.println("Points count: " + func.getPointsCount());

        func.printFunction();
        System.out.println();

        func.deletePoint(4);
        System.out.println("Deleting point with index 4");
        System.out.println("Points count: " + func.getPointsCount());

        func.printFunction();
    }
}
