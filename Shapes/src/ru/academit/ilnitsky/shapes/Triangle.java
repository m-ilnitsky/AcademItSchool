package ru.academit.ilnitsky.shapes;

/**
 * Created by Mike on 10.10.2016.
 * Класс "Треугольник"
 */
public class Triangle implements Shape {
    private Point pointA;
    private Point pointB;
    private Point pointC;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        set(x1, y1, x2, y2, x3, y3);
    }

    public Triangle(Point pointA, Point pointB, Point pointC) {
        set(pointA, pointB, pointC);
    }

    public Triangle() {
    }

    public double getWidth() {
        return Math.max(Math.max(pointA.getX(), pointB.getX()), pointC.getX()) -
                Math.min(Math.min(pointA.getX(), pointB.getX()), pointC.getX());
    }

    public double getHeight() {
        return Math.max(Math.max(pointA.getY(), pointB.getY()), pointC.getY()) -
                Math.min(Math.min(pointA.getY(), pointB.getY()), pointC.getY());
    }

    public double getArea() {
        double edgeAB = pointA.getDistance(pointB);
        double edgeBC = pointB.getDistance(pointC);
        double edgeCA = pointC.getDistance(pointA);
        double perimeter2 = (edgeAB + edgeBC + edgeCA) / 2;
        return Math.sqrt(perimeter2 * (perimeter2 - edgeAB) * (perimeter2 - edgeBC) * (perimeter2 - edgeCA));
    }

    public double getPerimeter() {
        return pointA.getDistance(pointB) + pointB.getDistance(pointC) + pointC.getDistance(pointA);
    }

    public void set(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.pointA.set(x1, y1);
        this.pointB.set(x2, y2);
        this.pointC.set(x3, y3);
    }

    public void set(Point pointA, Point pointB, Point pointC) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.pointC = pointC;
    }
}
