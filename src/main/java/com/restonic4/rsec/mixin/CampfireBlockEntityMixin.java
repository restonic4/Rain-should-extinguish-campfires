package com.restonic4.rsec.mixin;

import com.restonic4.rsec.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
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
    private static int tickCountdown = 0;

    @Inject(method = "cookTick", at = @At("TAIL"))
    private static void cookTick(ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, CampfireBlockEntity campfireBlockEntity, RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> cachedCheck, CallbackInfo ci) {
        if (Util.isRaining(serverLevel) && Util.canRainingAtBiome(serverLevel, blockPos)) {
            if (tickCountdown <= 0) {
                tickCountdown = Util.getRandomInt(Util.getTicksForSeconds(2), Util.getTicksForSeconds(6));

                boolean isOutside = Util.isOutside(
                        Util.checkRow(serverLevel, blockPos, 64),
                        Util.checkRow(serverLevel, blockPos.offset(1, 0, 0), 8),
                        Util.checkRow(serverLevel, blockPos.offset(-1, 0, 0), 8),
                        Util.checkRow(serverLevel, blockPos.offset(0, 0, 1), 8),
                        Util.checkRow(serverLevel, blockPos.offset(0, 0, -1), 8)
                );

                if (isOutside) {
                    CampfireBlock.dowse(null, serverLevel, blockPos, blockState);
                    BlockState newState = blockState.setValue(CampfireBlock.LIT, false);
                    serverLevel.setBlock(blockPos, newState, 11);
                }
            }

            tickCountdown--;
        }
    }
}
