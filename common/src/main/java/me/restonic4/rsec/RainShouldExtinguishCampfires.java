package me.restonic4.rsec;

import dev.architectury.platform.Platform;
import me.restonic4.restapi.RestApi;

public class RainShouldExtinguishCampfires
{
	public static final String MOD_ID = "rsec";

	public static void init() {
		RestApi.Log("Did you know that rain should extinguish campfires?");
		RestApi.Log("Yeah, this mod fixes that. With one simple mixin, that's cool.");
	}
}
