package com.restonic4.rsec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.restonic4.rsec.platform.Services;

import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    // Look at CONFIG.MD to understand how each one works
    public int rainLookupMinTickRange = 120;
    public int rainLookupMaxTickRange = 1200;
    public boolean requirePrecipitationBiome = true;
    public DetectionMode detectionMode = DetectionMode.RAYCAST;
    public int skyLightThreshold = 15;
    public int requiredOpenNeighboursToExtinguish = 3;
    public int neighbourCheckRadius = 1;

    private static final Path FILE_PATH = Services.PLATFORM.getConfigDirectory().resolve(Constants.MOD_ID + ".json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static Config INSTANCE = new Config();

    public static void load() {
        try {
            if (Files.exists(FILE_PATH)) {
                try (var reader = Files.newBufferedReader(FILE_PATH)) {
                    INSTANCE = GSON.fromJson(reader, Config.class);
                }
            } else {
                save();
            }
        } catch (Exception e) {
            Constants.LOG.error("Failed to load config!", e);
        }
    }

    public static void save() {
        try {
            Files.createDirectories(FILE_PATH.getParent());
            Files.writeString(FILE_PATH, GSON.toJson(INSTANCE));
        } catch (Exception e) {
            Constants.LOG.error("Failed to save config!", e);
        }
    }

    public enum DetectionMode { RAYCAST, SKY_LIGHT }
}
