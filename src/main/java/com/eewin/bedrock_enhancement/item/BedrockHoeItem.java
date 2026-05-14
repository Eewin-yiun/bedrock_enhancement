package com.eewin.bedrock_enhancement.item;

import net.minecraft.world.item.HoeItem;

// ========================================
// 基岩锄
// 下界合金锄 + 1基岩块 在锻造台升级获得
// 等级5，高于下界合金
// 防火：不会被岩浆/火焰烧毁
// ========================================

public class BedrockHoeItem extends HoeItem {

    public BedrockHoeItem() {
        super(BedrockTier.BEDROCK,
                0,         // 攻击伤害加成：锄一般为0
                -3.0F,     // 攻击速度：与钻石锄相同
                new net.minecraft.world.item.Item.Properties()
                        .durability(BedrockTier.BEDROCK.getUses())
                        .fireResistant());  // 防火：不会被岩浆/火焰烧毁
    }

    // 基岩工具防火
    @Override
    public boolean isFireResistant() {
        return true;
    }
}
