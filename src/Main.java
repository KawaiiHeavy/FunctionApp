import functions.*;
import functions.basic.*;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        TabulatedFunction tabSin, tabCos;
        Sin sin = new Sin();
        Cos cos = new Cos();
        System.out.println("Sin");
        for (double i = 0; i < 2 * Math.PI; i += 0.1){
            System.out.println("At x = " + i + ": " + sin.getFunctionValue(i));
        }

        System.out.println("Cos");
        for (double i = 0; i < 2 * Math.PI; i += 0.1){
            System.out.println("At x = " + i + ": " + cos.getFunctionValue(i));
        }

        System.out.println("Cos");
        for (double i = 0; i < 2 * Math.PI; i += 0.1){
            System.out.println("At x = " + i + ": " + cos.getFunctionValue(i));
        }

        System.out.println("tabSin");
        tabSin = TabulatedFunctions.tabulate(sin, 0, 2 * Math.PI, 10);
        for (double i = 0; i < 2 * Math.PI; i += 0.1){
            System.out.println("At x = " + i + ": " + tabSin.getFunctionValue(i));
        }

        System.out.println("tabCos");
        tabCos = TabulatedFunctions.tabulate(cos, 0, 2 * Math.PI, 10);
        for (double i = 0; i < 2 * Math.PI; i += 0.1){
            System.out.println("At x = " + i + ": " + tabCos.getFunctionValue(i));
        }

        Function squareSin = Functions.power(tabSin, 2);
        Function squareCos = Functions.power(tabCos, 2);
        Function sum = Functions.sum(squareSin, squareCos);

        System.out.println("Tabulated sum");
        for (double i = 0; i < 2 * Math.PI; i += 0.1){
            System.out.println("At x = " + i + ": " + sum.getFunctionValue(i));
        }

        System.out.println("tabExp");
        Exp e = new Exp();
        TabulatedFunction exp  = TabulatedFunctions.tabulate(e, 0, 10, 11);
        FileWriter writer = new FileWriter("exp.txt");
        TabulatedFunctions.writeTabulatedFunction(exp, writer);
        writer.flush();
        writer.close();
        FileReader reader = new FileReader("exp.txt");
        TabulatedFunction readedExp = TabulatedFunctions.readTabulatedFunction(reader);
        reader.close();

        System.out.println("tabLog");
        Log l = new Log(Math.E);
        TabulatedFunction tabLog = TabulatedFunctions.tabulate(l, 0, 10, 11);
        OutputStream output = new FileOutputStream("log.txt");
        TabulatedFunctions.outputTabulatedFunction(tabLog, output);
        output.flush();
        output.close();
        InputStream input = new FileInputStream("log.txt");
        TabulatedFunction readedLog = TabulatedFunctions.inputTabulatedFunction(input);
        input.close();

        System.out.println("Log from exp");
        Log ln = new Log(Math.E);
        Exp exp2 = new Exp();
        Function comp = Functions.composition(ln, exp2);
        TabulatedFunction tabLn = TabulatedFunctions.tabulate(comp, 1, 10, 11);
        for (double i = 0; i < 11; i++){
            System.out.println("At x = " + i + ": " + tabLn.getFunctionValue(i));
        }
        FileOutputStream fos = new FileOutputStream("temp.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(tabLn);
        oos.close();

        FileInputStream fis = new FileInputStream("temp.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        TabulatedFunction comp1 = (TabulatedFunction) ois.readObject();
        ois.close();
        for (double i = 0; i < 11; i++){
            System.out.println("At x = " + i + ": " + comp1.getFunctionValue(i));
        }
    }
}
