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
        this.pointA = new Point(x1, y1);
        this.pointB = new Point(x2, y2);
        this.pointC = new Point(x3, y3);
    }

    public Triangle(Point pointA, Point pointB, Point pointC) {
        this.pointA = new Point(pointA);
        this.pointB = new Point(pointB);
        this.pointC = new Point(pointC);
    }

    public Point getPointA() {
        return pointA;
    }

    public Point getPointB() {
        return pointB;
    }

    public Point getPointC() {
        return pointC;
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

    public void setPointA(Point pointA) {
        this.pointA = pointA;
    }

    public void setPointB(Point pointB) {
        this.pointB = pointB;
    }

    public void setPointC(Point pointC) {
        this.pointC = pointC;
    }

    public void clone(Triangle triangle) {
        this.pointA = triangle.pointA;
        this.pointB = triangle.pointB;
        this.pointC = triangle.pointC;
    }

    public int hashCode() {
        final int prime = 7;
        int result = 1;
        result = prime * result + pointA.hashCode();
        result = prime * result + pointB.hashCode();
        result = prime * result + pointC.hashCode();
        return result;
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        } else if (this == object) {
            return true;
        } else if (this.getClass() == object.getClass()) {
            Triangle other = (Triangle) object;
            return (pointA.equals(other.pointA) && pointB.equals(other.pointB) && pointC.equals(other.pointC));
        } else {
            return false;
        }
    }

    public String toString() {
        return String.format("[Triangle: A%s B%s C%s]", pointA, pointB, pointC);
    }
}
