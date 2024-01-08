package me.restonic4.rsec.mixin;

import me.restonic4.rsec.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CampfireBlockEntity.class)
public abstract class CampfireBlockEntityMixin {
    @Inject(method = "cookTick", at = @At("TAIL"))
    private static void cookTick(Level level, BlockPos blockPos, BlockState blockState, CampfireBlockEntity campfireBlockEntity, CallbackInfo ci) {
        ServerLevel serverLevel = (ServerLevel) level;

        if (Util.isRaining(serverLevel) && Util.isRainingAtBiome(serverLevel, blockPos)) {
            boolean isOutside = Util.isOutside(
                    Util.checkRow(serverLevel, blockPos, 64),
                    Util.checkRow(serverLevel, blockPos, 8),
                    Util.checkRow(serverLevel, blockPos, 8),
                    Util.checkRow(serverLevel, blockPos, 8),
                    Util.checkRow(serverLevel, blockPos, 8)
            );

            if (isOutside) {
                CampfireBlock.dowse(null, level, blockPos, blockState);
                BlockState newState = blockState.setValue(CampfireBlock.LIT, false);
                level.setBlock(blockPos, newState, 11);
            }
        }
    }
}