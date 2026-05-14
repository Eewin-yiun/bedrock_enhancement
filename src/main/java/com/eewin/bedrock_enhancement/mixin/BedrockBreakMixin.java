package com.eewin.bedrock_enhancement.mixin;

import com.eewin.bedrock_enhancement.registry.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
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
 *   在 HEAD 注入，若目标是基岩且玩家手持基岩镐，
 *   跳过原版 -1 检查，手动计算并返回正确的进度值。
 *
 * remap = false + refmap：
 *   我们用 official 映射编译，但 MC 运行时内部用 SRG 映射。
 *   remap = false 告诉 Mixin 注解处理器不要在编译时做映射。
 *   通过 refmap 文件，Mixin 运行时会自动从 official 名映射到 SRG 名。
 */
@Mixin(value = BlockBehaviour.BlockStateBase.class, remap = false)
public abstract class BedrockBreakMixin {

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

        if (!state.is(Blocks.BEDROCK)) return;
        if (player == null) return;
        if (!player.getMainHandItem().is(ModItems.BEDROCK_PICKAXE.get())) return;

        // 手动计算挖掘进度：
        // 公式：progressPerTick = player.getDigSpeed(state, pos) / hardness / 30.0F
        // 目标：20 秒 = 400 tick → 每 tick 累积 0.0025
        // 基岩镐 getDestroySpeed 返回 120.0F → player.getDigSpeed ≈ 120.0F
        // hardness = 1600.0F → 120.0 / 1600.0 / 30.0 = 0.0025 ✓
        // 注：TrueBedrockBlock 的 hardness 也设为 1600.0F，保持一致
        float hardness = 1600.0F;
        float digSpeed = player.getDigSpeed(state, pos);
        float progressPerTick = digSpeed / hardness / 30.0F;

        progressPerTick = Math.max(0.001F, Math.min(1.0F, progressPerTick));
        cir.setReturnValue(progressPerTick);
    }
}
