package functions;

import java.io.Serializable;
import java.util.Objects;

public class FunctionPoint implements Serializable {
    private double x;
    private double y;

    public FunctionPoint() {
        this.x = 0.0;
        this.y = 0.0;
    }

    public FunctionPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public FunctionPoint(FunctionPoint point) {
        this.x = point.x;
        this.y = point.y;
    }

    public double getX() { return this.x; }
    public double getY() { return this.y; }

    public void setX(double x){ this.x = x; }

    public void setY(double y){
        this.y = y;
    }

    public String toString(){
        return "(" + x + "; " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        FunctionPoint that = (FunctionPoint) o;
        return Double.compare(that.x, this.x) == 0 &&
                Double.compare(that.y, this.y) == 0;
    }

    public int hashCode() {
        long code = Double.doubleToLongBits(this.getX());
        return (int)(code ^ (code >>> 32));
    }

    public Object clone(){
        return new FunctionPoint(this);
    }
}
