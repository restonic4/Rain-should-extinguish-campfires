# Rain Should Extinguish Campfires: Config Guide

The config file is created automatically the first time you launch the game.
You can find it at: **`<your minecraft folder>/config/rsec.json`**

Open it with any text editor (Notepad works fine). After editing, **save the file and restart your world/server** for changes to take effect.

---

## How often does the mod check each campfire?

```json
"rainLookupMinTickRange": 120,
"rainLookupMaxTickRange": 1200
```

The mod doesn't check every single game tick, that would be wasteful. Instead, each campfire picks a random wait time between these two values before it checks whether it should be put out.

**What is a tick?** Minecraft runs 20 ticks per second. So the defaults mean each campfire waits somewhere between **6 and 60 seconds** between checks. [Learn about game ticks](https://minecraft.wiki/w/Tick)

> **Lower both values** → campfires go out faster once it starts raining.  
> **Raise both values** → campfires take longer to notice the rain.  
> Setting both to the same number makes the timing fixed instead of random.

---

## Should the biome matter?

```json
"requirePrecipitationBiome": true
```

In Minecraft, some biomes don't get rain, like deserts, savannas, and a few others. [See which biomes have precipitation](https://minecraft.wiki/w/Biome#Precipitation)

- **`true`** *(default)*:  campfires in dry biomes are **never** extinguished. Matches how rain actually works in the game.
- **`false`**: the mod ignores the biome entirely. Any storm, anywhere, can put out a campfire.

---

## How does the mod figure out if rain can reach a campfire?

```json
"detectionMode": "RAYCAST"
```

This controls the method used to decide whether a campfire is sheltered or exposed to the sky.

### `"RAYCAST"` *(default, recommended)*
The mod looks straight up from the campfire, block by block, all the way to the sky. If it hits anything solid on the way up, the campfire counts as sheltered.

This is the most accurate mode. A campfire inside a house stays lit. A campfire under a single slab stays lit. A campfire in the open gets wet.

### `"SKY_LIGHT"`
Instead of looking upward, the mod reads the [sky light level](https://minecraft.wiki/w/Light#Sky_light) at the campfire's position. Sky light is the brightness value Minecraft calculates for how much "sky" a block can see.

Sky light can "leak" through glass, fences, leaves, and other see-through blocks, so a campfire inside a glass greenhouse might still read as exposed even though it's clearly under a roof. Only use this mode if RAYCAST causes problems for you.

---

## Sky light threshold (only used in `SKY_LIGHT` mode)

```json
"skyLightThreshold": 15
```

*This setting does nothing if you're using `RAYCAST` mode.*

Sky light goes from 0 (fully dark/indoors) to 15 (wide open sky). [Sky light values explained](https://minecraft.wiki/w/Light#Sky_light)

- **`15`** *(default)*: only campfires with a fully open sky above them are at risk. The strictest setting.
- **Lower values** (e.g. `12`): campfires under partial cover can also be extinguished.

---

## How "sheltered" does a campfire need to be to stay safe?

These two settings work together. They control how much open sky needs to surround a campfire before it gets put out.

### Neighbour check radius

```json
"neighbourCheckRadius": 1
```

The mod doesn't just check directly above the campfire, it also checks the columns of blocks around it. This radius controls how far out those checks reach.

- **`1`** *(default)*: checks the 8 columns immediately surrounding the campfire (a 3×3 area). Good for most cases.
- **`2`**: checks a 5×5 area (24 surrounding columns). Useful if you want campfires to stay lit unless they're really out in the open.
- **`3`**: checks a 7×7 area (48 surrounding columns). Very lenient, only campfires in large clearings would be extinguished.

### Required open neighbors

```json
"requiredOpenNeighboursToExtinguish": 3
```

Out of all the surrounding columns checked (based on the radius above), how many need to be open sky before the campfire gets put out?

- **`0`**: only the column directly above the campfire needs to be open. A campfire right next to a wall can still be extinguished.
- **`3`** *(default)*: at least 3 surrounding columns must also be open. Campfires tucked in corners or against walls are protected.
- **Higher values**: the campfire needs to be increasingly in the open to get extinguished. With radius `1`, the max meaningful value is `8` (every surrounding column must be open).

> **Quick rule of thumb:**
> - Campfire keeps going out indoors? → raise `requiredOpenNeighboursToExtinguish` or lower `neighbourCheckRadius`.
> - Campfire never goes out even in the open? → lower `requiredOpenNeighboursToExtinguish` or switch to `detectionMode: RAYCAST`.

---

## Full default config for reference

```json
{
  "rainLookupMinTickRange": 120,
  "rainLookupMaxTickRange": 1200,
  "requirePrecipitationBiome": true,
  "detectionMode": "RAYCAST",
  "skyLightThreshold": 15,
  "requiredOpenNeighboursToExtinguish": 2,
  "neighbourCheckRadius": 3
}
```