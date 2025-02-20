package io.github.qe7.features.impl.modules.api.settings.impl;

import com.google.gson.JsonObject;
import io.github.qe7.features.impl.modules.api.settings.api.Setting;

public final class BooleanSetting extends Setting<Boolean> {

    public BooleanSetting(String name, Boolean defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public JsonObject serialize() {
        final JsonObject object = new JsonObject();

        object.addProperty("value", this.getValue());

        return object;
    }

    @Override
    public void deserialize(JsonObject object) {
        if (!object.has("value") || !object.get("value").isJsonPrimitive()) {
            return;
        }

        this.setValue(object.get("value").getAsBoolean());
    }
}
