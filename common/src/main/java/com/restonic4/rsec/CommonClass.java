package com.restonic4.rsec;

import com.restonic4.rsec.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

public class CommonClass {
    public static void init() {
        Constants.LOG.info("Did you know that rain should extinguish campfires?");
        Constants.LOG.info("Yeah, this mod fixes that. With one simple mixin, that's cool.");
    }
}
