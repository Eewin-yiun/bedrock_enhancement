package com.eewin.bedrock_enhancement.item;

import com.eewin.bedrock_enhancement.BedrockEnhancement;
import com.eewin.bedrock_enhancement.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

// =================================================================
// 基岩工具等级
// 等级5，高于下界合金（等级4）
// 耐久为下界合金的3倍（原1.5倍 → 现3倍 = 下界合金6093）
// 挖掘速度极高，除基岩相关方块外基本秒挖
// =================================================================

public enum BedrockTier implements Tier {

    BEDROCK;

    /**
     * 在 FMLCommonSetupEvent 中调用此方法完成注册
     * 确保注册在 Forge 系统就绪之后进行
     */
    public static void registerTier() {
        TierSortingRegistry.registerTier(
                BEDROCK,
                new ResourceLocation(BedrockEnhancement.MOD_ID, "bedrock"),
                List.of(Tiers.NETHERITE),  // 排在下界合金之后（更高等级）
                List.of()                   // 没有更高的等级在其后
        );
    }

    // 耐久度：下界合金2031 × 3 = 6093
    @Override
    public int getUses() {
        return 6093;
    }

    // 挖掘速度：足够高，除基岩相关方块外基本秒挖
    @Override
    public float getSpeed() {
        return 120.0F;
    }

    // 攻击伤害加成
    // 原8.0 → 现5.0：配合各工具基础值下调，实现总伤害"砍半但仍高于下界合金"
    @Override
    public float getAttackDamageBonus() {
        return 5.0F;
    }

    // 挖掘等级：5（高于下界合金的4）
    @Override
    public int getLevel() {
        return 5;
    }

    // 附魔价值
    @Override
    public int getEnchantmentValue() {
        return 18;
    }

    // 修复材料：使用基岩锭修复
    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(ModItems.BEDROCK_INGOT.get());
    }
}
