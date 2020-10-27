package functions;

import java.io.Serializable;

public class LinkedListTabulatedFunction implements TabulatedFunction, Serializable {

    private FunctionNode head, tail;
    private FunctionNode mainHead = new FunctionNode();
    private FunctionNode currentElement;
    private int pointsCount;
    private int currentIndex;


    /*
    Класс находится внутри класса для того, чтобы лишь класс списка имел доступ к нему,
    т.к. имеет смысл создавать целую функцию, а не точки по отдельности
    */
    private static class FunctionNode {
        private FunctionPoint point;
        private FunctionNode prev;
        private FunctionNode next;
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount){
        if (leftX >= rightX || pointsCount < 2){
            throw new IllegalArgumentException();
        }
        head = new FunctionNode();
        FunctionNode current = new FunctionNode();

        head.prev = mainHead;
        head.point = new FunctionPoint(leftX, 0.0);
        head.next = current;

        current.prev = head;
        currentIndex++;

        for (int i = 1; i < pointsCount; ++i){
            current.next = new FunctionNode();
            current.next.prev = current;
            current.point = new FunctionPoint(current.prev.point.getX() + (rightX - leftX) / (pointsCount - 1), 0);

            current = current.next;
            currentIndex++;
        }

        tail = current.prev;
        tail.next = mainHead;

        mainHead.prev = tail;
        mainHead.next = head;
        this.pointsCount = pointsCount;
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values){
        this(leftX, rightX, values.length);
        int i = 0;
        for (FunctionNode current = head; current != tail; current = current.next, ++i){
            current.point.setY(values[i]);
        }
        tail.point.setY(values[i]);
    }

    public LinkedListTabulatedFunction(FunctionPoint[] points){
        if (points[0].getX() >= points[points.length - 1].getX() || points.length < 2){
            throw new IllegalArgumentException();
        }
        head = new FunctionNode();
        FunctionNode current = new FunctionNode();

        head.prev = mainHead;
        head.point = points[0];
        head.next = current;

        current.prev = head;
        currentIndex++;

        for (int i = 1; i < points.length; i++, currentIndex++, current = current.next){
            current.next = new FunctionNode();
            current.next.prev = current;
            current.point = points[i];
            current.prev = head;
        }

        tail = current.prev;
        tail.next = mainHead;

        mainHead.prev = tail;
        mainHead.next = head;
        pointsCount = points.length;
    }

    public FunctionNode getNodeByIndex(int index) {
        //Индексы точки отправления
        int fromTail = pointsCount - index - 1;
        int fromHead = index;
        int fromCurrent = Math.abs(currentIndex - index);

        if (fromTail < fromHead) { //ищем оптимальный индекс начала движения
            if (fromTail < fromCurrent) {
                currentElement = tail;
                currentIndex = pointsCount - 1;
            }
        } else {
            if (fromHead < fromCurrent) {
                currentElement = head;
                currentIndex = 0;
            }
        }

        if (index < currentIndex) { //выбираем направление движения
            while (currentIndex != index) {
                currentElement = currentElement.prev;
                currentIndex--;
            }
        } else {
            while (currentIndex != index) {
                currentElement = currentElement.next;
                currentIndex++;
            }
        }
        return currentElement;
    }

    public FunctionNode addNodeToTail() {
        tail.next = new FunctionNode();
        // |1| <-> |2| <-> |3| <-> |..| <-> |9| <-> |(tail)10|
        tail.next.prev = tail;
        // |1| <-> |2| <-> |3| <-> |..| <-> |9| <-> |(tail)10| -> |(new) 11|
        tail.next.next = mainHead;
        // |1| <-> |2| <-> |3| <-> |..| <-> |9| <-> |(tail)10| <-> |(new) 11| -> mainHead
        tail = tail.next;
        // |1| <-> |2| <-> |3| <-> |..| <-> |9| <-> |10| <-> |(tail) 11| -> mainHead
        pointsCount++;
        mainHead.prev = tail;
        // |1| <-> |2| <-> |3| <-> |..| <-> |9| <-> |10| <-> |(tail) 11| <-> mainHead
        return tail;
    }

    public FunctionNode addNodeByIndex(int index){
        getNodeByIndex(index);
        FunctionNode node = new FunctionNode();
        // |1| <-> |2| <-> |3| <-> |..| <-> |9| <-> |10|
        node.next = currentElement;
        //        |1.5|
        //          |
        //          v
        // |1| <-> |(current) 2| <-> |3| <-> |..| <-> |9| <-> |10|
        node.prev = currentElement.prev;
        //        |1.5|
        //          ^
        //          |
        //          v
        // |1| <-> |(current) 2| <-> |3| <-> |..| <-> |9| <-> |10|
        currentElement.prev.next = node;
        //        |1.5|
        //      ^   ^
        //     /    |
        //    /     v
        // |1| <- |(current) 2| <-> |3| <-> |..| <-> |9| <-> |10|
        currentElement.prev = node;
        // |1| <-> |1.5| <-> |(current) 2| <-> |3| <-> |..| <-> |9| <-> |10|
        currentElement = node;
        // |1| <-> |(current) 1.5| <-> |(2| <-> |3| <-> |..| <-> |9| <-> |10|
        pointsCount++;
        return currentElement;
    }

    public FunctionNode deleteNodeByIndex(int index){
        getNodeByIndex(index);
        FunctionNode node = currentElement;
        // |1| <-> |(current) 2| <-> |3| <-> |..| <-> |9| <-> |10|
        currentElement.prev.next = currentElement.next;
        // |1| <- |(current) 2|  |..| <-> |9| <-> |10|
        //    \               ^   ^
        //     ------------\  |  /
        //                  v v v
        //                   |3|
        currentElement.next.prev = currentElement.prev;
        // |1| <- |(current) 2|  |..| <-> |9| <-> |10|
        //  ^                 |   ^
        //   \-------------\  |  /
        //                  v v v
        //                   |3|
        currentElement = currentElement.prev;
        // |(current) 1| <- |2|  |..| <-> |9| <-> |10|
        //  ^                 |   ^
        //   \-------------\  |  /
        //                  v v v
        //                   |3|
        --currentIndex;

        --pointsCount;
        head = mainHead.next;
        tail = mainHead.prev;
        return node;
    }

    @Override
    public double getLeftDomainBorder() {
        if (pointsCount == 0) {
            throw new IllegalStateException();
        }
        return head.point.getX();
    }

    @Override
    public double getRightDomainBorder() {
        if (pointsCount == 0) {
            throw new IllegalStateException();
        }
        return tail.point.getX();
    }

    @Override
    public int getPointsCount() {
        return pointsCount;
    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {

        if (pointsCount == 0) {
            throw new IllegalStateException();
        }
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Index not in range");
        }

        double left = Double.MIN_VALUE;
        double right = Double.MAX_VALUE;

        FunctionNode node = getNodeByIndex(index);

        if (node.prev != null) {
            left = node.prev.point.getX();
        }
        if (node.next != null) {
            right = node.next.point.getX()
            ;
        }

        if (!(point.getX() > left || point.getX() < right)) {
            throw new InappropriateFunctionPointException("Inappropriate function value");
        }

        node.point = new FunctionPoint(point);

    }

    @Override
    public double getPointX(int index) {
        if (pointsCount == 0) {
            throw new IllegalStateException();
        }
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Index out of range");
        }
        return getNodeByIndex(index).point.getX();
    }

    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if (pointsCount == 0) {
            throw new IllegalStateException();
        }
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Index out of range");
        }

        double left = Double.MIN_VALUE, right = Double.MAX_VALUE;

        FunctionNode node = getNodeByIndex(index);

        if (node.prev != null) {
            left = node.prev.point.getX();
        }
        if (node.next != null) {
            right = node.next.point.getX();
        }

        if (left > x || right < x) {
            throw new InappropriateFunctionPointException("Inappropriate function value");
        }

        node.point.setX(x);
    }

    @Override
    public double getPointY(int index) {
        if (pointsCount == 0) {
            throw new IllegalStateException();
        }
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
        }
        return getNodeByIndex(index).point.getY();
    }

    @Override
    public void setPointY(int index, double y) {
        if (pointsCount == 0) {
            throw new IllegalStateException();
        }
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Index out of bounds");
        }
        getNodeByIndex(index).point.setY(y);
    }

    @Override
    public double getFunctionValue(double x) {
        if (pointsCount == 0) {
            throw new IllegalStateException();
        }
        if (x < head.point.getX() || x > tail.point.getX()) {
            throw new FunctionPointIndexOutOfBoundsException("Inappropriate x");
        }

        currentElement = head;
        currentIndex = 0;
        while (currentElement.point.getX() < x) {
            currentElement = currentElement.next;
            currentIndex++;
        }

        if (currentElement.point.getX() == x) {
            return currentElement.point.getY();
        }

        double k = (currentElement.point.getY() - currentElement.prev.point.getY()) / (currentElement.point.getX() - currentElement.prev.point.getX());
        double b = currentElement.point.getY() - k * currentElement.point.getX();
        return k * x + b;
    }

    @Override
    public void deletePoint(int index) {
        if (pointsCount < 3) {
            throw new IllegalStateException();
        }
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Index out of bound");
        }

        deleteNodeByIndex(index);
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if (pointsCount != 0 && (point.getX() < head.point.getX() || point.getX() > tail.point.getX())) {
            throw new InappropriateFunctionPointException("Inappropriate x");
        }

        if (pointsCount == 0) {
            head = new FunctionNode();
            head.point = new FunctionPoint(point);
            pointsCount++;
            tail = head;
            currentElement = head;
            currentIndex = 0;
            return;
        }

        currentElement = head;
        currentIndex = 0;
        while (currentElement.point.getX() < point.getX()) {
            currentElement = currentElement.next;
            currentIndex++;
        }

        if (currentElement.point.getX() == point.getX()) {
            throw new InappropriateFunctionPointException("Inappropriate x");
        }

        addNodeByIndex(currentIndex).point = point;
    }

    public void printFunction(){
        int i = 0;
        for (FunctionNode current = head; current != tail; current = current.next, ++i){
            System.out.println("Point " + (i + 1));
            System.out.println("x = " + current.point.getX());
            System.out.println("y = " + current.point.getY());
        }
        System.out.println("Point " + (i + 1));
        System.out.println("x = " + tail.point.getX());
        System.out.println("y = " + tail.point.getY());
    }
}
