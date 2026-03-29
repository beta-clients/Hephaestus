package com.github.qe7.hephaestus.core.ui;

import com.github.qe7.hephaestus.core.ui.component.WindowComponent;
import com.github.qe7.hephaestus.utils.math.vec.Vector2i;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ScaledResolution;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The base class for all Hephaestus GUIs.
 * It provides common functionality such as rendering components, handling input, and managing the lifecycle of the GUI.
 */
public abstract class AbstractHephaestusGui extends GuiScreen {

    protected final List<Component> children = new ArrayList<>();

    public abstract void load(); // Called when the GUI is opened

    public abstract void unload(); // Called when the GUI is closed

    public abstract boolean renderBackground(); // Whether to render the default background

    /**
     * Loads the components of the GUI. This method should be called after the GUI is initialized and before it is displayed.
     */
    public void loadComponents() {
        this.load();
        this.alignComponents();
    }

    /**
     * Aligns the {@link WindowComponent}s based on the screen size and their dimensions.
     */
    public void alignComponents() {
        Vector2i pos = getStartingPos();

        for (Component child : this.children) {
            if (child instanceof WindowComponent) {
                WindowComponent windowComponent = (WindowComponent) child;

                windowComponent.getPosition().setX(pos.getX());

                pos.setX((int) (pos.getX() + windowComponent.getWidth() + 10));
            }
        }
    }

    private @NotNull Vector2i getStartingPos() {
        int windowTotalWidth = 0;

        for (Component child : this.children) {
            if (child instanceof WindowComponent) {
                WindowComponent windowComponent = (WindowComponent) child;
                windowTotalWidth += (int) (windowComponent.getWidth() + 10);
            }
        }

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft().gameSettings,
                Minecraft.getMinecraft().displayWidth,
                Minecraft.getMinecraft().displayHeight
        );

        int scaledWidth = scaledResolution.getScaledWidth();

        final int startX = (scaledWidth / 2) - (windowTotalWidth / 2);

        return new Vector2i(startX, 20);
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        // @qe7
        // If the renderBackground method returns true, draw the default background.
        // Mainly used for GUIs that want to have the default, but still want to render components on top of it (e.g. ClickGUI in main menu & Account manager).
        if (this.renderBackground()) {
            this.drawDefaultBackground();
        }

        for (Component component : this.children) {
            component.drawScreen(i, j, f);
        }
    }

    @Override
    protected void keyTyped(char c, int i) {
        // @qe7
        // Give components priority over the escape key to allow them to close themselves if needed (e.g. StringComponent)
        for (Component component : this.children) {
            if (component.keyTyped(c, i)) {
                break;
            }
        }

        // qe7
        // If the escape key was pressed and no component consumed the event, close the GUI :)
        if (i == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
            mc.setIngameFocus();
        }
    }

    @Override
    protected void mouseClicked(int i, int j, int k) {
        final List<Component> reversedChildren = IntStream.range(0, this.children.size()).mapToObj(position -> this.children.get(this.children.size() - 1 - position)).collect(Collectors.toList());

        for (Component component : reversedChildren) {
            if (component.mouseClicked(i, j, k)) {
                break;
            }
        }
    }

    @Override
    protected void mouseMovedOrUp(int i, int j, int k) {
        if (k != -1) {
            for (Component component : this.children) {
                component.mouseMovedOrUp(i, j, k);
            }
        }
    }

    @Override
    public void onGuiClosed() {
        this.unload();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
