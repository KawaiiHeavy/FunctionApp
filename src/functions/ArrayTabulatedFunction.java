package functions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class ArrayTabulatedFunction implements TabulatedFunction, Serializable, Cloneable {

    private FunctionPoint[] points;
    private int cnt;

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) {
        if (leftX >= rightX || pointsCount < 2){
            throw new IllegalArgumentException();
        }
        points = new FunctionPoint[pointsCount + pointsCount / 2];
        this.points[0] = new FunctionPoint(leftX, 0);
        cnt = pointsCount;

        for (int i = 1; i < pointsCount; i++) {
            this.points[i] = new FunctionPoint(this.points[i - 1].getX() + (rightX - leftX) / (pointsCount - 1), 0);
        }
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) {
        this(leftX, rightX, values.length);
        for (int i = 0; i < values.length; i++){
            this.points[i].setY(values[i]);
        }
    }

    public ArrayTabulatedFunction(FunctionPoint[] points){
        if (points[0].getX() >= points[points.length - 1].getX() || points.length < 2){
            throw new IllegalArgumentException();
        }
        this.points = new FunctionPoint[points.length + points.length / 2];
        for (int i = 0; i < points.length; i++){
            this.points[i] = points[i];
        }
        cnt = points.length;
    }

    public int getPointsCount() {
        return this.cnt;
    }

    public double getLeftDomainBorder() {
        return this.points[0].getX();
    }

    public double getRightDomainBorder() {
        return this.points[this.getPointsCount() - 1].getX();
    }

    public double getFunctionValue(double x) {
        //Если не найдется искомая точка - вычисляем через линейную интерполяцию

        if (x >= this.getLeftDomainBorder() && x <= this.getRightDomainBorder()) {
            int endIndex = this.getPointsCount();
            int startIndex = 0;
            int midIndex = startIndex + (endIndex - startIndex) / 2;
            if (points.length == 0) {
                return Double.NaN;
            }
            if (points[0].getX() > x) {
                return Double.NaN;
            }
            if (points[this.getPointsCount() - 1].getX() < x) {
                return Double.NaN;
            }

            while (startIndex < endIndex) {
                if (x <= points[midIndex].getX()) {
                    endIndex = midIndex;
                } else {
                    startIndex = midIndex + 1;
                }
                midIndex = startIndex + (endIndex - startIndex) / 2;
            }

            if (points[endIndex].getX() == x) {
                return points[endIndex].getY();
            }

            else {
                double k = (points[endIndex].getY() - points[endIndex - 1].getY()) / (points[endIndex].getX() - points[endIndex - 1].getX());
                double b = points[endIndex].getY() - k * points[endIndex].getX();
                return k * x + b;
            }
        }

        else {
            return Double.NaN;
        }
    }

    public FunctionPoint getPoint(int index) {
        if (index >= 0 && index < cnt) {
            return new FunctionPoint(this.points[index]);
        }
        else {
            throw new FunctionPointIndexOutOfBoundsException("Out of bound");
        }
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if (point.getX() >= this.points[index - 1].getX() && point.getX() <= this.points[index + 1].getX()){
            throw new InappropriateFunctionPointException("Inappropriate x value");
        }
        if (index >= 0 && index < cnt) {
            this.points[index].setX(point.getX());
            this.points[index].setY(point.getY());
        }
        else {
            throw new FunctionPointIndexOutOfBoundsException("Out of bound");
        }
    }

    public double getPointX(int index) {
        if (index >= 0 && index < cnt) { return new FunctionPoint(this.points[index]).getX(); }
        else {
            throw new FunctionPointIndexOutOfBoundsException("Out of bound");
        }
    }

    public double getPointY(int index) {
        if (index >= 0 && index < cnt) { return new FunctionPoint(this.points[index]).getY(); }
        else {
            throw new FunctionPointIndexOutOfBoundsException("Out of bound");
        }
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if (!(x >= this.points[index - 1].getX() && x <= this.points[index + 1].getX())){
            throw new InappropriateFunctionPointException("Inappropriate x value");
        }
        if (index >= 0 && index < cnt) {
            this.points[index].setX(x);
        }
        else {
            throw new FunctionPointIndexOutOfBoundsException("Out of bound");
        }
    }

    public void setPointY(int index, double y) {
        if (index >= 0 && index < cnt) {
            this.points[index].setY(y);
        }
        else {
            throw new FunctionPointIndexOutOfBoundsException("Out of bound");
        }
    }

    public void deletePoint(int index) {
        if (index >= 0 && index < cnt) {
            System.arraycopy(this.points, 0, this.points, 0, index);
            System.arraycopy(this.points, index + 1, this.points, index, this.getPointsCount() - index);
            --cnt;
        }
        else {
            throw new FunctionPointIndexOutOfBoundsException("Out of bound");
        }
    }

    public void addPoint(FunctionPoint point) {
        int endIndex = this.getPointsCount();
        int startIndex = 0;
        int midIndex = startIndex + (endIndex - startIndex) / 2;

        while (startIndex < endIndex) {
            if (point.getX() <= points[midIndex].getX()) {
                endIndex = midIndex;
            }
            else {
                startIndex = midIndex + 1;
            }
            midIndex = startIndex + (endIndex - startIndex) / 2;
        }

        if (cnt < points.length) {
            /*
            Копирование массива самого в себя с расширением
            params: текущий массив, индекс конца массива, массив для копирования, индекс конца второго массива, длина второго массива
            */
            System.arraycopy(this.points, endIndex, this.points, endIndex + 1, this.getPointsCount() - endIndex);
            this.points[endIndex] = point;
            ++cnt;
        }

        else {
            FunctionPoint[] pointsNew = new FunctionPoint[cnt + cnt / 2];
            /*
            Создание нового массива из-за нехватки памяти в исходном - копируем массив объекта в более вместительный
            params: 1) копируемый массив, 2) начальный индекс первого массива, 3) массив назначения,
            4) начальный индекс массива назначения, 5) конечный индекс массива назначения
             */
            System.arraycopy(this.points, 0, pointsNew, 0, cnt);
            this.points = pointsNew;

            /*
            Копирование массива самого в себя с расширением
            params: 1) текущий массив, 2) индекс конца массива, 3) массив для копирования,
            4) индекс конца второго массива, 5) длина второго массива
            */
            System.arraycopy(this.points, endIndex, this.points, endIndex + 1, this.getPointsCount() - endIndex);
            this.points[endIndex] = point;
            ++cnt;
        }
    }

    public void printFunction(){
        for (int i = 0; i < this.getPointsCount(); i++) {
            System.out.println("Point " + (i + 1));
            System.out.println("x = " + this.getPointX(i));
            System.out.println("y = " + this.getPointY(i));
        }
    }

    public String toString(){
        StringBuilder result = new StringBuilder("{");
        for (int i = 0; i < this.getPointsCount(); i++){
            result.append(this.getPoint(i).toString());
            result.append(", ");
        }
        result.deleteCharAt(result.lastIndexOf(","));
        result.append("}");
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null){
            return false;
        }
        if (o instanceof TabulatedFunction){
            if (o instanceof ArrayTabulatedFunction) {
                ArrayTabulatedFunction func = (ArrayTabulatedFunction) o;
                if (func.getPointsCount() != this.getPointsCount()){
                    return false;
                }
                for (int i = 0; i < getPointsCount(); i++){
                    if (func.points[i].getX() != this.points[i].getX() || func.points[i].getY() != this.points[i].getY()){
                        return false;
                    }
                }
                return true;
            }
            else {
                TabulatedFunction func = (TabulatedFunction) o;
                if (func.getPointsCount() != this.getPointsCount()){
                    return false;
                }
                for (int i = 0; i < this.getPointsCount(); i++){
                    if (!(this.getPoint(i).equals(func.getPoint(i)))){
                        return false;
                    }
                }
                return true;
            }
        }
        else {
            return false;
        }
    }

    public int hashCode() {
        if (this.getPointsCount() != 0){
            int hash = this.getPoint(0).hashCode();
            for (int i = 0; i < this.getPointsCount(); i++){
                hash ^= this.getPoint(i).hashCode();
            }
            return hash ^ this.getPointsCount();
        }
        return 0;
    }

    public Object clone(){
        FunctionPoint[] functionPoints = new FunctionPoint[this.getPointsCount()];
        for (int i = 0; i < this.getPointsCount(); i++){
            functionPoints[i] = (FunctionPoint) getPoint(i).clone();
        }
        return new ArrayTabulatedFunction(functionPoints);
    }
}
