package com.eewin.bedrock_enhancement.item;

import com.eewin.bedrock_enhancement.registry.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

// =======================================
// 基岩盔甲材质
// 防御值为下界合金的3倍
// 穿戴全套免疫火焰伤害和击退
// =======================================

public enum BedrockArmorMaterial implements ArmorMaterial {

    BEDROCK("bedrock",
            60,         // 耐久度乘数（下界合金37，基岩×5 ≈ 60→ 实际耐久见getDurabilityForType）
            new int[]{15, 40, 30, 15}, // 防御值 [头盔, 胸甲, 护腿, 靴子] = 下界合金 ×5
            30,         // 附魔价值（下界合金25，基岩更高）
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            10.0F,      // 韧性（下界合金3.0 × 3+）
            1.0F,       // 击退抗性（1.0 = 完全免疫击退）
            () -> Ingredient.of(ModItems.BEDROCK_INGOT.get()));

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionFunctionForType;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    // 构造方法
    BedrockArmorMaterial(String name, int durabilityMultiplier,
                         int[] protectionFunctionForType, int enchantmentValue,
                         SoundEvent equipSound, float toughness,
                         float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionFunctionForType = protectionFunctionForType;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        // 下界合金基础耐久：头盔407, 胸甲592, 护腿555, 靴子481
        // 基岩 ×5：
        int base = switch (type) {
            case BOOTS -> 481 * 5;       // 2405
            case LEGGINGS -> 555 * 5;     // 2775
            case CHESTPLATE -> 592 * 5;   // 2960
            case HELMET -> 407 * 5;       // 2035
            default -> 0;
        };
        return base;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        // 下界合金：头盔3, 胸甲8, 护腿6, 靴子3
        // 基岩 ×5：
        return switch (type) {
            case BOOTS -> 15;      // 3 × 5
            case LEGGINGS -> 30;   // 6 × 5
            case CHESTPLATE -> 40; // 8 × 5
            case HELMET -> 15;     // 3 × 5
            default -> 0;
        };
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
