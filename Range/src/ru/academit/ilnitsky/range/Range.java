package ru.academit.ilnitsky.range;

import ru.academit.ilnitsky.functions.Compare;

/**
 * Created by Mike on 09.10.2016.
 * Класс "Числовой диапазон"
 */
public class Range {
    private double from;
    private double to;

    public Range(Range range) {
        set(range.from, range.to);
    }

    public Range(double from, double to) {
        set(from, to);
    }

    public Range(double point) {
        setPoint(point);
    }

    public Range() {
        set(0, 0);
    }

    public void setPoint(double point) {
        this.from = point;
        this.to = point;
    }

    public void set(double from, double to) {
        if (to - from >= 0) {
            this.from = from;
            this.to = to;
        } else {
            this.to = from;
            this.from = to;
        }
    }

    public void setEquals(Range range) {
        this.from = range.from;
        this.to = range.to;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public double getTo() {
        return to;
    }

    public double calcLength() {
        if (Compare.isEqual(from, to)) {
            return 0;
        }

        return to - from;
    }

    public boolean isPoint() {
        return Compare.isEqual(from, to);
    }

    public boolean isFrom(double point) {
        return Compare.isEqual(from, point);
    }

    public boolean isTo(double point) {
        return Compare.isEqual(to, point);
    }

    public boolean isEqual(double point) {
        return isPoint() && Compare.isEqual(from, point) && Compare.isEqual(to, point);
    }

    public boolean isEqual(Range range) {
        return Compare.isEqual(from, range.from) && Compare.isEqual(to, range.to);
    }

    public boolean isInside(double point) {
        return Compare.isBiggerOrEqual(point, from) && Compare.isBiggerOrEqual(to, point);
    }

    public boolean isInside(Range range) {
        return Compare.isBiggerOrEqual(range.from, from) && Compare.isBiggerOrEqual(to, range.to);
    }

    public boolean isIntersection(Range range) {
        return Compare.isBiggerOrEqual(range.to, from) && Compare.isBiggerOrEqual(to, range.from);
    }

    public Range calcIntersection(Range range) {
        if (isIntersection(range)) {
            Range intersection = new Range(Math.max(range.from, from), Math.min(range.to, to));
            return intersection;
        }

        return null;
    }

    public Range[] calcUnion(Range range) {
        if (isIntersection(range)) {
            Range[] union = {new Range(Math.min(range.from, from), Math.max(range.to, to))};
            return union;
        } else {
            Range[] union = new Range[2];
            if (Compare.isBigger(range.from, to)) {
                union[0] = new Range(this);
                union[1] = new Range(range);
            } else {
                union[0] = new Range(range);
                union[1] = new Range(this);
            }
            return union;
        }
    }

    public Range[] calcDifference(Range range) {
        if (range.isInside(this) || isEqual(range)) {
            return new Range[0];
        } else if (isInside(range)) {
            if (Compare.isEqual(to, range.to)) {
                Range[] difference = {new Range(from, range.from)};
                return difference;
            } else if (Compare.isEqual(from, range.from)) {
                Range[] difference = {new Range(range.to, to)};
                return difference;
            } else {
                Range[] difference = {new Range(from, range.from), new Range(range.to, to)};
                return difference;
            }
        } else if (isIntersection(range)) {
            Range[] difference = new Range[1];
            if (Compare.isBigger(range.to, to)) {
                difference[0] = new Range(from, range.from);
            } else {
                difference[0] = new Range(range.to, to);
            }
            return difference;
        } else {
            Range[] difference = {new Range(this)};
            return difference;
        }
    }

    public String toString() {
        return String.format("[%f ; %f]", from, to);
    }
}
