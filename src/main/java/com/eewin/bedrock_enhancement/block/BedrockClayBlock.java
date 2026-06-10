package com.eewin.bedrock_enhancement.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

/**
 * 磐基黏土 - 属性类似原版黏土块，硬度为黏土块的1.5倍
 * 原版黏土块硬度为0.6，1.5倍约为0.9
 * 挖掘获得4个磐基黏土球（通过战利品表实现）
 * 用任何工具破坏都可以获得掉落物
 */
public class BedrockClayBlock extends Block {

    public BedrockClayBlock() {
        super(Properties.of()
                .mapColor(MapColor.COLOR_GRAY)
                .strength(0.9F, 3600000.0F)
                .sound(SoundType.GRAVEL));
    }
}
