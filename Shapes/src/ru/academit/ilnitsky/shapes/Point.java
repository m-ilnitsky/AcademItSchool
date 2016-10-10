package ru.academit.ilnitsky.shapes;

/**
 * Created by Mike on 10.10.2016.
 * Класс "Точка"
 */
public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        set(x, y);
    }

    public Point(Point point) {
        set(point.x, point.y);
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

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void shift(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void shift(Point vector) {
        shift(vector.x, vector.y);
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

    public boolean equals(Point point) {
        return (x == point.x) && (y == point.y);
    }

    public String toString() {
        return "( " + x + " ; " + y + " )";
        //return String.format("(%f ; %f)", x, y);
    }
}
