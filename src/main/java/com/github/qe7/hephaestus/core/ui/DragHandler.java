package com.github.qe7.hephaestus.core.ui;

import com.github.qe7.hephaestus.utils.math.vec.Vector2f;
import lombok.Getter;

/**
 * Handles dragging of UI elements.
 */
public class DragHandler {
    @Getter
    private boolean dragging;

    @Getter
    private final Vector2f position = new Vector2f();
    private final Vector2f offsetPosition = new Vector2f();

    public DragHandler(float x, float y) {
        this.position.set(x, y);
    }

    public void handleDrag(float mouseX, float mouseY) {
        if (this.dragging) {
            this.getPosition().set(mouseX + this.offsetPosition.getX(), mouseY + this.offsetPosition.getY());
        }
    }

    public void handleMouseClick(float mouseX, float mouseY, int mouseButton) {
        if (mouseButton == 0) {
            this.dragging = true;
            this.offsetPosition.set(this.getPosition().getX() - mouseX, this.getPosition().getY() - mouseY);
        }
    }

    public void handleMouseRelease() {
        this.dragging = false;
    }
}
