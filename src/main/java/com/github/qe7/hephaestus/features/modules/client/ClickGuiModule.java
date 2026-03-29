package com.github.qe7.hephaestus.features.modules.client;

import com.github.qe7.hephaestus.core.event.EventListener;
import com.github.qe7.hephaestus.core.event.EventSubscriber;
import com.github.qe7.hephaestus.core.feature.module.AbstractModule;
import com.github.qe7.hephaestus.core.feature.module.ModuleCategory;
import com.github.qe7.hephaestus.events.UpdateEvent;
import com.github.qe7.hephaestus.ui.click.ClickHephaestusGui;
import org.lwjgl.input.Keyboard;

/**
 * @author qe7
 * @since 2.0.0
 */
public final class ClickGuiModule extends AbstractModule {
    private ClickHephaestusGui screen;

    public ClickGuiModule() {
        super("Click GUI", "Displays a clickable interface to manage modules and their settings", ModuleCategory.CLIENT);

        this.getKeybind().setValue(Keyboard.KEY_RSHIFT); // Default keybind for opening the click GUI, can be changed by the user in the settings. - qe7
    }

    @Override
    public void onEnable() {
        if (this.minecraft.thePlayer == null || this.minecraft.theWorld == null) {
            this.setEnabled(false);
            return;
        }

        if (this.screen == null) {
            this.screen = new ClickHephaestusGui();
            this.screen.loadComponents();
        }

        this.minecraft.displayGuiScreen(this.screen);
    }

    @Override
    public void onDisable() {
        if (this.minecraft.currentScreen == this.screen) {
            this.minecraft.displayGuiScreen(null);
        }
    }

    @EventSubscriber
    private final EventListener<UpdateEvent> updateEventListener = event -> {
        if (this.screen != null && this.minecraft.currentScreen != this.screen) {
            this.setEnabled(false);
        }
    };
}
