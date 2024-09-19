package io.github.qe7.utils;

import net.minecraft.client.Minecraft;

public final class ChatUtil {

    private ChatUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Sends a message to the chat.
     *
     * @param message the message to send
     */
    public static void addMessage(final String message) {
        final Minecraft mc = Minecraft.getMinecraft();

        if (mc.thePlayer == null) return;

        mc.thePlayer.addChatMessage(message);
    }

    /**
     * Sends a prefixed message to the chat.
     *
     * @param prefix  the prefix to use
     * @param message the message to send
     */
    public static void addPrefixedMessage(final String prefix, final String message) {
        addMessage("§7(" + prefix + ") §r§f" + message);
    }

    /**
     * Sends a message to the chat.
     *
     * @param message the message to send
     */
    public static void sendMessage(final String message) {
        final Minecraft mc = Minecraft.getMinecraft();

        if (mc.thePlayer == null) return;

        mc.thePlayer.sendChatMessage(message);
    }
}
