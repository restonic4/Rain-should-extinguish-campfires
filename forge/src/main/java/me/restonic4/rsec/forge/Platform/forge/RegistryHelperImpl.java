package me.restonic4.rsec.forge.Platform.forge;

import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static me.restonic4.rsec.RainShouldExtinguishCampfires.MOD_ID;

public final class RegistryHelperImpl{
    public static final DeferredRegister<PoiType> POINT_OF_INTEREST_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, MOD_ID);

    public static <T extends PoiType> Supplier<T> registerPointOfInterestType(String name, Supplier<T> pointOfInterestType) {
        return POINT_OF_INTEREST_TYPES.register(name, pointOfInterestType);
    }
}
