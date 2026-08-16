package com.eewin.bedrock_enhancement.item;

import com.eewin.bedrock_enhancement.util.BedrockEnchantmentFilter;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

// ========================================
// 基岩镐
// 下界合金镐 + 1基岩块 在锻造台升级获得
// 等级5，可以挖掘真基岩
// 挖掘速度参考钻石镐挖黑曜石
// 防火：不会被岩浆/火焰烧毁
// ========================================

public class BedrockPickaxeItem extends PickaxeItem {

    public BedrockPickaxeItem() {
        super(BedrockTier.BEDROCK,
                2,         // 基础攻击伤害：2 + 材质加成5 = 总7（> 下界合金5，砍半后仍更高）
                -2.8F,     // 攻击速度：与下界合金相同
                new net.minecraft.world.item.Item.Properties()
                        .durability(BedrockTier.BEDROCK.getUses())
                        .fireResistant());  // 防火：不会被岩浆/火焰烧毁
    }

    // 基岩工具防火：不会被岩浆或火焰烧毁
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

    /**
     * 重写 getDestroySpeed
     * 对原版基岩返回基岩镐速度（原版基岩不在 mineable_with_pickaxe 标签中）
     * 签名：(ItemStack stack, BlockState state)
     */
    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(Blocks.BEDROCK)) {
            return BedrockTier.BEDROCK.getSpeed();
        }
        return super.getDestroySpeed(stack, state);
    }
}
