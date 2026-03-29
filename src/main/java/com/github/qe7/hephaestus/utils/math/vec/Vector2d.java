package com.github.qe7.hephaestus.utils.math.vec;

import lombok.Getter;

import java.util.Objects;

/**
 * A mutable 2D vector class with double precision.
 * Provides common vector operations and utility methods.
 */
@Getter
public class Vector2d {
    private double x;
    private double y;

    public Vector2d() {
        this(0, 0);
    }

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2d of(double x, double y) {
        return new Vector2d(x, y);
    }

    public Vector2d setX(double x) {
        this.x = x;
        return this;
    }

    public Vector2d setY(double y) {
        this.y = y;
        return this;
    }

    public Vector2d set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    // Mutating operations (return this for chaining)
    public Vector2d add(Vector2d other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vector2d sub(Vector2d other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public Vector2d mul(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public Vector2d div(double scalar) {
        if (scalar == 0) throw new ArithmeticException("Division by zero");
        this.x /= scalar;
        this.y /= scalar;
        return this;
    }

    public Vector2d added(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtracted(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public Vector2d multiplied(double scalar) {
        return new Vector2d(this.x * scalar, this.y * scalar);
    }

    public double dot(Vector2d other) {
        return this.x * other.x + this.y * other.y;
    }

    public long lengthSquared() {
        return (long) (x * x) + (long) (y * y);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public long distanceSquared(Vector2d other) {
        long dx = (long) (this.x - other.x);
        long dy = (long) (this.y - other.y);
        return dx * dx + dy * dy;
    }

    public double distance(Vector2d other) {
        return Math.sqrt(distanceSquared(other));
    }

    public Vector2d clamp(double minX, double minY, double maxX, double maxY) {
        if (minX > maxX || minY > maxY) throw new IllegalArgumentException("min must be <= max");
        this.x = Math.max(minX, Math.min(this.x, maxX));
        this.y = Math.max(minY, Math.min(this.y, maxY));
        return this;
    }

    public Vector2d copy() {
        return new Vector2d(this.x, this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2d)) return false;
        Vector2d vector2I = (Vector2d) o;
        return x == vector2I.x && y == vector2I.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vector2d{" + "x=" + x + ", y=" + y + '}';
    }
}
