package io.github.qe7.features.impl.modules.impl.movement;

import io.github.qe7.events.UpdateEvent;
import io.github.qe7.features.impl.modules.api.Module;
import io.github.qe7.features.impl.modules.api.ModuleCategory;
import io.github.qe7.utils.MovementUtil;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiContainer;
import net.minecraft.src.GuiInventory;
import org.lwjgl.input.Keyboard;

public class InventoryMoveModule extends Module {

    public InventoryMoveModule() {
        super("InventoryMove", "Allows you to move while your inventory is open", ModuleCategory.MOVEMENT);
    }

    @Subscribe
    public final Listener<UpdateEvent> updateEventListener = new Listener<>(event -> {
        if (Minecraft.getMinecraft().currentScreen == null) {
            return;
        }

        if (!(Minecraft.getMinecraft().currentScreen instanceof GuiInventory || Minecraft.getMinecraft().currentScreen instanceof GuiContainer)) {
            return;
        }

        float moveForward = 0.0F;
        float moveStrafe = 0.0F;

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
            Minecraft.getMinecraft().thePlayer.rotationYaw -= 5.0F;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
            Minecraft.getMinecraft().thePlayer.rotationYaw += 5.0F;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
            Minecraft.getMinecraft().thePlayer.rotationPitch -= 5.0F;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
            Minecraft.getMinecraft().thePlayer.rotationPitch += 5.0F;
        }

        if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindForward.keyCode)) {
            moveForward += 1.0F;
        }

        if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindBack.keyCode)) {
            moveForward -= 1.0F;
        }

        if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindLeft.keyCode)) {
            moveStrafe += 1.0F;
        }

        if (Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindRight.keyCode)) {
            moveStrafe -= 1.0F;
        }

        MovementUtil.setSpeed(0.22d, Minecraft.getMinecraft().thePlayer.rotationYaw, moveStrafe, moveForward);
    });
}