package com.eewin.bedrock_enhancement.event;

import com.eewin.bedrock_enhancement.BedrockEnhancement;
import com.eewin.bedrock_enhancement.enchantment.BedrockMinerEnchantment;
import com.eewin.bedrock_enhancement.registry.ModBlocks;
import com.eewin.bedrock_enhancement.registry.ModEnchants;
import com.eewin.bedrock_enhancement.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

// =================================================================
// 基岩镐事件处理
//
// 功能：
// 1. 手持基岩镐挖掘原版基岩/基岩半砖/基岩楼梯时：
//    - BreakSpeed：设置合理的挖掘速度
//    - BreakEvent：破坏成功后手动处理掉落/音效
// 2. 手持带有基岩挖掘者附魔的镐子挖掘基岩系方块时：
//    - BreakSpeed：设置对应挖掘速度
//    - BreakEvent：消耗耐久，不掉落物品
// =================================================================

@Mod.EventBusSubscriber(modid = BedrockEnhancement.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BedrockPickaxeEventHandler {

    // 基岩镐挖掘基岩系方块时的速度（≈20秒）
    private static final float BEDROCK_DIG_SPEED = 120.0F;

    // 基岩挖掘者附魔：钻石镐挖掘速度（≈10秒）
    private static final float BEDROCK_MINER_DIAMOND_SPEED = 240.0F;

    // 基岩挖掘者附魔：下界合金镐挖掘速度（≈5秒）
    private static final float BEDROCK_MINER_NETHERITE_SPEED = 480.0F;

    // 延迟获取附魔，避免注册时序问题
    private static Enchantment getBedrockMiner() {
        return ForgeRegistries.ENCHANTMENTS.getValue(
                new ResourceLocation(BedrockEnhancement.MOD_ID, "bedrock_miner"));
    }

    // 检查物品是否带有基岩挖掘者附魔
    private static boolean hasBedrockMiner(ItemStack stack) {
        Enchantment ench = getBedrockMiner();
        if (ench == null) return false;
        return stack.getEnchantmentLevel(ench) > 0;
    }

    // 获取基岩挖掘者附魔可挖掘基岩的次数上限
    // 钻石级（tier 3）：1 次；下界合金级（tier 4）：2 次
    private static int getMaxBedrockUses(ItemStack stack) {
        if (!(stack.getItem() instanceof TieredItem tieredItem)) return 0;
        int tierLevel = tieredItem.getTier().getLevel();
        if (tierLevel >= 4) return 2;
        if (tierLevel >= 3) return 1;
        return 0;
    }

    // 判断是否是基岩系方块（基岩、基岩半砖、基岩楼梯）
    private static boolean isBedrockBaseBlock(BlockState state) {
        if (state.is(Blocks.BEDROCK)) return true;
        var block = state.getBlock();
        return block == ModBlocks.BEDROCK_SLAB.get()
                || block == ModBlocks.BEDROCK_STAIRS.get();
    }

    // 判断是否是半砖或楼梯（挖掘时间减半）
    private static boolean isSlabOrStairs(BlockState state) {
        var block = state.getBlock();
        return block == ModBlocks.BEDROCK_SLAB.get()
                || block == ModBlocks.BEDROCK_STAIRS.get();
    }

    /**
     * 修改挖掘速度事件（客户端 + 服
     */
    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        BlockState state = event.getState();
        if (!isBedrockBaseBlock(state)) return;

        Player player = event.getEntity();
        if (player == null) return;
        ItemStack mainHand = player.getMainHandItem();

        // 情况1：基岩工具（基岩20秒，半砖/楼梯10秒）
        if (isBedrockTool(mainHand)) {
            float speed = isSlabOrStairs(event.getState()) ? BEDROCK_DIG_SPEED * 2 : BEDROCK_DIG_SPEED;
            // 基岩破坏者附魔：每等级在前一级基础上挖掘基岩速度 ×1.5（仅基岩镐）
            int breakerLevel = mainHand.getEnchantmentLevel(ModEnchants.BEDROCK_BREAKER.get());
            if (breakerLevel > 0) {
                speed *= (float) Math.pow(1.5, breakerLevel);
            }
            event.setNewSpeed(speed);
            return;
        }

        // 情况2：基岩挖掘者附魔
        if (hasBedrockMiner(mainHand)) {
            if (mainHand.getItem() instanceof TieredItem tieredItem) {
                int tierLevel = tieredItem.getTier().getLevel();
                float baseSpeed = (tierLevel >= 4) ? BEDROCK_MINER_NETHERITE_SPEED : BEDROCK_MINER_DIAMOND_SPEED;
                // 半砖/楼梯挖掘时间减半（速度翻倍）
                if (isSlabOrStairs(event.getState())) baseSpeed *= 2;
                event.setNewSpeed(baseSpeed);
            }
        }
    }

    /**
     * 方块被破坏时触发（仅服务端）
     */
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        if (!isBedrockBaseBlock(event.getState())) return;
        if (!(event.getPlayer() instanceof ServerPlayer player)) return;

        ItemStack mainHand = player.getMainHandItem();

        // 情况1：基岩工具（原有逻辑）
        if (isBedrockTool(mainHand)) {
            BlockPos pos = event.getPos();
            serverLevel.playSound(null, pos, SoundEvents.STONE_BREAK,
                    SoundSource.BLOCKS, 1.0F, 0.8F);
            // 手动掉落基岩粉末
            net.minecraft.world.entity.item.ItemEntity drop = new net.minecraft.world.entity.item.ItemEntity(
                    serverLevel,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    new ItemStack(ModItems.BEDROCK_POWDER.get(), 1));
            serverLevel.addFreshEntity(drop);
            return;
        }

        // 情况2：基岩挖掘者附魔
        if (hasBedrockMiner(mainHand)) {
            BlockPos pos = event.getPos();
            int maxUses = getMaxBedrockUses(mainHand);
            if (maxUses <= 0) return;

            int cost = mainHand.getMaxDamage() / maxUses;
            if (cost <= 0) cost = 1;

            // 消耗耐久（天然兼容耐久附魔）
            mainHand.hurtAndBreak(cost, player, (p) ->
                    p.broadcastBreakEvent(net.minecraft.world.InteractionHand.MAIN_HAND));

            // 取消原版掉落逻辑
            event.setCanceled(true);

            // 手动将方块设为空气
            serverLevel.setBlock(pos, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);

            // 播放破坏音效
            serverLevel.playSound(null, pos, SoundEvents.STONE_BREAK,
                    SoundSource.BLOCKS, 1.0F, 0.8F);
        }
    }

    // 判断手持物品是否为基岩工具
    private static boolean isBedrockTool(ItemStack stack) {
        return stack.is(ModItems.BEDROCK_PICKAXE.get())
                || stack.is(ModItems.BEDROCK_AXE.get())
                || stack.is(ModItems.BEDROCK_SHOVEL.get())
                || stack.is(ModItems.BEDROCK_HOE.get())
                || stack.is(ModItems.BEDROCK_SWORD.get());
    }
}
