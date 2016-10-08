package ru.academit.ilnitsky.range;

/**
 * Created by Mike on 09.10.2016.
 * Числовой диапазон
 */
public class Range {
    private double from;
    private double to;

    public Range(Range range0) {
        set(range0.from, range0.to);
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

    public void setEquals(Range range0) {
        this.from = range0.from;
        this.to = range0.to;
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
        if (FD.isEqual(from, to)) {
            return 0;
        }

        return to - from;
    }

    public boolean isPoint() {
        return FD.isEqual(from, to);
    }

    public boolean isFrom(double point) {
        return FD.isEqual(from, point);
    }

    public boolean isTo(double point) {
        return FD.isEqual(to, point);
    }

    public boolean isEqual(double point) {
        return isPoint() && FD.isEqual(from, point) && FD.isEqual(to, point);
    }

    public boolean isEqual(Range range2) {
        return FD.isEqual(from, range2.from) && FD.isEqual(to, range2.to);
    }

    public boolean isInside(double point) {
        return FD.isBiggerOrEqual(point, from) && FD.isBiggerOrEqual(to, point);
    }

    public boolean isInside(Range range2) {
        return FD.isBiggerOrEqual(range2.from, from) && FD.isBiggerOrEqual(to, range2.to);
    }

    public boolean isIntersection(Range range2) {
        return FD.isBiggerOrEqual(range2.to, from) && FD.isBiggerOrEqual(to, range2.from);
    }

    public Range calcIntersection(Range range2){
        if(isIntersection(range2)){
            Range intersection = new Range();

            if (range2.isInside(this)) {
                intersection.set(from, to);
            } else if (this.isInside(range2)) {
                intersection.set(range2.from, range2.to);
            } else if (FD.isBigger(to, range2.to) && FD.isBiggerOrEqual(range2.to, from)) {
                intersection.set(from, range2.to);
            } else if (FD.isBigger(range2.to, to) && FD.isBiggerOrEqual(to, range2.from)) {
                intersection.set(range2.from, to);
            } else {
                return null;
            }

            return intersection;
        }

        return null;
    }

    public Range[] calcUnion(Range range2){
        if(isInside(range2)){
            Range[] union = new Range[1];
            union[0].setEquals(this);
            return union;
        }else if(range2.isInside(this)){
            Range[] union = new Range[1];
            union[0].setEquals(range2);
            return union;
        }else if(isIntersection(range2)){
            Range[] union = new Range[1];
            union[0].set(Math.min(range2.getFrom(),from),Math.max(range2.getTo(),to));
            return union;
        }else{
            Range[] union = new Range[2];
            if(FD.isBigger(range2.from,to)){
                union[0].setEquals(this);
                union[1].setEquals(range2);
            }else{
                union[0].setEquals(range2);
                union[1].setEquals(this);
            }
            return union;
        }
    }

    public Range[] calcDifference(Range range2){

    }
}
