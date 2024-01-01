package me.restonic4.rsec.fabric.Platform.fabric;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;

import java.util.function.Supplier;

import static me.restonic4.rsec.RainShouldExtinguishCampfires.MOD_ID;

public final class RegistryHelperImpl {
    public static <T extends PoiType> Supplier<T> registerPointOfInterestType(String name, Supplier<T> pointOfInterestType) {
        var registry = Registry.register(BuiltInRegistries.POINT_OF_INTEREST_TYPE, new ResourceLocation(MOD_ID, name), pointOfInterestType.get());
        return () -> registry;
    }
}