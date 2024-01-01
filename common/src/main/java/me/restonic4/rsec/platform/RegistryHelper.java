package me.restonic4.rsec.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.ai.village.poi.PoiType;

import java.util.function.Supplier;

public final class RegistryHelper {
    @ExpectPlatform
    public static <T extends PoiType> Supplier<T> registerPointOfInterestType(String name, Supplier<T> pointOfInterestType) {
        throw new AssertionError();
    }

    private RegistryHelper() {}
}
