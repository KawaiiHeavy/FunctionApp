package functions;

public class ArrayTabulatedFunction {

    private FunctionPoint[] points;
    private int cnt;

    public ArrayTabulatedFunction(double leftX, double rightX, int pointsCount) {
        points = new FunctionPoint[pointsCount + pointsCount / 2];
        this.points[0] = new FunctionPoint(leftX, 0);
        cnt = pointsCount;

        for (int i = 1; i < pointsCount; i++) {
            this.points[i] = new FunctionPoint(this.points[i - 1].getX() + (rightX - leftX) / (pointsCount - 1), 0);
        }
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double[] values) {

        points = new FunctionPoint[values.length + values.length / 2];
        this.points[0] = new FunctionPoint(leftX, values[0]);
        cnt = values.length;

        for (int i = 1; i < values.length; i++) {
            this.points[i] = new FunctionPoint(this.points[i - 1].getX() + (rightX - leftX) / (values.length - 1), values[i]);
        }
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
            return null;
        }
    }

    public void setPoint(int index, FunctionPoint point) {
        if (index >= 0 && index < cnt || point.getX() >= this.points[index - 1].getX() && point.getX() <= this.points[index + 1].getX()) {
            this.points[index].setX(point.getX());
            this.points[index].setY(point.getY());
        }
    }

    public double getPointX(int index) {
        if (index >= 0 && index < cnt) { return new FunctionPoint(this.points[index]).getX(); }
        else { return Double.NaN; }
    }

    public double getPointY(int index) {
        if (index >= 0 && index < cnt) { return new FunctionPoint(this.points[index]).getY(); }
        else { return Double.NaN; }
    }

    public void setPointX(int index, double x) {
        if (x >= this.points[index - 1].getX() && x <= this.points[index + 1].getX() || index >= 0 && index < cnt) {
            this.points[index].setX(x);
        }
    }

    public void setPointY(int index, double y) {
        if (index >= 0 && index < cnt) {
            this.points[index].setY(y);
        }
    }

    public void deletePoint(int index) {
        if (index >= 0 && index < cnt) {
            System.arraycopy(this.points, index + 1, this.points, index, this.getPointsCount() - index);
            --cnt;
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
}
