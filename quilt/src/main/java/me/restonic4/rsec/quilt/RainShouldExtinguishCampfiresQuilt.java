package me.restonic4.rsec.quilt;

import me.restonic4.rsec.RainShouldExtinguishCampfires;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class RainShouldExtinguishCampfiresQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        RainShouldExtinguishCampfires.init();
    }
}