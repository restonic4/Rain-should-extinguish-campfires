package com.example.my_mod.platform.services;

import java.util.Objects;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    default boolean isFabric() {
        return Objects.equals(getPlatformName(), "Fabric");
    }
    default boolean isForge() {
        return Objects.equals(getPlatformName(), "Forge");
    }
    default boolean isNeoForge() {
        return Objects.equals(getPlatformName(), "NeoForge");
    }

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }

    /**
     * Gets the mod's current version string.
     * @return The version string.
     */
    String getModVersion();
}