package com.github.qe7.hephaestus.features.settings;

import com.github.qe7.hephaestus.core.feature.setting.AbstractSetting;
import com.github.qe7.hephaestus.core.ui.Component;
import com.github.qe7.hephaestus.core.ui.component.ParentComponent;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public final class KeybindSetting extends AbstractSetting<Integer> {

    public KeybindSetting(String name, Integer defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public JsonObject serialize() {
        JsonObject object = new JsonObject();
        object.addProperty(getName(), getValue());
        return object;
    }

    @Override
    public void deserialize(JsonObject object) {
        if (object.has(getName())) {
            setValue(object.get(getName()).getAsInt());
        }
    }

    @Override
    public Component getComponent() {
        return new ParentComponent(getName()) {
            private boolean listening = false;

            {
                this.setDefaultHeight(12);
            }

            @Override
            public void drawComponent(float mouseX, float mouseY, float partialTicks, boolean hovered, boolean focused) {
                final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

                Gui.drawRect(this.getPosition().getX(),
                        this.getPosition().getY(),
                        this.getPosition().getX() + this.getWidth(),
                        this.getPosition().getY() + this.getTitleHeight(),
                        this.listening ?
                                hovered ? new Color(255, 132, 132, 150).getRGB() : new Color(255, 132, 132, 150).brighter().getRGB() :
                                hovered ? new Color(255, 132, 132, 30).getRGB() : new Color(255, 132, 132, 30).brighter().getRGB()
                );

                fontRenderer.drawStringWithShadow(
                        this.name,
                        (int) (this.getPosition().getX() + 2),
                        (int) this.getPosition().getY() + 2,
                        Color.WHITE.getRGB()
                );

                final String display = this.listening ? "..." : Keyboard.getKeyName(getValue());

                fontRenderer.drawStringWithShadow(
                        display,
                        (int) (this.getPosition().getX() + this.getWidth() - fontRenderer.getStringWidth(display) - 2),
                        (int) this.getPosition().getY() + 2,
                        Color.WHITE.getRGB()
                );

                setDefaultHeight(12);
                setHeight(12);
            }

            @Override
            public boolean keyTyped(char typedChar, int keyCode) {
                if (!listening) {
                    return super.keyTyped(typedChar, keyCode);
                }

                switch (keyCode) {
                    case Keyboard.KEY_DELETE:
                    case Keyboard.KEY_BACK:
                    case Keyboard.KEY_ESCAPE:
                        setValue(Keyboard.KEY_NONE);
                        break;
                    default:
                        setValue(keyCode);
                        break;
                }

                listening = false;
                return true;
            }

            @Override
            public boolean mouseClicked(float mouseX, float mouseY, int mouseButton) {
                if (this.isHovered(mouseX, mouseY)) {
                    if (mouseButton == 0) {
                        listening = !listening;
                    } else if (mouseButton == 1) {
                        setValue(Keyboard.KEY_NONE);
                    }
                    return true;
                }
                return super.mouseClicked(mouseX, mouseY, mouseButton);
            }
        };
    }
}
