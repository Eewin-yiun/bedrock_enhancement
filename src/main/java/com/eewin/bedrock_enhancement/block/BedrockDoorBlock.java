package com.eewin.bedrock_enhancement.block;

import com.eewin.bedrock_enhancement.enchantment.BedrockMinerEnchantment;
import com.eewin.bedrock_enhancement.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.resources.ResourceLocation;

/**
 * 基岩门 - 防火防爆，只能通过红石激活开关
 * 挖掘属性同基岩楼梯/半砖
 */
public class BedrockDoorBlock extends DoorBlock {

    public BedrockDoorBlock() {
        super(Properties.of()
                        .mapColor(MapColor.COLOR_GRAY)
                        .strength(-1.0F, 3600000.0F)
                        .requiresCorrectToolForDrops()
                        .sound(SoundType.STONE)
                        .noOcclusion(),
                BlockSetType.IRON); // 使用铁门的声音和交互类型（不能手动开关）
    }

    /**
     * 只有基岩工具 或 带有基岩挖掘者附魔的钻石/下界合金镐 才能挖掘
     */
    @Override
    public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
        ItemStack stack = player.getMainHandItem();
        // 情况1：基岩工具
        if (isBedrockTool(stack)) return true;
        // 情况2：带有基岩挖掘者附魔的镐子
        if (stack.getItem() instanceof TieredItem tieredItem) {
            int tierLevel = tieredItem.getTier().getLevel();
            if (tierLevel >= 3 && tierLevel <= 4) {
                var ench = ForgeRegistries.ENCHANTMENTS.getValue(
                        new ResourceLocation("bedrock_enhancement", "bedrock_miner"));
                if (ench != null && stack.getEnchantmentLevel(ench) > 0) {
                    return true;
                }
            }
        }
        return false;
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
