# Bedrock Enhancement v1.9.0 - Release Notes

## New Features

### Bedrock Apple
- Added Bedrock Apple - a powerful consumable item with both positive and negative effects
- Positive effects: Absorption IV (5min), Regeneration II (3min), Fire Resistance (5min), Resistance (10min), Strength (2min), Speed (2.5min)
- Negative effects: Nausea (30s), Blindness (30s), Hunger (10s)
- Restores 15 hunger points
- Recipe: 8× Vanilla Bedrock surrounding a Golden Apple (center)
- Hand-drawn texture by Eewin

### Bedrock Door
- Added Bedrock Door - blast-proof door that can only be opened/closed by redstone signal
- Properties: destroyTime=-1.0F, blast resistance 3600000.0F, fire-proof
- Can only be mined with Bedrock tools or pickaxes with Bedrock Miner enchantment
- Recipe: 6× Bedrock → 3× Bedrock Door (first two columns)
- Hand-drawn texture by Eewin (iron door style)

### Bedrock Sandstone
- Added Bedrock Sandstone - hardness is 1.5× vanilla Sandstone, blast-proof
- Recipe: 2×2 Bedrock Sand → 1 Bedrock Sandstone
- Texture: sandstone pattern + bedrock sand color (#8A8A8A)
- Hand-drawn texture by Eewin

### Bedrock Clay / Bedrock Clay Ball
- Added Bedrock Clay Block - hardness is 1.5× vanilla Clay Block, can be mined with any tool (drops 4 Bedrock Clay Balls)
- Added Bedrock Clay Ball - similar to vanilla Clay Ball, obtained by mining Bedrock Clay
- Recipes:
  - Bedrock Clay: [P C P][C P C][P C P] (P=Bedrock Powder, C=Clay Ball) → 1× Bedrock Clay
  - Bedrock Clay (from balls): 2×2 Bedrock Clay Balls → 1× Bedrock Clay
- Loot table: Mining Bedrock Clay drops 4× Bedrock Clay Balls
- Tool: Any tool (shovel recommended, added to minecraft:shovel tag)
- Hand-drawn textures by Eewin (clay.png → bedrock_clay.png, clay_ball.png → bedrock_clay_ball.png)

## Technical Updates

### Door Block Model Fix
- Fixed Bedrock Door rendering issue (purple/black missing texture)
- Root cause: Used wrong parent model name and texture variable names
- Correct parent: `minecraft:block/iron_door_bottom_left` (not `iron_door_bottom`)
- Correct texture variables: `"bottom"` and `"top"` (not `"door"`)
- Added 8 correct blockstate variants (bottom_left, bottom_left_open, bottom_right, bottom_right_open, top_left, top_left_open, top_right, top_right_open)

### Door Transparency Fix
- Added `RenderType.cutout()` for Bedrock Door in `ClientSetup.java`
- Previously transparent parts rendered as opaque (fixed)

### Clay Block Tool Type
- Added `data/minecraft/tags/blocks/mineable/shovel.json` to register Bedrock Clay as shovel-mineable
- Removed `requiresCorrectToolForDrops()` - any tool (including hand) can obtain drops

### Language File Fix
- Added missing `block.bedrock_enhancement.bedrock_clay` translation key (was causing "displaying ID only" issue in Creative inventory)

## Recipe Summary

| Item | Recipe |
|---|---|
| Bedrock Apple | [B][B][B] [B][Golden Apple][B] [B][B][B] (B=Vanilla Bedrock) |
| Bedrock Door | [B][B][ ] [B][B][ ] (B=Bedrock, 6 total) → 3× Door |
| Bedrock Sandstone | 2×2 Bedrock Sand → 1 |
| Bedrock Clay | [P][C][P] [C][P][C] [P][C][P] (P=Powder, C=Clay Ball) → 1 |
| Bedrock Clay (from balls) | 2×2 Clay Balls → 1 |
| Bedrock Clay Ball | Obtained by mining Bedrock Clay (drops 4) |

## Compatibility

- Minecraft Forge 1.20.1
- Tested with Forge 47.2.0+
- Compatible with Curios API, GeckoLib, Cloth Config, JEI (see full compatibility list in MEMORY.md)

## Installation

1. Install Minecraft Forge 1.20.1 (47.2.0+)
2. Download `bedrock_enhancement-1.9.0.jar`
3. Place in `mods/` folder
4. Launch game

## Full Changelog

- Added: Bedrock Apple (food with positive/negative effects)
- Added: Bedrock Door (blast-proof, redstone-activated only)
- Added: Bedrock Sandstone (hard sandstone variant)
- Added: Bedrock Clay + Bedrock Clay Ball (clay variant)
- Fixed: Bedrock Door purple/black missing texture (model parent + texture variable fix)
- Fixed: Bedrock Door transparent parts rendering opaque (added cutout render layer)
- Fixed: Bedrock Clay missing translation in Creative inventory (added block.* key)
- Fixed: Bedrock Clay requires correct tool (now any tool drops loot)
- Updated: Creative tab ordering (added new items)
- Updated: Lang files (en_us.json, zh_cn.json)
