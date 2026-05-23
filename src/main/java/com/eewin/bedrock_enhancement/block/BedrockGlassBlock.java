package com.eewin.bedrock_enhancement.block;

import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.material.MapColor;

/**
 * 基岩玻璃 - 防爆（极高爆炸抗性）
 * 可通过烧制基岩沙获得
 */
public class BedrockGlassBlock extends GlassBlock {
    public BedrockGlassBlock() {
        super(Properties.of()
                .mapColor(MapColor.COLOR_GRAY)
                .strength(0.3F)  // 与普通玻璃相同的硬度
                .explosionResistance(1200.0F)  // 极高爆炸抗性（原版玻璃是0.3）
                .sound(net.minecraft.world.level.block.SoundType.GLASS)
                .noOcclusion());  // 透明方块需要
    }
}
