package com.eewin.bedrock_enhancement.command;

import com.eewin.bedrock_enhancement.BedrockEnhancement;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.registries.BuiltInRegistries;

// ========================================
// 管理员指令：查找最近的基岩残骸
// 用法：/findbedrockdebris [搜索半径]
// 权限等级：2（管理员）
// 功能：从执行者位置出发，在指定球形半径内
//        搜索最近的 bedrock_enhancement:bedrock_debris 方块
// ========================================

public class FindBedrockDebrisCommand {

    // 默认搜索半径（格）
    private static final int DEFAULT_RADIUS = 100;

    // 最大搜索半径（防止卡顿）
    private static final int MAX_RADIUS = 500;

    // 目标方块ID
    private static final ResourceLocation TARGET_BLOCK = new ResourceLocation(
            BedrockEnhancement.MOD_ID, "bedrock_debris");

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("findbedrockdebris")
                        .requires(source -> source.hasPermission(2))  // 需要管理员权限
                        .executes(context -> execute(context.getSource(), DEFAULT_RADIUS))
                        .then(Commands.argument("radius", IntegerArgumentType.integer(1, MAX_RADIUS))
                                .executes(context -> execute(
                                        context.getSource(),
                                        IntegerArgumentType.getInteger(context, "radius")
                                ))
                        )
        );
    }

    private static int execute(CommandSourceStack source, int radius) {
        // 只能由玩家执行（需要获取玩家位置）
        // 使用 getPlayer() 而非 getPlayerOrException()，避免 CommandSyntaxException
        if (source.getPlayer() == null) {
            source.sendFailure(Component.translatable("commands.findbedrockdebris.only_player"));
            return 0;
        }

        BlockPos center = BlockPos.containing(source.getPlayer().position());
        Level level = source.getLevel();

        source.sendSuccess(() ->
                Component.translatable("commands.findbedrockdebris.searching", radius), false);

        // 在球形范围内搜索基岩残骸
        final BlockPos[] foundPos = {null};
        final double[] minDistSq = {Double.MAX_VALUE};
        int r = radius;

        for (int dx = -r; dx <= r; dx++) {
            for (int dy = -r; dy <= r; dy++) {
                for (int dz = -r; dz <= r; dz++) {
                    // 球形裁剪，提升性能
                    if (dx * dx + dy * dy + dz * dz > r * r) {
                        continue;
                    }

                    BlockPos pos = center.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(pos);

                    // 跳过空气
                    if (state.is(Blocks.AIR) || state.is(Blocks.CAVE_AIR)) {
                        continue;
                    }

                    // 比对方块ID
                    ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
                    if (TARGET_BLOCK.equals(blockId)) {
                        double distSq = pos.distSqr(center);
                        if (distSq < minDistSq[0]) {
                            minDistSq[0] = distSq;
                            foundPos[0] = pos.immutable();
                        }
                    }
                }
            }
        }

        if (foundPos[0] == null) {
            source.sendSuccess(() ->
                    Component.translatable("commands.findbedrockdebris.not_found", radius), false);
            return 0;
        }

        // 将坐标提取为 final 局部变量，供 lambda 使用
        final int fx = foundPos[0].getX();
        final int fy = foundPos[0].getY();
        final int fz = foundPos[0].getZ();
        final String distStr = String.format("%.1f", Math.sqrt(minDistSq[0]));

        source.sendSuccess(() ->
                Component.translatable(
                        "commands.findbedrockdebris.found",
                        fx, fy, fz, distStr
                ), false);
        return 1;
    }
}
