package com.eewin.bedrock_enhancement.mixin;

import com.eewin.bedrock_enhancement.enchantment.BedrockMinerEnchantment;
import com.eewin.bedrock_enhancement.registry.ModBlocks;
import com.eewin.bedrock_enhancement.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin 目标：BlockBehaviour$BlockStateBase#getDestroyProgress
 *
 * 原版问题：
 *   getDestroyProgress 内部调用 getDestroySpeed(level, pos)
 *   基岩的 destroyTime = -1 → getDestroySpeed 返回 -1.0F
 *   然后检查：if (f == -1.0F) return 0.0F;
 *   客户端拿到 0.0 → startDestroyBlock 返回 false → 不开始挖掘动画
 *
 * 解决方案：
 *   在 HEAD 注入，若目标是基岩/基岩半砖/基岩楼梯，且玩家手持合法工具，
 *   跳过原版 -1 检查，手动返回正确的进度值。
 *
 * remap = false + refmap：
 *   用 official 映射编译，Mixin 运行时会通过 refmap 自动映射到 SRG 名。
 */
@Mixin(value = BlockBehaviour.BlockStateBase.class, remap = false)
public abstract class BedrockBreakMixin {

    // 基岩硬度
    private static final float BEDROCK_HARDNESS = 1600.0F;

    @Inject(
            method = "getDestroyProgress",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void onGetDestroyProgress(
            Player player,
            net.minecraft.world.level.BlockGetter level,
            net.minecraft.core.BlockPos pos,
            CallbackInfoReturnable<Float> cir) {

        // self 即 BlockStateBase 实例，转型为 BlockState 使用
        BlockState state = (BlockState) (Object) this;

        // 只处理基岩、基岩半砖、基岩楼梯
        if (!isBedrockBaseBlock(state)) return;
        if (player == null) return;

        ItemStack mainHand = player.getMainHandItem();

        // 情况1：手持基岩工具（基岩20秒，半砖/楼梯10秒）
        if (isBedrockTool(mainHand)) {
            float digSpeed = player.getDigSpeed(state, pos);
            float hardness = isSlabOrStairs(state) ? BEDROCK_HARDNESS / 2 : BEDROCK_HARDNESS;
            float progressPerTick = digSpeed / hardness / 30.0F;
            progressPerTick = Math.max(0.001F, Math.min(1.0F, progressPerTick));
            cir.setReturnValue(progressPerTick);
            return;
        }

        // 情况2：手持带有基岩挖掘者附魔的镐子
        if (!mainHand.isEmpty() && mainHand.getItem() instanceof TieredItem tieredItem) {
            int tierLevel = tieredItem.getTier().getLevel();
            // 只处理钻石级（3）和下界合金级（4）
            if (tierLevel >= 3 && tierLevel <= 4) {
                // 验证附魔确实存在
                if (mainHand.getEnchantmentLevel(
                        ForgeRegistries.ENCHANTMENTS.getValue(
                                new ResourceLocation("bedrock_enhancement", "bedrock_miner"))) > 0) {
                    // 根据等级决定速度
                    float progressPerTick;
                    if (tierLevel >= 4) {
                        // 下界合金级：5秒
                        progressPerTick = 1.0F / (5.0F * 20.0F);
                    } else {
                        // 钻石级：10秒
                        progressPerTick = 1.0F / (10.0F * 20.0F);
                    }
                    progressPerTick = Math.max(0.001F, Math.min(1.0F, progressPerTick));
                    cir.setReturnValue(progressPerTick);
                }
            }
        }
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

    // 判断手持物品是否为基岩工具（镐/斧/锹/锄/剑）
    private static boolean isBedrockTool(ItemStack stack) {
        return stack.is(ModItems.BEDROCK_PICKAXE.get())
                || stack.is(ModItems.BEDROCK_AXE.get())
                || stack.is(ModItems.BEDROCK_SHOVEL.get())
                || stack.is(ModItems.BEDROCK_HOE.get())
                || stack.is(ModItems.BEDROCK_SWORD.get());
    }
}
