package com.github.qe7.hephaestus.ui.click;

import com.github.qe7.hephaestus.Hephaestus;
import com.github.qe7.hephaestus.core.feature.module.AbstractModule;
import com.github.qe7.hephaestus.core.feature.module.ModuleCategory;
import com.github.qe7.hephaestus.core.ui.AbstractHephaestusGui;
import com.github.qe7.hephaestus.core.ui.component.WindowComponent;
import com.github.qe7.hephaestus.features.modules.client.ClickGuiModule;
import com.github.qe7.hephaestus.services.managers.ModuleManager;

public final class ClickHephaestusGui extends AbstractHephaestusGui {
    @Override
    public void load() {
        for (ModuleCategory moduleCategory : ModuleCategory.values()) {
            WindowComponent windowComponent = new WindowComponent(moduleCategory.getName());

            windowComponent.setWidth(120);
            windowComponent.setHeight(0);
            windowComponent.getPosition().setY(5);

            for (AbstractModule abstractModule : Hephaestus.getInstance().getServices().get(ModuleManager.class).getModulesFromCategory(moduleCategory)) {
                windowComponent.children.add(abstractModule.getComponent());
            }

            windowComponent.setExpanded(!windowComponent.children.isEmpty());
            this.children.add(windowComponent);
        }
    }

    @Override
    public void unload() {
        Hephaestus.getInstance().getServices().get(ModuleManager.class).get(ClickGuiModule.class).setEnabled(false);
    }

    @Override
    public boolean renderBackground() {
        return false;
    }
}
