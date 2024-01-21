package me.restonic4.rsec.neoforge;

import me.restonic4.rsec.RainShouldExtinguishCampfires;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(RainShouldExtinguishCampfires.MOD_ID)
public class RainShouldExtinguishCampfiresNeoForge {
	public RainShouldExtinguishCampfiresNeoForge(IEventBus modEventBus) {
		RainShouldExtinguishCampfires.init();
	}
}
