package com.eewin.bedrock_enhancement.item;

import com.eewin.bedrock_enhancement.entity.BedrockArrowEntity;
import com.eewin.bedrock_enhancement.registry.ModEntities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * 基岩箭 - 高伤害箭矢
 * 伤害为原版箭的10倍（getBaseDamage() 返回 20.0）
 *
 * 射程射速逻辑（由 BedrockBowItem + BedrockArrowEventHandler 配合实现）：
 * - 基岩箭 + 基岩弓：速度正常（1x）
 * - 基岩箭 + 原版弓：速度 ×0.2（1/5）
 */
public class BedrockArrowItem extends ArrowItem {

    public BedrockArrowItem() {
        super(new Properties().stacksTo(64).fireResistant());
    }

    @Override
    public Arrow createArrow(Level level, ItemStack stack, LivingEntity shooter) {
        // 用 EntityType 工厂创建，避免 Arrow(Level, LivingEntity) 硬编码 EntityType.ARROW
        BedrockArrowEntity arrow = ModEntities.BEDROCK_ARROW.get().create(level);
        arrow.setOwner(shooter);
        arrow.setPos(shooter.getX(), shooter.getEyeY() - 0.10000000149011612D, shooter.getZ());
        if (shooter instanceof Player) {
            arrow.pickup = AbstractArrow.Pickup.ALLOWED;
        }
        arrow.setBaseDamage(20.0);
        return arrow;
    }

    @Override
    public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.world.entity.player.Player player) {
        return false;
    }
}
