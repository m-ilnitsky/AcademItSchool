package ru.academit.ilnitsky.shapes;

import org.omg.CORBA.Object;

/**
 * Created by Mike on 10.10.2016.
 * Класс "Точка"
 */
public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public Point() {
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void clone(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public void shift(Point vector) {
        this.x += vector.x;
        this.y += vector.y;
    }

    public double getModule() {
        return Math.sqrt(x * x + y * y);
    }

    public double getDistance(Point point) {
        double dx = x - point.x;
        double dy = y - point.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + HashCode.hashCode(x);
        result = prime * result + HashCode.hashCode(y);
        return result;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        } else if (this == object) {
            return true;
        } else if (this.getClass() == object.getClass()) {
            Point other = (Point) object;
            return (x == other.x) && (y == other.y);
        } else {
            return false;
        }
    }

    public String toString() {
        return "( " + x + " ; " + y + " )";
        //return String.format("(%f ; %f)", x, y);
    }
}
