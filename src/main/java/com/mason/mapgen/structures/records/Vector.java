package com.mason.mapgen.structures.records;

import static com.mason.mapgen.core.Utils.RANDOM;
import static java.lang.Math.PI;

public record Vector(double x, double y){

    public static final Vector ZERO  = new Vector(0,0);
    public static final Vector UNIT_X = new Vector(1,0);
    public static final Vector UNIT_Y = new Vector(0,1);


    //Factories
    public static Vector fromPolar(double r, double theta) {
        return new Vector(r * Math.cos(theta), r * Math.sin(theta));
    }

    public static Vector fromCartesian(double x, double y) {
        return new Vector(x, y);
    }

    public static Vector generateRandomUnitVector(){
        return fromPolar(1, 2 * PI * RANDOM.nextDouble());
    }


    //Operations
    public Vector add(Vector v){
        return new Vector(x + v.x, y + v.y);
    }
    public Vector sub(Vector v){
        return new Vector(x - v.x, y - v.y);
    }
    public Vector neg(){
        return new Vector(-x, -y);
    }
    public Vector mul(double s){
        return new Vector(x * s, y * s);
    }
    public Vector div(double s){
        return new Vector(x / s, y / s);
    }

    public double dot(Vector v){
        return x * v.x + y * v.y;
    }
    public double cross(Vector v){
        return x * v.y - y * v.x;
    }
    public double lengthSquared(){
        return x * x + y * y;
    }
    public double length(){
        return Math.hypot(x, y);
    }
    public double distance(Vector v){
        return this.sub(v).length();
    }
    public double distanceSquared(Vector v){
        return this.sub(v).lengthSquared();
    }


    // --- normalization / projection ---
    public Vector normalized() {
        double len = length();
        return (len == 0.0) ? this : this.div(len);
    }

    public Vector withLength(double newLen) {
        double len = length();
        return (len == 0.0) ? this : this.mul(newLen / len);
    }

    public Vector projectOnto(Vector axis) {
        double d = axis.lengthSquared();
        if (d == 0.0) return ZERO;
        return axis.mul(this.dot(axis) / d);
    }

    public Vector rejectFrom(Vector axis) {
        return this.sub(this.projectOnto(axis));
    }

    // --- geometry helpers ---
    /** Angle of this vector from +X (radians), in [-pi, pi]. */
    public double angle() { return Math.atan2(y, x); }

    /** Signed smallest angle from this to target (radians), between -pi and pi. */
    public double angleTo(Vector target) {
        return Math.atan2(cross(target), dot(target));
    }

    public Vector rotated(double radians) {
        double c = Math.cos(radians), s = Math.sin(radians);
        return new Vector(c * x - s * y, s * x + c * y);
    }

    public Vector interpolate(Vector to, double t) {
        return new Vector(x + (to.x - x) * t, y + (to.y - y) * t);
    }

    public Vector clampLength(double maxLen){
        double l2 = lengthSquared(), m2 = maxLen * maxLen;
        if (l2 <= m2) return this;
        double scale = maxLen / Math.sqrt(l2);
        return this.mul(scale);
    }

    public boolean almostEquals(Vector v, double eps){
        return Math.abs(x - v.x) <= eps && Math.abs(y - v.y) <= eps;
    }

}
