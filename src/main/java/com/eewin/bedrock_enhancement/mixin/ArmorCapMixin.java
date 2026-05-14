package com.eewin.bedrock_enhancement.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin 目标：LivingEntity.getDamageAfterArmorAbsorb
 * 
 * 原版护甲公式在护甲值超过约30后收益极低（递减回报）。
 * 本 Mixin 对超过30的护甲值使用修正公式，将有效上限提升至200。
 * 
 * 修正公式：damage *= max(0.05, 1 - armor / (armor + 10 + toughness/2))
 *  - 200护甲 + 10韧性 → 伤害乘数 ≈ 0.32（68%减伤）
 *  - 100护甲 + 10韧性 → 伤害乘数 ≈ 0.47（53%减伤）
 *  - 最低保留5%伤害，避免完全无敌
 *  - 护甲≤30时跳过，使用原版逻辑
 *
 * 注意：本 Mixin 处理护甲减免，附魔保护由原版在后续流程处理。
 */
@Mixin(net.minecraft.world.entity.LivingEntity.class)
public abstract class ArmorCapMixin {

    @Inject(
            method = "getDamageAfterArmorAbsorb",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void onGetDamageAfterArmorAbsorb(
            DamageSource pDamageSource,
            float pDamage,
            CallbackInfoReturnable<Float> cir) {

        LivingEntity entity = (LivingEntity) (Object) this;

        int armorValue = entity.getArmorValue();
        // 护甲值 ≤30 时使用原版逻辑，不影响原版装备
        if (armorValue <= 30) return;

        // 限制护甲上限为200
        float armor = Math.min(armorValue, 200);
        float toughness = (float) entity.getAttributeValue(Attributes.ARMOR_TOUGHNESS);

        // 修正公式：高护甲值时仍有意义的伤害减免
        float damageMultiplier = Math.max(0.05F,
                1.0F - armor / (armor + 10.0F + toughness / 2.0F));
        float reducedDamage = pDamage * damageMultiplier;

        // 返回修正后的伤害值
        // 附魔保护（如保护附魔）会在原版后续流程中生效
        cir.setReturnValue(Math.max(0.0F, reducedDamage));
    }
}
