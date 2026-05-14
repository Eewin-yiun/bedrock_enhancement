package com.eewin.bedrock_enhancement.registry;

import com.eewin.bedrock_enhancement.BedrockEnhancement;
import com.eewin.bedrock_enhancement.block.ModBedrockBlock;
import com.eewin.bedrock_enhancement.block.BedrockDebrisBlock;
import com.eewin.bedrock_enhancement.block.QuasiBedrockBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// ========================================
// 方块注册类
// 所有自定义方块都在这里注册
// ========================================

public class ModBlocks {

    // 创建方块延迟注册器
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, BedrockEnhancement.MOD_ID);

    // ========== 方块注册 ==========

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

    // 注册方法，在主类中调用
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
