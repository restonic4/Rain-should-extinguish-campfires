package com.restonic4.rsec;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import java.util.concurrent.ThreadLocalRandom;

public class ConditionsManager {
    public static boolean isRainingOrThundering(ServerLevel level) {
        return level.isRaining() || level.isThundering();
    }

    public static boolean canRainingAtPosition(ServerLevel level, BlockPos pos) {
        if (!Config.INSTANCE.requirePrecipitationBiome) return true;
        Biome biome = level.getBiome(pos).value();
        return biome.hasPrecipitation();
    }

    public static boolean hasSkyVisibilityRaycast(ServerLevel level, BlockPos pos) {
        int maxY = level.getMaxBuildHeight();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos(pos.getX(), pos.getY() + 1, pos.getZ());

        while (mutable.getY() < maxY) {
            BlockState state = level.getBlockState(mutable);
            if (!state.propagatesSkylightDown(level, mutable)) {
                return false;
            }
            mutable.setY(mutable.getY() + 1);
        }

        return true;
    }

    private static final BlockPos.MutableBlockPos SKY_LIGHT_CHECK_POS = new BlockPos.MutableBlockPos();
    public static boolean hasSkyVisibilitySkyLight(ServerLevel level, int x, int y, int z) {
        SKY_LIGHT_CHECK_POS.set(x, y, z);
        int skyLight = level.getLightEngine()
                .getLayerListener(LightLayer.SKY)
                .getLightValue(SKY_LIGHT_CHECK_POS);
        return skyLight >= Config.INSTANCE.skyLightThreshold;
    }

    public static boolean hasSkyVisibility(ServerLevel level, BlockPos pos) {
        return hasSkyVisibility(level, pos.getX(), pos.getY(), pos.getZ());
    }

    public static boolean hasSkyVisibility(ServerLevel level, int x, int y, int z) {
        if (Config.INSTANCE.detectionMode == Config.DetectionMode.RAYCAST) {
            BlockPos immutable = new BlockPos(x, y, z);
            return hasSkyVisibilityRaycast(level, immutable);
        } else {
            return hasSkyVisibilitySkyLight(level, x, y, z);
        }
    }

    public static boolean isExposedToRain(ServerLevel level, BlockPos blockPos) {
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();

        // The campfire's own column must always be open.
        if (!hasSkyVisibility(level, blockPos)) return false;

        int required = Config.INSTANCE.requiredOpenNeighboursToExtinguish;
        if (required <= 0) return true;

        int r = Math.max(1, Config.INSTANCE.neighbourCheckRadius);
        int open = 0;

        for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {
                if (dx == 0 && dz == 0) continue; // own column, skip
                if (hasSkyVisibility(level, x + dx, y, z + dz)) {
                    if (++open >= required) return true;
                }
            }
        }

        return false;
    }

    public static int getRandomInt(int min, int max) {
        if (min == max) {
            return min;
        }

        if (min > max) {
            int temp = min;
            min = max;
            max = temp;
        }

        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
