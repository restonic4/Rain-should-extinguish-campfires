package com.restonic4.rsec;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(Constants.MOD_ID)
public class RainShouldExtinguishCampfiresNeoForge {
    public RainShouldExtinguishCampfiresNeoForge(IEventBus eventBus) {
        RainShouldExtinguishCampfires.init();
    }
}