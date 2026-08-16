package com.eewin.bedrock_enhancement.item;

import com.eewin.bedrock_enhancement.util.BedrockEnchantmentFilter;
import net.minecraft.world.item.ItemStack;
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
                9,          // 基础攻击伤害：9 + 材质加成5 = 总14
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

    // 拒绝附魔书中的「坚不可摧」(光谱世界) 与「轻裾凛云」(非靴子) 等禁止附魔
    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        if (BedrockEnchantmentFilter.isForbiddenBook(stack, book)) {
            return false;
        }
        return super.isBookEnchantable(stack, book);
    }
}
