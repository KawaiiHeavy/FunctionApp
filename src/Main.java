import functions.FunctionPoint;
import functions.InappropriateFunctionPointException;

import functions.LinkedListTabulatedFunction;
import functions.ArrayTabulatedFunction;

public class Main {
    public static void main(String[] args) throws InappropriateFunctionPointException {
        ArrayTabulatedFunction func1;
        double[] values = {0, -1, 1};

        LinkedListTabulatedFunction func2 = new LinkedListTabulatedFunction(-10, 3, values);
        func2.printFunction();
        System.out.println(func2.getFunctionValue(4));

//        func1 = new ArrayTabulatedFunction(-5, 5, values);
//
//        func1.printFunction();
//        System.out.println(func1.getFunctionValue(4));
//
//
//        System.out.println("Left border " + func1.getLeftDomainBorder());
//        System.out.println("Right border " + func1.getRightDomainBorder());
//
//        func1.printFunction();
//        System.out.println();
//
//        System.out.println("Y = " + func1.getFunctionValue(2.5));
//        System.out.println("Points count: " + func1.getPointsCount());
//
//        FunctionPoint p1 = new FunctionPoint(2.5, 5.0);
//        func1.addPoint(p1);
//        System.out.println("Adding new point with coords (2.5 , 5.0)");
//        System.out.println("Points count: " + func1.getPointsCount());
//
//        func1.printFunction();
//        System.out.println();
//
//        FunctionPoint p2 = new FunctionPoint(1.5, 5.0);
//        System.out.println("Adding new point with coords (1.5 , 5.0)");
//        func1.addPoint(p2);
//        System.out.println("Points count: " + func1.getPointsCount());
//
//        func1.printFunction();
//        System.out.println();
//
//        func1.deletePoint(4);
//        System.out.println("Deleting point with index 4");
//        System.out.println("Points count: " + func1.getPointsCount());
//        func1.printFunction();
//
//        func1.deletePoint(4);
//        System.out.println("Deleting point with index 4");
//        System.out.println("Points count: " + func1.getPointsCount());
//
//        func1.printFunction();
    }
}
