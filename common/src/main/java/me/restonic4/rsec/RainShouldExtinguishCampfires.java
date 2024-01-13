package me.restonic4.rsec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RainShouldExtinguishCampfires
{
	public static final String MOD_ID = "rsec";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static void init() {
		LOGGER.info("----- Rain should extinguish campfires --- 1.19.2 --- Cant use RestApi -----");
		LOGGER.info("Did you know that rain should extinguish campfires?");
		LOGGER.info("Yeah, this mod fixes that. With one simple mixin, that's cool.");
	}
}
