package me.restonic4.rsec;

import dev.architectury.platform.Platform;
import me.restonic4.restapi.RestApi;
import me.restonic4.restapi.holder.Generic.RestLogger;

public class RainShouldExtinguishCampfires
{
	public static final String MOD_ID = "rsec";
	public static final RestLogger LOGGER = new RestLogger(MOD_ID);

	public static void init() {
		LOGGER.log("Did you know that rain should extinguish campfires?");
		LOGGER.log("Yeah, this mod fixes that. With one simple mixin, that's cool.");
	}
}
