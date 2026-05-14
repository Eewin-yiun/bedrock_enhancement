package com.eewin.bedrock_enhancement.event;

import com.eewin.bedrock_enhancement.item.BedrockArmorItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.eewin.bedrock_enhancement.BedrockEnhancement;

// =================================================================
// 基岩盔甲事件处理器
// 全套穿戴基岩盔甲时：
//   1. 每 tick 给予防火效果（无限时长）
//   2. 免疫箭矢等弹射物伤害
//   3. 免疫爆炸伤害
// =================================================================

@Mod.EventBusSubscriber(modid = BedrockEnhancement.MOD_ID)
public class BedrockArmorEventHandler {

    // 每 tick 检测玩家是否穿戴全套基岩盔甲，给予防火效果
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;
        if (player.level().isClientSide()) return;

        boolean fullSet = isWearingFullSet(player);

        if (fullSet) {
            player.addEffect(new MobEffectInstance(
                    MobEffects.FIRE_RESISTANCE,
                    40,          // duration: 2 seconds
                    0,           // amplifier: level I
                    false,       // ambient: false
                    false,       // showParticles: false
                    true         // showIcon: true
            ));
        }
    }

    // 免疫箭矢和爆炸伤害
    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (!isWearingFullSet(player)) return;

        DamageSource source = event.getSource();

        // 免疫弹射物（箭矢、三叉戟、火焰弹、烟花等）
        if (source.is(DamageTypes.ARROW)
                || source.is(DamageTypes.TRIDENT)
                || source.is(DamageTypes.MOB_PROJECTILE)
                || source.is(DamageTypes.FIREWORKS)) {
            event.setCanceled(true);
            return;
        }

        // 免疫爆炸伤害（TNT、凋零、末影水晶、苦力怕等）
        if (source.is(DamageTypes.EXPLOSION)
                || source.is(DamageTypes.PLAYER_EXPLOSION)) {
            event.setCanceled(true);
        }
    }

    // 检测是否穿戴全套基岩盔甲
    private static boolean isWearingFullSet(Player player) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack leggings = player.getItemBySlot(EquipmentSlot.LEGS);
        ItemStack boots = player.getItemBySlot(EquipmentSlot.FEET);

        return !helmet.isEmpty()
                && !chestplate.isEmpty()
                && !leggings.isEmpty()
                && !boots.isEmpty()
                && helmet.getItem() instanceof BedrockArmorItem
                && chestplate.getItem() instanceof BedrockArmorItem
                && leggings.getItem() instanceof BedrockArmorItem
                && boots.getItem() instanceof BedrockArmorItem;
    }
}
