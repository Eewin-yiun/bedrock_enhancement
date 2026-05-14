package com.eewin.bedrock_enhancement.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;

// ========================================
// 基岩块方块
// 用9个基岩锭合成
// 通过工具标签控制挖掘等级（需下界合金镐）
// ========================================

public class ModBedrockBlock extends Block {

    public ModBedrockBlock() {
        super(Properties.of()
                .mapColor(MapColor.COLOR_GRAY)
                // 硬度53.333：下界合金镐(速度8)挖掘约10秒
                .strength(53.333333F, 3600000.0F)
                .requiresCorrectToolForDrops());
    }
}
