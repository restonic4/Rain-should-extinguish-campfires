package com.restonic4.rsec;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Random;

public class Util {
    public static boolean isRaining(ServerLevel level) {
        return level.isRaining() || level.isThundering();
    }

    public static boolean canRainingAtBiome(ServerLevel level, BlockPos pos) {
        Biome biome = level.getBiome(pos).value();

        return (biome.hasPrecipitation());
    }

    public static boolean checkRow(ServerLevel level, BlockPos blockPos, int maxY) {
        boolean isOutside = true;

        for (int i = 1; i < maxY; i++) {
            Block foundBlock = level.getBlockState(blockPos.offset(0, i, 0)).getBlock();

            if (foundBlock != Blocks.AIR && foundBlock != Blocks.VOID_AIR && foundBlock != Blocks.CAVE_AIR) {
                isOutside = false;
            }
        }

        return isOutside;
    }

    public static boolean isOutside(boolean mainCheck, boolean check1, boolean check2, boolean check3, boolean check4) {
        boolean isOutside = false;

        if (mainCheck) {
            boolean[] checks = {check1, check2, check3, check4};
            int count = 0;

            for (boolean check : checks) {
                if (check) {
                    count++;
                }
            }

            if (count >= 2) {
                isOutside = true;
            }
        }

        return isOutside;
    }

    public static int getRandomInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Min value should be smaller than Max value!");
        }

        Random random = new Random();

        return random.nextInt((max - min) + 1) + min;
    }

    public static int getTicksForSeconds(int seconds) {
        return seconds * 20;
    }
}
