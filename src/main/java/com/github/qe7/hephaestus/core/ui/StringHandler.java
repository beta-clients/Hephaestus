package com.github.qe7.hephaestus.core.ui;

import com.github.qe7.hephaestus.utils.Stopwatch;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.TimeUnit;

/**
 * Handles the string that is being typed.
 */
public class StringHandler {
    private final Stopwatch idleStopwatch = new Stopwatch();

    @Getter
    private boolean idle = false;

    @Getter
    @Setter
    private String currentString = "";
    @Getter
    @Setter
    private String fallbackString = "";

    public boolean isFallback() {
        return this.currentString.isEmpty();
    }

    public String getIdleString() {
        return (this.isFallback() ? this.fallbackString : this.currentString) + this.getIdleSign();
    }

    public String getIdleSign() {
        this.updateIdleState();
        return this.idle ? "_" : "";
    }

    public void update(char chr) {
        if (this.isAscii(chr)) {
            this.idle = false;
            this.idleStopwatch.reset();
            this.setCurrentString(this.getCurrentString() + chr);
        }
    }

    public void delete() {
        this.setCurrentString(this.removeLastChar(this.getCurrentString()));
    }

    public String removeLastChar(String string) {
        if (string == null || string.isEmpty()) {
            return "";
        }

        return string.substring(0, string.length() - 1);
    }

    private boolean isAscii(int codePoint) {
        return codePoint >= 0x20 && codePoint <= 0x7E;
    }

    private boolean isAscii(char c) {
        return this.isAscii((int) c);
    }

    private void updateIdleState() {
        if (!this.idleStopwatch.hasTimeElapsed(500, TimeUnit.MILLISECONDS)) {
            return;
        }

        this.idle = !this.idle;
        this.idleStopwatch.reset();
    }
}
