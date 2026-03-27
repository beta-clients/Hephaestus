package com.github.qe7.hephaestus;

import lombok.Getter;

/**
 * Welcome to the recode. (>_<)
 * By Shae
 */
@Getter
public final class Hephaestus {

    @Getter
    private static Hephaestus instance;

    private final String name, version, buildInfo;

    public Hephaestus() {
        instance = this;

        this.name = "Hephaestus";
        this.version = "2.0.0";
        this.buildInfo = String.format("%s.%s", BuildConstants.GIT_REVISION, BuildConstants.GIT_COMMIT);

        System.out.printf("%s %s (build %s) starting...", name, version, buildInfo);

        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    }

    public void stop() {
        System.out.printf("%s %s stopping...", name, version);
    }
}
