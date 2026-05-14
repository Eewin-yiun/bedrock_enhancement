package com.eewin.bedrock_enhancement.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

// ========================================
// 基岩残骸方块
// 生成在主世界基岩层附近，极其稀有
// 需要下界合金镐及以上才能挖掘并掉落
// 基岩镐（等级5）也可以挖掘
// 硬度53.333：下界合金镐(速度8)挖掘约10秒
// 爆炸抗性1200
//
// 挖掘时间公式：ticks = hardness * 30 / digSpeed
// 下界合金镐(8): 53.333*30/8 = 200tick = 10秒
// 基岩镐(120): 53.333*30/120 = 13.3tick ≈ 0.67秒
//
// 挖掘等级通过数据包标签控制：
// - data/minecraft/tags/blocks/mineable/pickaxe.json（需镐）
// - data/minecraft/tags/blocks/needs_netherite_tool.json（需下界合金等级）
// ========================================

public class BedrockDebrisBlock extends Block {

    public BedrockDebrisBlock() {
        super(Properties.of()
                .mapColor(MapColor.COLOR_GRAY)
                .strength(53.333333F, 1200.0F)
                .requiresCorrectToolForDrops());
    }
}
