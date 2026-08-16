package com.eewin.bedrock_enhancement.item;

import com.eewin.bedrock_enhancement.util.BedrockEnchantmentFilter;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;

// ========================================
// 基岩锄
// 下界合金锄 + 1基岩块 在锻造台升级获得
// 等级5，高于下界合金
// 防火：不会被岩浆/火焰烧毁
// ========================================

public class BedrockHoeItem extends HoeItem {

    public BedrockHoeItem() {
        super(BedrockTier.BEDROCK,
                0,         // 基础攻击伤害加成：0 + 材质加成5 = 总5（> 下界合金4，砍半后仍更高）
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

    // 拒绝附魔书中的「坚不可摧」(光谱世界) 与「轻裾凛云」(非靴子) 等禁止附魔
    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        if (BedrockEnchantmentFilter.isForbiddenBook(stack, book)) {
            return false;
        }
        return super.isBookEnchantable(stack, book);
    }
}
