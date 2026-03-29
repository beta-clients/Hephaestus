package com.github.qe7.hephaestus.core.ui.component;

import com.github.qe7.hephaestus.core.ui.DragHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;

import java.awt.*;

public class WindowComponent extends ParentComponent {
    private DragHandler dragHandler;

    public WindowComponent(String name) {
        super(name);

        this.setTitleHeight(12);
    }

    @Override
    public void drawComponent(float mouseX, float mouseY, float partialTicks, boolean hovered, boolean focused) {
        final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        focused = this.isExpanded();

        Gui.drawRect(this.getPosition().getX(),
                this.getPosition().getY(),
                this.getPosition().getX() + this.getWidth(),
                this.getPosition().getY() + (this.isExpanded() ? this.getHeight() : this.getTitleHeight()),
                new Color(0, 0, 0, 146).getRGB()
        );

        Gui.drawRect(this.getPosition().getX(),
                this.getPosition().getY(),
                this.getPosition().getX() + this.getWidth(),
                this.getPosition().getY() + this.getTitleHeight(),
                focused ?
                        hovered ? new Color(255, 132, 132, 150).brighter().getRGB() : new Color(255, 132, 132, 150).getRGB() :
                        hovered ? new Color(255, 132, 132, 100).brighter().getRGB() : new Color(255, 132, 132, 100).getRGB()
        );

        fontRenderer.drawStringWithShadow(this.name,
                (int) (this.getPosition().getX() + 2),
                (int) (this.getPosition().getY() + 2),
                Color.WHITE.getRGB()
        );

        if (!this.children.isEmpty()) {
            fontRenderer.drawStringWithShadow(
                    this.isExpanded() ? "-" : "+",
                    (int) (this.getPosition().getX() + this.getWidth() - fontRenderer.getStringWidth(this.isExpanded() ? "-" : "+") - 2),
                    (int) (this.getPosition().getY() + 2),
                    Color.WHITE.getRGB()
            );
        }
    }

    @Override
    public void drawScreen(float mouseX, float mouseY, float partialTicks) {
        if (this.dragHandler == null) {
            this.dragHandler = new DragHandler(this.getPosition().getX(), this.getPosition().getY());
        }

        this.dragHandler.handleDrag(mouseX, mouseY);

        if (this.dragHandler.isDragging()) {
            this.getPosition().set(this.dragHandler.getPosition().getX(), this.dragHandler.getPosition().getY());
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (super.mouseClicked(mouseX, mouseY, mouseButton)) {
            return true;
        }

        if (this.isHovered(mouseX, mouseY)) {
            this.dragHandler.handleMouseClick(mouseX, mouseY, mouseButton);
            return true;
        }

        return false;
    }

    @Override
    public void mouseMovedOrUp(float mouseX, float mouseY, int mouseButton) {
        this.dragHandler.handleMouseRelease();
        super.mouseMovedOrUp(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean isHovered(float mouseX, float mouseY) {
        return mouseX >= getX() && mouseX <= getX() + getWidth() &&
                mouseY >= getY() && mouseY <= getY() + getHeight();
    }
}
