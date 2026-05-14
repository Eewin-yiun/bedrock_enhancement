package com.eewin.bedrock_enhancement.item;

import net.minecraft.world.item.ShovelItem;

// ========================================
// 基岩锹
// 下界合金锹 + 1基岩块 在锻造台升级获得
// 防火：不会被岩浆/火焰烧毁
// ========================================

public class BedrockShovelItem extends ShovelItem {

    public BedrockShovelItem() {
        super(BedrockTier.BEDROCK,
                6.5F,     // 攻击伤害：下界合金5.5 + 1 = 6.5
                -3.0F,    // 攻击速度：与下界合金相同
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
