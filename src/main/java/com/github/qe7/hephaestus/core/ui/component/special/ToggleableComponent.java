package com.github.qe7.hephaestus.core.ui.component.special;

import com.github.qe7.hephaestus.core.ui.component.ParentComponent;
import net.minecraft.src.Gui;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class ToggleableComponent extends ParentComponent {
    private final Supplier<Boolean> supplier;
    private final Consumer<Boolean> consumer;

    public ToggleableComponent(String name, Supplier<Boolean> supplier, Consumer<Boolean> consumer) {
        super(name);

        this.supplier = supplier;
        this.consumer = consumer;

        this.setDefaultHeight(12);
    }

    @Override
    public void drawComponent(float mouseX, float mouseY, float partialTicks, boolean hovered, boolean focused) {
        this.setDefaultHeight(12);
        this.setHeight(12);

        super.drawComponent(mouseX, mouseY, partialTicks, hovered, this.supplier.get());
    }

    @Override
    public boolean mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (this.isHovered(mouseX, mouseY) && mouseButton == 0) {
            this.consumer.accept(!this.supplier.get());
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
