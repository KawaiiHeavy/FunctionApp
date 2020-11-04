package functions;

import java.io.Serializable;

public interface TabulatedFunction extends Function, Serializable, Cloneable {

    /**
     *
     * @return
     */
    public int getPointsCount();

    /**
     *
     * @param index
     * @param point
     * @throws InappropriateFunctionPointException
     */
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException;

    /**
     *
     * @param index
     * @return
     */
    public double getPointX(int index);

    /**
     *
     * @param index
     * @param x
     * @throws InappropriateFunctionPointException
     */
    public void setPointX(int index, double x) throws InappropriateFunctionPointException;

    /**
     *
     * @param index
     * @return
     */
    public double getPointY(int index);

    /**
     *
     * @param index
     * @param y
     */
    public void setPointY(int index, double y);

    /**
     *
     * @param index
     */

    public FunctionPoint getPoint(int index);

    public void deletePoint(int index);

    /**
     *
     * @param point
     * @throws InappropriateFunctionPointException
     */
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;

    public void printFunction();

    public Object clone() throws CloneNotSupportedException;
}
