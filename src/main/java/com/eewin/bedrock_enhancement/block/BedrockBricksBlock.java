package com.eewin.bedrock_enhancement.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

/**
 * 基岩砖块 - 属性类似原版红砖块
 * 硬度为红砖块的1.5倍（原版2.0 → 3.0）
 * 防爆防火
 * 需挖掘等级3（钻石镐）以上的镐子挖掘才有掉落物
 * 挖掘速度和镐子等级有关（由数据包标签控制）
 */
public class BedrockBricksBlock extends Block {

    public BedrockBricksBlock() {
        super(Properties.of()
                .mapColor(MapColor.COLOR_GRAY)
                .strength(3.0F, 3600000.0F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE));
    }
}
