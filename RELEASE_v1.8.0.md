# bedrock_enhancement v1.8.0 Release Notes

## 中文说明

**新增功能 / New Features:**
- ✅ 添加基岩半砖（Bedrock Slab）| Added Bedrock Slab
- ✅ 添加基岩楼梯（Bedrock Stairs）| Added Bedrock Stairs
- ✅ 添加基岩玻璃（Bedrock Glass）| Added Bedrock Glass
- ✅ 添加基岩挖掘者附魔（Bedrock Miner）| Added Bedrock Miner enchantment
- ✅ 添加「防爆玻璃」成就 | Added "Blast-Proof Glass" advancement

**方块说明 / Block Details:**
- 基岩半砖：3 个原版基岩 → 6 个，防爆，仅基岩镐可挖掘 | Bedrock Slab: 3 vanilla bedrock → 6, blast-proof, only Bedrock Pickaxe can mine
- 基岩楼梯：6 个原版基岩 → 4 个，防爆，仅基岩镐可挖掘 | Bedrock Stairs: 6 vanilla bedrock → 4, blast-proof, only Bedrock Pickaxe can mine
- 基岩玻璃：基岩沙烧制获得，防爆（爆炸抗性 1200.0F）| Bedrock Glass: smelt Bedrock Sand, blast-proof (resistance 1200.0F)

**技术更新 / Technical Updates:**
- 新增 `forge:needs_bedrock_tool` 标签，配合 TierSortingRegistry | Added `forge:needs_bedrock_tool` tag for TierSortingRegistry
- 基岩半砖/楼梯重写 `canHarvestBlock()` 控制挖掘权限 | Bedrock Slab/Stairs override `canHarvestBlock()` for mining control
- 配方使用 `minecraft:bedrock`（原版基岩）| Recipes use `minecraft:bedrock` (vanilla bedrock)
- 材质复用原版基岩贴图 `minecraft:block/bedrock` | Textures use vanilla bedrock `minecraft:block/bedrock`
- 新增基岩挖掘者附魔（Bedrock Miner），可附魔在钻石镐/下界合金镐 | Added Bedrock Miner enchantment, applicable to diamond/netherite pickaxes
- 钻石镐附魔后 10 秒破坏基岩，消耗全部耐久 | Diamond pickaxe: 10s to break bedrock, consumes all durability
- 下界合金镐附魔后 5 秒破坏基岩，可破坏 2 次 | Netherite pickaxe: 5s to break bedrock, 2 uses
- 破坏基岩后不掉落任何物品 | No drops when breaking bedrock with this enchantment
- 支持通过附魔台获取 | Obtainable from enchanting table
- 基岩半砖/楼梯挖掘时间减半（更快挖掘）| Bedrock Slab/Stairs mining time halved (faster mining)

**兼容性 / Compatibility:**
- Minecraft Forge 1.20.1

---

## 安装说明 / Installation

1. 安装 Minecraft Forge 1.20.1
2. 将 `bedrock_enhancement-1.8.0.jar` 放入 `mods` 文件夹
3. 启动游戏

1. Install Minecraft Forge 1.20.1
2. Put `bedrock_enhancement-1.8.0.jar` into `mods` folder
3. Launch the game

---

## 源代码 / Source Code

GitHub: https://github.com/Eewin-yiun/bedrock_enhancement
