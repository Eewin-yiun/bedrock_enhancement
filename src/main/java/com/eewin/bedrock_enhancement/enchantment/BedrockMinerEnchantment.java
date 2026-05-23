package com.eewin.bedrock_enhancement.enchantment;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.EquipmentSlot;

/**
 * 基岩挖掘者 (Bedrock Miner) 附魔
 *
 * 效果：消耗大量耐久来破坏原版基岩，不掉落任何物品
 * 可附魔物品：仅钻石镐、下界合金镐（挖掘等级 3~4）
 * 钻石镐：最多挖 1 次（消耗全部耐久）
 * 下界合金镐：最多挖 2 次（每次消耗约 1/2 耐久）
 */
public class BedrockMinerEnchantment extends Enchantment {

    public BedrockMinerEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    /**
     * 只允许附魔在钻石镐或下界合金镐上
     */
    @Override
    public boolean canEnchant(ItemStack pStack) {
        // 用 Items 常量判断，SRG 映射下最可靠
        return pStack.is(Items.DIAMOND_PICKAXE) || pStack.is(Items.NETHERITE_PICKAXE);
    }

    /**
     * 允许在附魔台上附魔到钻石/下界合金镐
     */
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack pStack) {
        return pStack.is(Items.DIAMOND_PICKAXE) || pStack.is(Items.NETHERITE_PICKAXE);
    }

    /**
     * 最大附魔等级：1（只有 I 级）
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
        return 15;
    }

    /**
     * 附魔台最高经验等级
     */
    @Override
    public int getMaxCost(int pLevel) {
        return 50;
    }

    /**
     * 检查镐子是否是钻石级或以上（等级 3~4）
     * 运行时调用，供 Mixin/EventHandler 使用
     */
    public static boolean isDiamondOrBetter(ItemStack stack) {
        if (!(stack.getItem() instanceof TieredItem tieredItem)) return false;
        return tieredItem.getTier().getLevel() >= 3;
    }
}
