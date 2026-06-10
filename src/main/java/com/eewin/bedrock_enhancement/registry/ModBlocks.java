package com.eewin.bedrock_enhancement.registry;

import com.eewin.bedrock_enhancement.BedrockEnhancement;
import com.eewin.bedrock_enhancement.block.BedrockBricksBlock;
import com.eewin.bedrock_enhancement.block.BedrockClayBlock;
import com.eewin.bedrock_enhancement.block.BedrockDebrisBlock;
import com.eewin.bedrock_enhancement.block.BedrockDoorBlock;
import com.eewin.bedrock_enhancement.block.BedrockGlassBlock;
import com.eewin.bedrock_enhancement.block.BedrockSandstoneBlock;
import com.eewin.bedrock_enhancement.block.BedrockSlabBlock;
import com.eewin.bedrock_enhancement.block.BedrockStairBlock;
import com.eewin.bedrock_enhancement.block.ModBedrockBlock;
import com.eewin.bedrock_enhancement.block.QuasiBedrockBlock;
import com.eewin.bedrock_enhancement.block.BedrockSandBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// ======================================
// 方块注册类
// 所有自定义方块都在这里注册
// ======================================

public class ModBlocks {

    // 创建方块延迟注册器
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, BedrockEnhancement.MOD_ID);

    // ========= 方块注册 =========

    // 基岩残骸：生成在主世界基岩层附近，非常稀有
    // 需要下界合金镐及以上才能挖掘，挖掘掉落1个基岩碎渣
    public static final RegistryObject<Block> BEDROCK_DEBRIS =
            BLOCKS.register("bedrock_debris", BedrockDebrisBlock::new);

    // 基岩块：用9个基岩锭合成，不等于原版基岩
    // 需要下界合金镐及以上才能挖掘，掉落自身
    public static final RegistryObject<Block> BEDROCK_BLOCK =
            BLOCKS.register("bedrock_block", ModBedrockBlock::new);

    // 准基岩块：用9个基岩块合成
    // 需要下界合金镐及以上才能挖掘，烧制获得准基岩锭
    public static final RegistryObject<Block> QUASI_BEDROCK_BLOCK =
            BLOCKS.register("quasi_bedrock_block", QuasiBedrockBlock::new);

    // 基岩沙：行为类似沙子，受重力影响，可用任何物品挖掘
    // 只能通过合成获得（基岩粉末 + 沙子）
    public static final RegistryObject<Block> BEDROCK_SAND =
            BLOCKS.register("bedrock_sand", BedrockSandBlock::new);

    // 基岩玻璃：防爆（极高爆炸抗性），通过烧制基岩沙获得
    public static final RegistryObject<Block> BEDROCK_GLASS =
            BLOCKS.register("bedrock_glass", BedrockGlassBlock::new);

    // 基岩半砖：用基岩块合成，防爆，只能基岩镐挖掘
    public static final RegistryObject<Block> BEDROCK_SLAB =
            BLOCKS.register("bedrock_slab", BedrockSlabBlock::new);

    // 基岩楼梯：用基岩块合成，防爆，只能基岩镐挖掘
    public static final RegistryObject<Block> BEDROCK_STAIRS =
            BLOCKS.register("bedrock_stairs", BedrockStairBlock::new);

    // 基岩门：防火防爆，只能通过红石激活开关
    public static final RegistryObject<Block> BEDROCK_DOOR =
            BLOCKS.register("bedrock_door", BedrockDoorBlock::new);

    // 基岩砂岩：硬度为砂岩1.5倍，材质为砂岩改色
    public static final RegistryObject<Block> BEDROCK_SANDSTONE =
            BLOCKS.register("bedrock_sandstone", BedrockSandstoneBlock::new);

    // 磐基黏土：硬度为黏土块1.5倍，挖掘获得4个磐基黏土球
    public static final RegistryObject<Block> BEDROCK_CLAY =
            BLOCKS.register("bedrock_clay", BedrockClayBlock::new);

    // 基岩砖块：硬度为红砖块1.5倍，防爆防火，需钻石镐+挖掘才有掉落物
    public static final RegistryObject<Block> BEDROCK_BRICKS =
            BLOCKS.register("bedrock_bricks", BedrockBricksBlock::new);

    // 注册方法，在主类中调用
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
