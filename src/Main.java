import functions.*;
import functions.basic.*;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InappropriateFunctionPointException {
        double[] values = {-2, - 1, 0, 1, 2};
        LinkedListTabulatedFunction func = new LinkedListTabulatedFunction(-2, 4, values);
        System.out.println(func.hashCode());
        TabulatedFunction func1 = (TabulatedFunction) func.clone();
        func.printFunction();
        func1.printFunction();
        System.out.println(func.equals(func1));
        func.addPoint(new FunctionPoint(1.2, 7.0));
        func1.addPoint(new FunctionPoint(-1.2, 7.0));
        System.out.println(func.hashCode());
        System.out.println(func1.hashCode());

    }
}
