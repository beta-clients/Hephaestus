package com.github.qe7.hephaestus.core.ui.component;

import com.github.qe7.hephaestus.core.ui.Component;
import com.github.qe7.hephaestus.utils.math.vec.Vector2f;
import lombok.Getter;
import lombok.Setter;

/**
 * Abstract implementation of the {@link Component} interface, providing common functionality for UI components.
 */
public abstract class AbstractComponent implements Component {
    protected final String name;

    @Getter
    protected Vector2f position = new Vector2f();
    @Getter
    protected Vector2f size = new Vector2f();

    @Getter
    @Setter
    protected float defaultHeight;

    public AbstractComponent(String name) {
        this.name = name;
    }

    @Override
    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    @Override
    public void setSize(float width, float height) {
        this.size.set(width, height);
    }
}
