package com.eewin.bedrock_enhancement.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

/**
 * 基岩砂岩 - 属性类似原版砂岩，但硬度为砂岩的 1.5 倍
 * 材质为砂岩改色（基岩粉末颜色）
 * 配方：2×2 四个基岩沙
 */
public class BedrockSandstoneBlock extends Block {

    // 原版砂岩硬度为 0.8，1.5 倍为 1.2
    // 爆炸抗性保持基岩级别
    public BedrockSandstoneBlock() {
        super(Properties.of()
                .mapColor(MapColor.COLOR_GRAY)
                .strength(1.2F, 3600000.0F)
                .requiresCorrectToolForDrops()
                .sound(SoundType.STONE));
    }
}
