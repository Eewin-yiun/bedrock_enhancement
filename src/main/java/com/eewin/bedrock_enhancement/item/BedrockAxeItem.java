package com.eewin.bedrock_enhancement.item;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;

// ========================================
// 基岩斧
// 下界合金斧 + 1基岩块 在锻造台升级获得
// 攻击伤害 = 下界合金1.5倍
// 防火：不会被岩浆/火焰烧毁
// ========================================

public class BedrockAxeItem extends AxeItem {

    public BedrockAxeItem() {
        super(BedrockTier.BEDROCK,
                10.0F,     // 攻击伤害：下界合金8 + 2 = 10
                -3.0F,     // 攻击速度：与下界合金相同
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
