package io.github.qe7.features.impl.modules.api;

import com.google.gson.JsonObject;
import io.github.qe7.Hephaestus;
import io.github.qe7.features.impl.commands.api.Command;
import io.github.qe7.features.impl.modules.api.settings.api.Setting;
import io.github.qe7.features.impl.modules.api.settings.impl.BooleanSetting;
import io.github.qe7.features.impl.modules.api.settings.impl.DoubleSetting;
import io.github.qe7.features.impl.modules.api.settings.impl.IntSetting;
import io.github.qe7.utils.ChatUtil;
import io.github.qe7.utils.config.Serialized;
import lombok.Getter;
import lombok.Setter;
import me.zero.alpine.listener.Subscriber;

import java.util.Objects;

@Getter
@Setter
public abstract class Module extends Command implements Subscriber, Serialized {

    // Module category
    private final ModuleCategory category;

    // Key bind
    private int keyBind;

    // State
    private boolean enabled;

    // Constructor
    public Module(final String name, final String description, final ModuleCategory category) {
        super(name, description);
        this.category = category;
    }

    /**
     * Called when the module is enabled
     */
    public void onEnable() {
        Hephaestus.getInstance().getEventBus().subscribe(this);
    }

    /**
     * Called when the module is disabled
     */
    public void onDisable() {
        Hephaestus.getInstance().getEventBus().unsubscribe(this);
    }

    /**
     * Sets the module to enabled or disabled
     *
     * @param enabled the new state of the module
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    /**
     * Toggles the module
     */
    public void toggle() {
        enabled = !enabled;

        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    /**
     * Executes the command associated with the module
     *
     * @param args the arguments passed to the command
     */
    @Override
    public void execute(String[] args) {
        if (args.length == 1) {
            if (Objects.equals(this.getUsage(), "No usage provided")) {
                // Create new usage, if not provided (based off settings)
                // Formatted as "module <setting> <value>"
                StringBuilder usage = new StringBuilder(this.getName());

                if (Hephaestus.getInstance().getModuleManager().getSettingsByModule(this).isEmpty()) {
                    this.setUsage("No settings available");
                } else {
                    for (Setting<?> setting : Hephaestus.getInstance().getModuleManager().getSettingsByModule(this)) {
                        usage.append(" <").append(setting.getName().replace(" ", "")).append(">");
                    }
                    this.setUsage(usage.toString());
                }

                ChatUtil.addPrefixedMessage(this.getClass().getSimpleName(), "Usage: " + this.getUsage());
            } else {
                ChatUtil.addPrefixedMessage(this.getClass().getSimpleName(), "Usage: " + this.getUsage());
            }
            return;
        }

        if (args.length == 2 || args.length == 3) {
            for (Setting<?> setting : Hephaestus.getInstance().getModuleManager().getSettingsByModule(this)) {
                if (!setting.getName().replace(" ", "").equalsIgnoreCase(args[1])) {
                    continue;
                }

                if (args.length == 2) {
                    ChatUtil.addPrefixedMessage(this.getClass().getSimpleName(), setting.getName() + ": " + setting.getValue());
                    return;
                }

                if (setting instanceof BooleanSetting) {
                    BooleanSetting booleanSetting = (BooleanSetting) setting;

                    if (!args[2].equalsIgnoreCase("true") && !args[2].equalsIgnoreCase("false")) {
                        ChatUtil.addPrefixedMessage(this.getClass().getSimpleName(), "Invalid value");
                        return;
                    }

                    booleanSetting.setValue(Boolean.parseBoolean(args[2]));
                    ChatUtil.addPrefixedMessage(this.getClass().getSimpleName(), "Set " + setting.getName() + " to " + args[2]);
                    return;
                }
                if (setting instanceof IntSetting) {
                    IntSetting intSetting = (IntSetting) setting;

                    if (!args[2].matches("[0-9]+")) {
                        ChatUtil.addPrefixedMessage(this.getClass().getSimpleName(), "Invalid value");
                        return;
                    }

                    if (intSetting.getMinimum() != Integer.MIN_VALUE && Integer.parseInt(args[2]) < intSetting.getMinimum()) {
                        intSetting.setValue(intSetting.getMinimum());
                        ChatUtil.addPrefixedMessage(this.getClass().getSimpleName(), "Set " + setting.getName() + " to " + intSetting.getMinimum());
                        return;
                    }

                    if (intSetting.getMaximum() != Integer.MAX_VALUE && Integer.parseInt(args[2]) > intSetting.getMaximum()) {
                        intSetting.setValue(intSetting.getMaximum());
                        ChatUtil.addPrefixedMessage(this.getClass().getSimpleName(), "Set " + setting.getName() + " to " + intSetting.getMaximum());
                        return;
                    }

                    intSetting.setValue(Integer.parseInt(args[2]));
                    ChatUtil.addPrefixedMessage(this.getClass().getSimpleName(), "Set " + setting.getName() + " to " + args[2]);
                    return;
                }
                if (setting instanceof DoubleSetting) {
                    DoubleSetting doubleSetting = (DoubleSetting) setting;

                    if (!args[2].matches("[-+]?[0-9]*\\.?[0-9]+")) {
                        ChatUtil.addPrefixedMessage(this.getClass().getSimpleName(), "Invalid value");
                        return;
                    }

                    if (doubleSetting.getMinimum() != Double.MIN_VALUE && Double.parseDouble(args[2]) < doubleSetting.getMinimum()) {
                        doubleSetting.setValue(doubleSetting.getMinimum());
                        ChatUtil.addPrefixedMessage(this.getClass().getSimpleName(), "Set " + setting.getName() + " to " + doubleSetting.getMinimum());
                        return;
                    }

                    if (doubleSetting.getMaximum() != Double.MAX_VALUE && Double.parseDouble(args[2]) > doubleSetting.getMaximum()) {
                        doubleSetting.setValue(doubleSetting.getMaximum());
                        ChatUtil.addPrefixedMessage(this.getClass().getSimpleName(), "Set " + setting.getName() + " to " + doubleSetting.getMaximum());
                        return;
                    }

                    doubleSetting.setValue(Double.parseDouble(args[2]));
                    ChatUtil.addPrefixedMessage(this.getClass().getSimpleName(), "Set " + setting.getName() + " to " + args[2]);
                    return;
                }
                ChatUtil.addPrefixedMessage(this.getClass().getSimpleName(), "Invalid setting type");
                return;
            }
        }
    }

    @Override
    public JsonObject serialize() {
        final JsonObject object = new JsonObject();

        object.addProperty("enabled", enabled);
        object.addProperty("keyBind", keyBind);

        if (!Hephaestus.getInstance().getModuleManager().getSettingsByModule(this).isEmpty()) {
            final JsonObject settings = new JsonObject();

            for (Setting<?> setting : Hephaestus.getInstance().getModuleManager().getSettingsByModule(this)) {
                settings.addProperty(setting.getName(), setting.getValue().toString());
            }

            object.add("settings", settings);
        }

        return object;
    }

    @Override
    public void deserialize(JsonObject object) {

    }
}
