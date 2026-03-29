package com.github.qe7.hephaestus.utils.math.vec;

import lombok.Getter;

import java.util.Objects;

/**
 * A mutable 2D vector class with float precision.
 * Provides common vector operations and utility methods.
 */
@Getter
public class Vector2f {
    private float x;
    private float y;

    public Vector2f() {
        this(0, 0);
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2f of(float x, float y) {
        return new Vector2f(x, y);
    }

    public Vector2f setX(float x) {
        this.x = x;
        return this;
    }

    public Vector2f setY(float y) {
        this.y = y;
        return this;
    }

    public Vector2f set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    // Mutating operations (return this for chaining)
    public Vector2f add(Vector2f other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vector2f sub(Vector2f other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public Vector2f mul(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
        return this;
    }

    public Vector2f div(float scalar) {
        if (scalar == 0) throw new ArithmeticException("Division by zero");
        this.x /= scalar;
        this.y /= scalar;
        return this;
    }

    public Vector2f added(Vector2f other) {
        return new Vector2f(this.x + other.x, this.y + other.y);
    }

    public Vector2f subtracted(Vector2f other) {
        return new Vector2f(this.x - other.x, this.y - other.y);
    }

    public Vector2f multiplied(float scalar) {
        return new Vector2f(this.x * scalar, this.y * scalar);
    }

    public float dot(Vector2f other) {
        return this.x * other.x + this.y * other.y;
    }

    public long lengthSquared() {
        return (long) (x * x) + (long) (y * y);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public long distanceSquared(Vector2f other) {
        long dx = (long) (this.x - other.x);
        long dy = (long) (this.y - other.y);
        return dx * dx + dy * dy;
    }

    public double distance(Vector2f other) {
        return Math.sqrt(distanceSquared(other));
    }

    public Vector2f clamp(float minX, float minY, float maxX, float maxY) {
        if (minX > maxX || minY > maxY) throw new IllegalArgumentException("min must be <= max");
        this.x = Math.max(minX, Math.min(this.x, maxX));
        this.y = Math.max(minY, Math.min(this.y, maxY));
        return this;
    }

    public Vector2f copy() {
        return new Vector2f(this.x, this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2f)) return false;
        Vector2f vector2I = (Vector2f) o;
        return x == vector2I.x && y == vector2I.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vector2f{" + "x=" + x + ", y=" + y + '}';
    }
}
