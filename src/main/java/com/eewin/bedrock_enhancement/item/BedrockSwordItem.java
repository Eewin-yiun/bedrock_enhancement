package com.eewin.bedrock_enhancement.item;

import net.minecraft.world.item.SwordItem;

// ========================================
// 基岩剑
// 下界合金剑 + 1基岩块 在锻造台升级获得
// 攻击伤害 = 下界合金1.5倍
// 防火：不会被岩浆/火焰烧毁
// ========================================

public class BedrockSwordItem extends SwordItem {

    public BedrockSwordItem() {
        super(BedrockTier.BEDROCK,
                10,         // 攻击伤害：下界合金8 + 2 = 10
                -2.4F,      // 攻击速度：与下界合金相同
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
