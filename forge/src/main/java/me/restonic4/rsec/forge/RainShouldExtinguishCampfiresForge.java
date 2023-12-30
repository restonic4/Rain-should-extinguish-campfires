package me.restonic4.rsec.forge;

import dev.architectury.platform.forge.EventBuses;
import me.restonic4.rsec.RainShouldExtinguishCampfires;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RainShouldExtinguishCampfires.MOD_ID)
public class RainShouldExtinguishCampfiresForge {
    public RainShouldExtinguishCampfiresForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(RainShouldExtinguishCampfires.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        RainShouldExtinguishCampfires.init();
    }
}