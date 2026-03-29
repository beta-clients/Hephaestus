package com.github.qe7.hephaestus.core.ui.component;

import com.github.qe7.hephaestus.core.ui.Component;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ParentComponent extends AbstractComponent {
    public final List<Component> children = new ArrayList<>();

    @Getter
    @Setter
    private boolean expanded = false;

    @Getter
    @Setter
    private int titleHeight = 0;

    public ParentComponent(String name) {
        super(name);
    }

    public void drawComponent(float mouseX, float mouseY, float partialTicks, boolean hovered, boolean focused) {
        final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

        Gui.drawRect(this.getPosition().getX(),
                this.getPosition().getY(),
                this.getPosition().getX() + this.getWidth(),
                this.getPosition().getY() + this.getHeight(),
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
        // @qe7
        // TODO: enable scissor
        this.drawComponent(mouseX, mouseY, partialTicks, this.isHovered(mouseX, mouseY), false);

        float childrenHeight = 0.5f; // magic number for style reasons - qe7
        float indent = 1;

        if (this instanceof WindowComponent) {
            childrenHeight += this.getTitleHeight();
        }


        if (this.isExpanded() && !this.children.isEmpty()) {
            if (!(this instanceof WindowComponent)) {
                childrenHeight += getHeight() - 0.5f;
            }

            for (Component child : this.children) {
                child.getPosition().setX(this.getPosition().getX() + indent);
                child.getPosition().setY(this.getPosition().getY() + childrenHeight);
                child.setWidth(this.getWidth() - indent - (this instanceof WindowComponent ? 1 : 0));
                child.drawScreen(mouseX, mouseY, partialTicks);

                childrenHeight += child.getHeight();
            }
        }

        if (!this.children.isEmpty()) {
            this.setHeight(this.getDefaultHeight());
        }

        if (childrenHeight > this.getHeight()) {
            this.setHeight(childrenHeight);

            if (this instanceof WindowComponent) {
                this.setHeight(this.getHeight() + 0.5f);
            }
        }

        // @qe7
        // TODO: disable scissor
    }

    @Override
    public boolean keyTyped(char typedChar, int keyCode) {
        if (this.isExpanded()) {
            for (Component child : this.children) {
                if (child.keyTyped(typedChar, keyCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean mouseClicked(float mouseX, float mouseY, int mouseButton) {
        if (this.isExpanded()) {
            for (Component child : this.children) {
                if (child.mouseClicked(mouseX, mouseY, mouseButton)) {
                    return true;
                }
            }
        }

        if (this.isHovered(mouseX, mouseY)) {
            if (mouseButton == 1 && !this.children.isEmpty()) {
                this.setExpanded(!this.isExpanded());
            }
            return true;
        }

        return false;
    }

    @Override
    public void mouseMovedOrUp(float mouseX, float mouseY, int mouseButton) {
        if (this.isExpanded()) {
            for (Component child : this.children) {
                child.mouseMovedOrUp(mouseX, mouseY, mouseButton);
            }
        }
    }
}
