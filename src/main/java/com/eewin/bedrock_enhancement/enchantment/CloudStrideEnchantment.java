package com.eewin.bedrock_enhancement.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 * 轻裾凛云 (Cloud Stride) 附魔
 *
 * 效果：附魔后移速增加 10%
 *
 * 可附魔物品：任何靴子
 * 获取方式：
 *   - 附魔台
 *   - 村民交易（64 绿宝石 + 书）
 */
public class CloudStrideEnchantment extends Enchantment {

    public CloudStrideEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    /**
     * 只允许附魔在任何靴子上
     */
    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.getItem() instanceof ArmorItem armor
                && armor.getType() == ArmorItem.Type.BOOTS;
    }

    /**
     * 允许在附魔台上附魔到任何靴子
     */
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack pStack) {
        return pStack.getItem() instanceof ArmorItem armor
                && armor.getType() == ArmorItem.Type.BOOTS;
    }

    /**
     * 最大附魔等级：I（单级）
     */
    @Override
    public int getMaxLevel() {
        return 1;
    }

    /**
     * 附魔台最低经验等级
     */
    @Override
    public int getMinCost(int pLevel) {
        return 20;
    }

    /**
     * 附魔台最高经验等级
     */
    @Override
    public int getMaxCost(int pLevel) {
        return 50;
    }
}
