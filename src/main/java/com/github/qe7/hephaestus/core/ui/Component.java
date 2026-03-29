package com.github.qe7.hephaestus.core.ui;

import com.github.qe7.hephaestus.utils.math.vec.Vector2f;

/**
 * Component interface for UI components.
 */
public interface Component {
    Vector2f getPosition();

    Vector2f getSize();

    float getDefaultHeight();

    default float getX() {
        return getPosition().getX();
    }

    default float getY() {
        return getPosition().getY();
    }

    default float getWidth() {
        return getSize().getX();
    }

    default float getHeight() {
        return getSize().getY();
    }

    void setDefaultHeight(float defaultHeight);

    void setPosition(float x, float y);

    void setSize(float width, float height);

    default void setHeight(float height) {
        setSize(getWidth(), height);
    }

    default void setWidth(float width) {
        setSize(width, getHeight());
    }

    default void setPosition(Vector2f position) {
        setPosition(position.getX(), position.getY());
    }

    default void setSize(Vector2f size) {
        setSize(size.getX(), size.getY());
    }

    void drawScreen(float mouseX, float mouseY, float partialTicks);

    boolean keyTyped(char typedChar, int keyCode);

    boolean mouseClicked(float mouseX, float mouseY, int mouseButton);

    void mouseMovedOrUp(float mouseX, float mouseY, int mouseButton);

    default boolean isHovered(float mouseX, float mouseY) {
        return mouseX > getX() && mouseX < getX() + getWidth() &&
               mouseY > getY() && mouseY < getY() + getDefaultHeight();
    }
}
