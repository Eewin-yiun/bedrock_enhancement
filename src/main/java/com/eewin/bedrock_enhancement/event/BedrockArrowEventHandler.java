package com.eewin.bedrock_enhancement.event;

import com.eewin.bedrock_enhancement.entity.BedrockArrowEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 基岩箭事件处理器
 *
 * 速度逻辑（直接检查射手手持物品，不依赖 NBT 标记）：
 * - 基岩箭 + 基岩弓：已在 BedrockBowItem 中设为 5x 速度
 *   → 事件处理器检查射手手持，是基岩弓则不减速 → 结果 5x ✅
 * - 基岩箭 + 原版弓：速度 1x
 *   → 事件处理器检查射手手持，不是基岩弓则减速 → 结果 0.2x ✅
 */
@Mod.EventBusSubscriber(modid = "bedrock_enhancement", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BedrockArrowEventHandler {

    @SubscribeEvent
    public static void onArrowJoinLevel(EntityJoinLevelEvent event) {
        // 只在服务端处理
        if (event.getLevel().isClientSide()) return;

        Entity entity = event.getEntity();
        if (!(entity instanceof BedrockArrowEntity arrow)) return;

        // 检查射手是否用基岩弓射出的（检查射手当前手持物品）
        boolean shotByBedrockBow = false;
        if (arrow.getOwner() instanceof LivingEntity shooter) {
            ItemStack mainHand = shooter.getMainHandItem();
            ItemStack offHand = shooter.getOffhandItem();
            // 检查射手手中是否有基岩弓
            if (mainHand.getItem() instanceof com.eewin.bedrock_enhancement.item.BedrockBowItem
                    || offHand.getItem() instanceof com.eewin.bedrock_enhancement.item.BedrockBowItem) {
                shotByBedrockBow = true;
            }
        }

        if (shotByBedrockBow) {
            // 基岩弓射出的基岩箭，已经是 5x 速度，不减速
            return;
        }

        // 原版弓射出的基岩箭：速度 × 0.2（1/5）
        Vec3 motion = arrow.getDeltaMovement();
        arrow.setDeltaMovement(motion.scale(0.2));
    }
}
