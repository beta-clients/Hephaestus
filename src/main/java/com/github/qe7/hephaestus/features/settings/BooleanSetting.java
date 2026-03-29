package com.github.qe7.hephaestus.features.settings;

import com.github.qe7.hephaestus.core.feature.setting.AbstractSetting;
import com.github.qe7.hephaestus.core.ui.Component;
import com.github.qe7.hephaestus.core.ui.component.special.ToggleableComponent;
import com.google.gson.JsonObject;

public final class BooleanSetting extends AbstractSetting<Boolean> {

    public BooleanSetting(String name, Boolean defaultValue) {
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
            setValue(object.get(getName()).getAsBoolean());
        }
    }

    @Override
    public Component getComponent() {
        return new ToggleableComponent(getName(), this::getValue, this::setValue);
    }
}
