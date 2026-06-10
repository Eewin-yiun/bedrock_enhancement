package com.eewin.bedrock_enhancement.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * 基岩苹果 - 吃下后获得强大的正面效果和负面效果
 * 配方：一圈基岩粉末，中间一个金苹果
 * 回复饱食度 15 格
 */
public class BedrockAppleItem extends Item {

    public BedrockAppleItem() {
        super(new Properties()
                .food(new net.minecraft.world.food.FoodProperties.Builder()
                        .nutrition(15)      // 饱食度 15 格
                        .saturationMod(1.2F) // 饱和度倍率
                        .alwaysEat()        // 即使不饿也能吃
                        .build())
                .rarity(net.minecraft.world.item.Rarity.EPIC)); // 史诗稀有度
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (!level.isClientSide && entity instanceof Player player) {
            // ===== 正面效果 =====
            // 伤害吸收 IV - 5 分钟 (6000 ticks)
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 6000, 3));
            // 生命恢复 II - 3 分钟 (3600 ticks)
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 3600, 1));
            // 抗火 - 5 分钟 (6000 ticks)
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0));
            // 抗性提升 - 10 分钟 (12000 ticks)
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 12000, 0));
            // 力量 - 2 分钟 (2400 ticks)
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2400, 0));
            // 速度提升 - 2.5 分钟 (3000 ticks)
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3000, 0));

            // ===== 负面效果 =====
            // 反胃 - 30 秒 (600 ticks)
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 600, 0));
            // 失明 - 30 秒 (600 ticks)
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 600, 0));
            // 饥饿 - 10 秒 (200 ticks)
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 0));
        }

        return result;
    }
}
