package com.github.qe7.hephaestus.utils.math.vec;

import lombok.Getter;

import java.util.Objects;

/**
 * A mutable 2D vector class with int precision.
 * Provides common vector operations and utility methods.
 */
@Getter
public class Vector2i {
    private int x;
    private int y;

    public Vector2i() {
        this(0, 0);
    }

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2i of(int x, int y) {
        return new Vector2i(x, y);
    }

    public Vector2i setX(int x) {
        this.x = x;
        return this;
    }

    public Vector2i setY(int y) {
        this.y = y;
        return this;
    }

    public Vector2i set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    // Mutating operations (return this for chaining)
    public Vector2i add(Vector2i other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vector2i sub(Vector2i other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public Vector2i mul(int scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public Vector2i div(int scalar) {
        if (scalar == 0) throw new ArithmeticException("Division by zero");
        this.x /= scalar;
        this.y /= scalar;
        return this;
    }

    public Vector2i added(Vector2i other) {
        return new Vector2i(this.x + other.x, this.y + other.y);
    }

    public Vector2i subtracted(Vector2i other) {
        return new Vector2i(this.x - other.x, this.y - other.y);
    }

    public Vector2i multiplied(int scalar) {
        return new Vector2i(this.x * scalar, this.y * scalar);
    }

    public int dot(Vector2i other) {
        return this.x * other.x + this.y * other.y;
    }

    public long lengthSquared() {
        return (long) x * x + (long) y * y;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public long distanceSquared(Vector2i other) {
        long dx = (long) this.x - other.x;
        long dy = (long) this.y - other.y;
        return dx * dx + dy * dy;
    }

    public double distance(Vector2i other) {
        return Math.sqrt(distanceSquared(other));
    }

    public Vector2i clamp(int minX, int minY, int maxX, int maxY) {
        if (minX > maxX || minY > maxY) throw new IllegalArgumentException("min must be <= max");
        this.x = Math.max(minX, Math.min(this.x, maxX));
        this.y = Math.max(minY, Math.min(this.y, maxY));
        return this;
    }

    public Vector2i copy() {
        return new Vector2i(this.x, this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2i)) return false;
        Vector2i vector2I = (Vector2i) o;
        return x == vector2I.x && y == vector2I.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vector2i{" + "x=" + x + ", y=" + y + '}';
    }
}
