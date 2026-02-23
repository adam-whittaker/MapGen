package com.mason.mapgen.structures.records;

import org.junit.jupiter.api.Test;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.*;

class VectorTest{

    private static final double EPS = 1e-9;

    @Test
    void constantsAreCorrect() {
        assertEquals(0.0, Vector.ZERO.x(), EPS);
        assertEquals(0.0, Vector.ZERO.y(), EPS);

        assertEquals(1.0, Vector.UNIT_X.x(), EPS);
        assertEquals(0.0, Vector.UNIT_X.y(), EPS);

        assertEquals(0.0, Vector.UNIT_Y.x(), EPS);
        assertEquals(1.0, Vector.UNIT_Y.y(), EPS);
    }

    @Test
    void fromCartesianIsIdentity() {
        Vector v = Vector.fromCartesian(3.5, -2.0);
        assertEquals(3.5, v.x(), EPS);
        assertEquals(-2.0, v.y(), EPS);
    }

    @Test
    void fromPolarBuildsCorrectVector() {
        Vector v = Vector.fromPolar(2.0, PI / 2); // r=2, theta=90°
        assertEquals(0.0, v.x(), 1e-8);
        assertEquals(2.0, v.y(), 1e-8);

        Vector v2 = Vector.fromPolar(3.0, 0.0);
        assertEquals(3.0, v2.x(), 1e-8);
        assertEquals(0.0, v2.y(), 1e-8);
    }

    @Test
    void generateRandomUnitVectorHasUnitLength() {
        // Not testing randomness, just that length ≈ 1 and finite
        for (int i = 0; i < 100; i++) {
            Vector v = Vector.generateRandomUnitVector();
            double len = v.length();
            assertTrue(Double.isFinite(len));
            assertEquals(1.0, len, 1e-9);
        }
    }

    @Test
    void basicArithmeticOperationsWork() {
        Vector a = new Vector(1.0, 2.0);
        Vector b = new Vector(-3.0, 4.0);

        Vector sum = a.add(b);
        assertEquals(-2.0, sum.x(), EPS);
        assertEquals(6.0, sum.y(), EPS);

        Vector diff = a.sub(b);
        assertEquals(4.0, diff.x(), EPS);
        assertEquals(-2.0, diff.y(), EPS);

        Vector neg = a.neg();
        assertEquals(-1.0, neg.x(), EPS);
        assertEquals(-2.0, neg.y(), EPS);

        Vector scaled = a.mul(3.0);
        assertEquals(3.0, scaled.x(), EPS);
        assertEquals(6.0, scaled.y(), EPS);

        Vector divided = a.div(2.0);
        assertEquals(0.5, divided.x(), EPS);
        assertEquals(1.0, divided.y(), EPS);
    }

    @Test
    void dotAndCrossProducts() {
        Vector a = new Vector(1.0, 2.0);
        Vector b = new Vector(3.0, 4.0);

        assertEquals(1 * 3 + 2 * 4, a.dot(b), EPS);        // 11
        assertEquals(1 * 4 - 2 * 3, a.cross(b), EPS);      // -2
    }

    @Test
    void lengthAndDistance() {
        Vector v = new Vector(3.0, 4.0);
        assertEquals(25.0, v.lengthSquared(), EPS);
        assertEquals(5.0, v.length(), EPS);

        Vector a = new Vector(1.0, 1.0);
        Vector b = new Vector(4.0, 5.0);
        assertEquals(5.0, a.distance(b), 1e-9);
        assertEquals(25.0, a.distanceSquared(b), 1e-9);
    }

    @Test
    void normalizedProducesUnitVectorExceptZero() {
        Vector v = new Vector(3.0, 4.0);
        Vector n = v.normalized();
        assertEquals(1.0, n.length(), 1e-9);
        // direction preserved
        assertEquals(3.0 / 5.0, n.x(), 1e-9);
        assertEquals(4.0 / 5.0, n.y(), 1e-9);

        Vector zero = Vector.ZERO;
        Vector nZero = zero.normalized();
        // zero stays zero
        assertSame(zero, nZero);
    }

    @Test
    void withLengthScalesToGivenMagnitudeExceptZero() {
        Vector v = new Vector(3.0, 4.0); // length 5
        Vector w = v.withLength(10.0);

        assertEquals(10.0, w.length(), 1e-9);
        assertEquals(3.0 / 5.0 * 10.0, w.x(), 1e-9);
        assertEquals(4.0 / 5.0 * 10.0, w.y(), 1e-9);

        Vector zero = Vector.ZERO;
        Vector wZero = zero.withLength(10.0);
        assertSame(zero, wZero);
    }

    @Test
    void projectOntoAxisAndRejectFromAxis() {
        Vector v = new Vector(3.0, 4.0);
        Vector axis = new Vector(1.0, 0.0); // X-axis

        Vector proj = v.projectOnto(axis);
        // projection onto x-axis is (3,0)
        assertEquals(3.0, proj.x(), 1e-9);
        assertEquals(0.0, proj.y(), 1e-9);

        Vector rej = v.rejectFrom(axis);
        // v - proj = (0,4)
        assertEquals(0.0, rej.x(), 1e-9);
        assertEquals(4.0, rej.y(), 1e-9);

        // v = proj + rej
        Vector sum = proj.add(rej);
        assertEquals(v.x(), sum.x(), 1e-9);
        assertEquals(v.y(), sum.y(), 1e-9);

        // rejection is orthogonal to axis
        assertEquals(0.0, rej.dot(axis), 1e-9);
    }

    @Test
    void projectOntoZeroAxisReturnsZeroVector() {
        Vector v = new Vector(1.0, 2.0);
        Vector zeroAxis = Vector.ZERO;
        Vector proj = v.projectOnto(zeroAxis);

        assertEquals(Vector.ZERO.x(), proj.x(), EPS);
        assertEquals(Vector.ZERO.y(), proj.y(), EPS);
    }

    @Test
    void angleOfVectorIsCorrect() {
        assertEquals(0.0, Vector.UNIT_X.angle(), 1e-9);
        assertEquals(PI / 2, Vector.UNIT_Y.angle(), 1e-9);
        assertEquals(-PI / 2, new Vector(0.0, -1.0).angle(), 1e-9);

        // 135° = 3π/4
        assertEquals(3 * PI / 4, new Vector(-1.0, 1.0).angle(), 1e-9);
    }

    @Test
    void angleToComputesSignedAngleBetweenVectors() {
        // From +X to +Y: +90°
        assertEquals(PI / 2,
                Vector.UNIT_X.angleTo(Vector.UNIT_Y),
                1e-9);

        // From +Y to +X: -90°
        assertEquals(-PI / 2,
                Vector.UNIT_Y.angleTo(Vector.UNIT_X),
                1e-9);

        // From +X to -X: 180° or -180° (atan2 behavior)
        double angle = Vector.UNIT_X.angleTo(new Vector(-1.0, 0.0));
        assertTrue(abs(abs(angle) - PI) < 1e-9);

        // Same direction: 0
        assertEquals(0.0,
                Vector.UNIT_X.angleTo(Vector.UNIT_X),
                1e-9);
    }

    @Test
    void rotatedPerforms2DRotation() {
        Vector v = Vector.UNIT_X;

        Vector rotated90 = v.rotated(PI / 2);
        assertEquals(0.0, rotated90.x(), 1e-8);
        assertEquals(1.0, rotated90.y(), 1e-8);

        Vector rotated180 = v.rotated(PI);
        assertEquals(-1.0, rotated180.x(), 1e-8);
        assertEquals(0.0, rotated180.y(), 1e-8);
    }

    @Test
    void interpolatePerformsLinearInterpolation() {
        Vector a = new Vector(0.0, 0.0);
        Vector b = new Vector(10.0, 20.0);

        Vector at0 = a.interpolate(b, 0.0);
        assertEquals(a.x(), at0.x(), EPS);
        assertEquals(a.y(), at0.y(), EPS);

        Vector at1 = a.interpolate(b, 1.0);
        assertEquals(b.x(), at1.x(), EPS);
        assertEquals(b.y(), at1.y(), EPS);

        Vector atHalf = a.interpolate(b, 0.5);
        assertEquals(5.0, atHalf.x(), EPS);
        assertEquals(10.0, atHalf.y(), EPS);
    }

    @Test
    void clampLengthLeavesShortVectorsUnchanged() {
        Vector v = new Vector(3.0, 4.0); // length 5
        Vector clamped = v.clampLength(10.0);

        // length < maxLen, should return same vector (by value)
        assertEquals(v.x(), clamped.x(), EPS);
        assertEquals(v.y(), clamped.y(), EPS);
    }

    @Test
    void clampLengthScalesDownLongVectorsToMaxLength() {
        Vector v = new Vector(3.0, 4.0); // length 5
        Vector clamped = v.clampLength(2.0);

        assertEquals(2.0, clamped.length(), 1e-9);

        // Should be same direction as v
        double scale = 2.0 / 5.0;
        assertEquals(3.0 * scale, clamped.x(), 1e-9);
        assertEquals(4.0 * scale, clamped.y(), 1e-9);
    }

    @Test
    void almostEqualsUsesComponentWiseTolerance() {
        Vector a = new Vector(1.0, 2.0);
        Vector b = new Vector(1.0000001, 1.9999999);

        assertTrue(a.almostEquals(b, 1e-5));
        assertFalse(a.almostEquals(b, 1e-8));

        Vector c = new Vector(1.0 + 1e-6, 2.0 + 1e-6);
        assertTrue(a.almostEquals(c, 1e-5));
    }

}