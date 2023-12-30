package me.restonic4.rsec.fabric;

import me.restonic4.rsec.RainShouldExtinguishCampfires;
import net.fabricmc.api.ModInitializer;

public class RainShouldExtinguishCampfiresFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        RainShouldExtinguishCampfires.init();
    }
}