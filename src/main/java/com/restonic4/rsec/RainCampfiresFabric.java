package com.restonic4.rsec;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RainCampfiresFabric implements ModInitializer {
    public static final String MOD_ID = "rsec";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Did you know that rain should extinguish campfires?");
        LOGGER.info("Yeah, this mod fixes that. With one simple mixin, that's cool.");
    }
}
