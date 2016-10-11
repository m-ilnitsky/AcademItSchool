package ru.academit.ilnitsky.shapes;

/**
 * Created by Mike on 10.10.2016.
 * Класс "Прямоугольник"
 */
public class Rectangle implements Shape {
    private double height;
    private double width;

    public Rectangle(double width, double height) {
        this.height = height;
        this.width = width;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getArea() {
        return width * height;
    }

    public double getPerimeter() {
        return (width + height) * 2;
    }

    public void clone(Shape shape) {
        this.height = shape.getHeight();
        this.width = shape.getWidth();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + HashCode.hashCode(width);
        result = prime * result + HashCode.hashCode(height);
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        } else if (this == object) {
            return true;
        } else if (this.getClass() == object.getClass()) {
            Rectangle other = (Rectangle) object;
            return (height == other.height) && (width == other.width);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("[Rectangle: W = %f, H = %f]", width, height);
    }
}
