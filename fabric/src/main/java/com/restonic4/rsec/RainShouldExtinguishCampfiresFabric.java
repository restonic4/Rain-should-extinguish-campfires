package com.restonic4.rsec;

import net.fabricmc.api.ModInitializer;

public class RainShouldExtinguishCampfiresFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        RainShouldExtinguishCampfires.init();
    }
}
