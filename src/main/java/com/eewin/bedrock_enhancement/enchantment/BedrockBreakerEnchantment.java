package com.eewin.bedrock_enhancement.enchantment;

import com.eewin.bedrock_enhancement.registry.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 * 基岩破坏者 (Bedrock Breaker) 附魔
 *
 * 效果：每等级在前一等级基础上，挖掘基岩的速度提升 50%
 *   I 级：×1.5
 *   II 级：×1.5 × 1.5 = ×2.25
 *
 * 可附魔物品：仅基岩镐
 * 获取方式：
 *   - 附魔台（I / II 均可；II 级出现概率极低，需高等级附魔台）
 *   - 村民交易：仅 I 级（32 绿宝石 + 书）
 */
public class BedrockBreakerEnchantment extends Enchantment {

    public BedrockBreakerEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.DIGGER, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    /**
     * 只允许附魔在基岩镐上
     */
    @Override
    public boolean canEnchant(ItemStack pStack) {
        return pStack.is(ModItems.BEDROCK_PICKAXE.get());
    }

    /**
     * 允许在附魔台上附魔到基岩镐
     */
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack pStack) {
        return pStack.is(ModItems.BEDROCK_PICKAXE.get());
    }

    /**
     * 最大附魔等级：II
     */
    @Override
    public int getMaxLevel() {
        return 2;
    }

    /**
     * 附魔台最低经验等级
     * I 级常见（15），II 级需要高等级（40）→ 出现概率极低
     */
    @Override
    public int getMinCost(int pLevel) {
        return pLevel == 1 ? 15 : 40;
    }

    /**
     * 附魔台最高经验等级
     */
    @Override
    public int getMaxCost(int pLevel) {
        return pLevel == 1 ? 40 : 80;
    }
}
