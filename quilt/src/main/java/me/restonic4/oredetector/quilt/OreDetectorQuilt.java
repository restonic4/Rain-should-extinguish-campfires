package me.restonic4.oredetector.quilt;

import me.restonic4.oredetector.OreDetector;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class OreDetectorQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        OreDetector.init();
    }
}