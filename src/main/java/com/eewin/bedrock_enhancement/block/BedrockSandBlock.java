package com.eewin.bedrock_enhancement.block;

import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.material.MapColor;

/**
 * 基岩沙 - 模仿原版沙子行为
 * 受重力影响，可用任何物品挖掘
 * 通过合成获得（基岩粉末 + 沙子）
 */
public class BedrockSandBlock extends FallingBlock {
    public BedrockSandBlock() {
        super(Properties.of()
                .mapColor(MapColor.COLOR_GRAY)
                .strength(0.5F)
                .sound(net.minecraft.world.level.block.SoundType.SAND));
    }
}
