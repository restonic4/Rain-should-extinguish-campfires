package com.restonic4.rsec.mixin;

import com.restonic4.rsec.Config;
import com.restonic4.rsec.RainShouldExtinguishCampfires;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.restonic4.rsec.ConditionsManager.*;

@Mixin(CampfireBlockEntity.class)
public class CampfireBlockEntityMixin {
    @Unique private int rsec$tickRainCountdown = -1;

    @Inject(method = "cookTick", at = @At("TAIL"))
    private static void rsec$rainTick(Level level, BlockPos blockPos, BlockState blockState, CampfireBlockEntity campfireBlockEntity, CallbackInfo ci) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        if (!blockState.getValue(CampfireBlock.LIT)) return;
        if (!isRainingOrThundering(serverLevel) || !canRainingAtPosition(serverLevel, blockPos)) return;

        CampfireBlockEntityMixin self = (CampfireBlockEntityMixin) (Object) campfireBlockEntity;

        if (self.rsec$tickRainCountdown < 0) {
            self.rsec$tickRainCountdown = getRandomInt(Config.INSTANCE.rainLookupMinTickRange, Config.INSTANCE.rainLookupMaxTickRange);
        }

        self.rsec$tickRainCountdown--;

        if (self.rsec$tickRainCountdown <= 0) {
            self.rsec$tickRainCountdown = getRandomInt(Config.INSTANCE.rainLookupMinTickRange, Config.INSTANCE.rainLookupMaxTickRange);

            if (isExposedToRain(serverLevel, blockPos)) {
                CampfireBlock.dowse(null, level, blockPos, blockState);
                BlockState newState = blockState.setValue(CampfireBlock.LIT, false);
                level.setBlock(blockPos, newState, 11);

                level.playSound(null, blockPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5f, 1.0f);
            }
        }

        self.rsec$tickRainCountdown--;
    }
}
