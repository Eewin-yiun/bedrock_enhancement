package com.eewin.bedrock_enhancement.event;

import com.eewin.bedrock_enhancement.BedrockEnhancement;
import com.eewin.bedrock_enhancement.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// =================================================================
// 基岩镐事件处理
//
// 功能：
// 1. 手持基岩镐挖掘原版基岩（minecraft:bedrock）时：
//    - BreakSpeed：设置合理的挖掘速度（原版基岩 hardness=-1，计算速度为0）
//    - BreakEvent：破坏成功后手动掉落基岩粉末 + 播放音效
// 2. 手持基岩镐挖掘模组基岩方块时：速度已由 getSpeed()=120 处理，无需额外干预
// =================================================================

@Mod.EventBusSubscriber(modid = BedrockEnhancement.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BedrockPickaxeEventHandler {

    // 基岩镐挖掘原版基岩时的速度（≈20秒挖掉，与 TrueBedrockBlock 一致）
    // BedrockBreakMixin 中 hardness=1600.0F，
    // digSpeed=120 时：120/1600/30 = 0.0025 → 400 ticks = 20秒
    private static final float BEDROCK_DIG_SPEED = 120.0F;

    /**
     * 修改挖掘速度事件（客户端 + 服务端均触发）
     * 原版基岩 hardness = -1，MC 计算出的 progressPerTick = 0
     * 通过 setNewSpeed 强制覆盖，让客户端显示挖掘动画
     */
    @SubscribeEvent
    public static void onBreakSpeed(net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event) {
        BlockState state = event.getState();
        // 只处理原版基岩（模组基岩方块 hardness=1600，由方块属性直接处理）
        if (state.getBlock() != Blocks.BEDROCK) {
            return;
        }

        Player player = event.getEntity();
        if (player == null) return;
        ItemStack mainHand = player.getMainHandItem();

        // 基岩镐 或 基岩其他工具
        if (isBedrockTool(mainHand)) {
            event.setNewSpeed(BEDROCK_DIG_SPEED);
        }
    }

    /**
     * 方块被破坏时触发（仅服务端）
     * 原版基岩没有 LootTable，必须手动掉落
     */
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) {
            return;
        }

        if (event.getState().getBlock() != Blocks.BEDROCK) {
            return;
        }

        if (!(event.getPlayer() instanceof ServerPlayer player)) {
            return;
        }

        if (isBedrockTool(player.getMainHandItem())) {
            BlockPos pos = event.getPos();

            // 播放破坏音效
            serverLevel.playSound(null, pos, SoundEvents.STONE_BREAK,
                    SoundSource.BLOCKS, 1.0F, 0.8F);

            // 手动掉落基岩粉末（原版基岩无 LootTable）
            ItemStack drop = new ItemStack(ModItems.BEDROCK_POWDER.get(), 1);
            net.minecraft.world.entity.item.ItemEntity itemEntity =
                    new net.minecraft.world.entity.item.ItemEntity(
                            serverLevel,
                            pos.getX() + 0.5,
                            pos.getY() + 0.5,
                            pos.getZ() + 0.5,
                            drop);
            serverLevel.addFreshEntity(itemEntity);
        }
    }

    // 判断手持物品是否为基岩工具（镐/斧/锹/锄/剑）
    private static boolean isBedrockTool(ItemStack stack) {
        return stack.is(ModItems.BEDROCK_PICKAXE.get())
                || stack.is(ModItems.BEDROCK_AXE.get())
                || stack.is(ModItems.BEDROCK_SHOVEL.get())
                || stack.is(ModItems.BEDROCK_HOE.get())
                || stack.is(ModItems.BEDROCK_SWORD.get());
    }
}
