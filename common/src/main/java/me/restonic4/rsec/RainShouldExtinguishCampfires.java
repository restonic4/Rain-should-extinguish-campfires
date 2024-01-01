package me.restonic4.rsec;

import dev.architectury.event.events.common.TickEvent;
import me.restonic4.restapi.RestApi;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RainShouldExtinguishCampfires
{
	public static final String MOD_ID = "rsec";

	public static void init() {
		RestApi.Log("Did you know that rain should extinguish campfires?");

		POIManager.register();

		AtomicInteger tickCount = new AtomicInteger();

		TickEvent.SERVER_POST.register(
				(server) -> {
					ServerLevel level = server.overworld().getLevel();

					if (shouldCheck(level, tickCount)) {
						level.players().forEach(player -> {
							List<BlockPos> poi = getPOI(level, player);

							int modified = 0;
							int maxNumber = (poi.size() / 3 == 0) ? 1 : poi.size() / 3;
							int maxToModify = new Random().nextInt(maxNumber) + 1;

							for (BlockPos poiPos : poi) {
								if (isRainingAtBiome(level, poiPos)) {
									BlockState campfire = level.getBlockState(poiPos);

									boolean isOutside = isOutside(
											checkRow(level, poiPos, poiPos.getX(), poiPos.getZ(), 64),
											checkRow(level, poiPos, poiPos.getX() + 1, poiPos.getZ(), 8),
											checkRow(level, poiPos, poiPos.getX() - 1, poiPos.getZ(), 8),
											checkRow(level, poiPos, poiPos.getX(), poiPos.getZ() + 1, 8),
											checkRow(level, poiPos, poiPos.getX(), poiPos.getZ() - 1, 8)
									);

									if (campfire.getValue(CampfireBlock.LIT) && modified <= maxToModify && isOutside) {
										CampfireBlock.dowse(player, level, poiPos, campfire);
										BlockState newState = campfire.setValue(CampfireBlock.LIT, false);
										level.setBlock(poiPos, newState, 11);

										modified++;
									}
								}
							}
						});
					}
				}
		);

		RestApi.Log("Yeah, this mod fixes that");
	}

	public static boolean shouldCheck(ServerLevel level, AtomicInteger tickCount) {
		if (level.isRaining() || level.isThundering()) {
			tickCount.getAndIncrement();

			if (tickCount.get() >= 120) {
				tickCount.set(0);

				return true;
			}
		}

		return false;
	}

	public static boolean isRainingAtBiome(ServerLevel level, BlockPos pos) {
		Biome biome = level.getBiome(pos).value();

		RestApi.Log("Checking biome conditions");

        return biome.hasPrecipitation();
    }

	public static List<BlockPos> getPOI(ServerLevel level, ServerPlayer player) {
		PoiManager pointOfInterestManager = level.getPoiManager();
		BlockPos blockPos = player.getOnPos();

		Stream<BlockPos> stream = pointOfInterestManager.findAll(poiTypeHolder -> poiTypeHolder.is(POIManager.campfire.getKey()), blockPos1 -> true, blockPos, 128, PoiManager.Occupancy.ANY);

		List<BlockPos> blockPositions = stream.toList();

		RestApi.Log("Getting POI");

		for (BlockPos pos : blockPositions) {
			RestApi.Log(pos);
		}

		return blockPositions;
	}

	public static boolean checkRow(ServerLevel level, BlockPos poiPos, int x, int z, int maxY) {
		boolean isOutside = true;

		for (int i = 1; i < maxY; i++) {
			Block foundBlock = level.getBlockState(new BlockPos(x, poiPos.getY() + i, z)).getBlock();

			if (foundBlock != Blocks.AIR && foundBlock != Blocks.VOID_AIR && foundBlock != Blocks.CAVE_AIR) {
				isOutside = false;
			}
		}

		return isOutside;
	}

	public static boolean isOutside(boolean mainCheck, boolean check1, boolean check2, boolean check3, boolean check4) {
		boolean isOutside = false;

		RestApi.Log("Checking if it is outside");

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
}
