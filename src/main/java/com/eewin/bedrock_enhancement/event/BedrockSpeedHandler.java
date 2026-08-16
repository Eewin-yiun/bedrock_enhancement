package com.eewin.bedrock_enhancement.event;

import com.eewin.bedrock_enhancement.BedrockEnhancement;
import com.eewin.bedrock_enhancement.item.BedrockArmorItem;
import com.eewin.bedrock_enhancement.item.BedrockTier;
import com.eewin.bedrock_enhancement.registry.ModEnchants;
import com.eewin.bedrock_enhancement.registry.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

// =================================================================
// 基岩装备速度处理
//   1. 每穿一件基岩护甲 → 减速 5%（可累加，最多 4 件 = 20%）
//   2. 手持任何基岩工具/武器 → 再减速 5%
//   3. 靴子附魔「轻裾凛云」→ 移速 +10%
// 三者叠加：最终速度倍率 = (1 - 0.05 × 减速层数) × (1.10 若有凛云)
// =================================================================

@Mod.EventBusSubscriber(modid = BedrockEnhancement.MOD_ID)
public class BedrockSpeedHandler {

    // 固定 UUID，用于精确移除速度修改器
    private static final UUID SPEED_MODIFIER_UUID =
            UUID.fromString("7e1f3a2b-5c4d-4e8f-9a1b-2c3d4e5f6a7b");

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;
        if (player.level().isClientSide()) return;

        // 1. 统计基岩护甲件数（头盔/胸甲/护腿/靴子）
        int armorCount = 0;
        for (EquipmentSlot slot : new EquipmentSlot[]{
                EquipmentSlot.HEAD, EquipmentSlot.CHEST,
                EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            if (player.getItemBySlot(slot).getItem() instanceof BedrockArmorItem) {
                armorCount++;
            }
        }

        // 2. 统计手持基岩工具/武器层数（主手 1 层 + 副手 1 层，可累加）
        int handStacks = 0;
        if (isBedrockToolOrWeapon(player.getMainHandItem())) handStacks++;
        if (isBedrockToolOrWeapon(player.getOffhandItem())) handStacks++;

        // 减速层数 = 护甲件数 + 手持工具层数
        int slowStacks = armorCount + handStacks;

        // 3. 靴子是否附魔「轻裾凛云」→ +10% 移速
        boolean hasCloudStride = player.getItemBySlot(EquipmentSlot.FEET)
                .getEnchantmentLevel(ModEnchants.CLOUD_STRIDE.get()) > 0;

        // 最终速度倍率
        double speedMult = 1.0 - 0.05 * slowStacks;
        if (hasCloudStride) speedMult *= 1.10;

        AttributeInstance attr = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attr == null) return;

        attr.removeModifier(SPEED_MODIFIER_UUID);
        if (Math.abs(speedMult - 1.0) > 1e-6) {
            attr.addTransientModifier(new AttributeModifier(
                    SPEED_MODIFIER_UUID,
                    "bedrock_speed_modifier",
                    speedMult - 1.0,
                    AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
    }

    // 判断是否为基岩工具/武器（镐/斧/锹/剑/锄用 BedrockTier 识别，弓单独判断）
    private static boolean isBedrockToolOrWeapon(ItemStack stack) {
        if (stack.getItem() instanceof TieredItem tiered) {
            return tiered.getTier() == BedrockTier.BEDROCK;
        }
        return stack.is(ModItems.BEDROCK_BOW.get());
    }
}
