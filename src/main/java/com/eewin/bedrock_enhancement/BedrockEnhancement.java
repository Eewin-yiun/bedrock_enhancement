package com.eewin.bedrock_enhancement;

import com.eewin.bedrock_enhancement.item.BedrockTier;
import com.eewin.bedrock_enhancement.registry.ModBlocks;
import com.eewin.bedrock_enhancement.creative.ModCreativeTabs;
import com.eewin.bedrock_enhancement.registry.ModEnchants;
import com.eewin.bedrock_enhancement.registry.ModEntities;
import com.eewin.bedrock_enhancement.registry.ModItems;
import com.eewin.bedrock_enhancement.registry.ModSounds;
import com.eewin.bedrock_enhancement.command.FindBedrockDebrisCommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

// ========================================
// 基岩增强 - 模组主类
// 作者：Eewin
// 版本：1.4.0
// MC版本：1.20.1
// Forge版本：47.x
// ========================================

@Mod(BedrockEnhancement.MOD_ID)
public class BedrockEnhancement {

    // 模组ID，全局常量，所有注册都依赖这个值
    public static final String MOD_ID = "bedrock_enhancement";

    public BedrockEnhancement() {
        // 获取模组事件总线，用于注册方块、物品、创造栏等
        IEventBus modEventBus =
                net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext.get().getModEventBus();

        // 注册自定义声音事件（必须在物品注册之前，避免 RecordItem 引用不完整的 SoundEvent）
        ModSounds.register(modEventBus);

        // 注册物品（必须在监听器注册之前）
        ModItems.register(modEventBus);

        // 注册附魔
        ModEnchants.register(modEventBus);

        // 注册方块（必须在监听器注册之前）
        ModBlocks.register(modEventBus);

        // 注册创造模式物品栏
        ModCreativeTabs.register(modEventBus);

        // 注册实体
        ModEntities.register(modEventBus);

        // 在 FMLCommonSetupEvent 中注册 BedrockTier 到 TierSortingRegistry
        // 必须在此事件中注册，不能在 enum static{} 块里注册
        // 因为枚举类加载时序早于 Forge 注册系统就绪
        modEventBus.addListener(this::setup);

        // 注册到Forge事件总线（用于处理游戏事件）
        MinecraftForge.EVENT_BUS.register(this);
    }

    // 注册管理员指令
    @net.minecraftforge.eventbus.api.SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        FindBedrockDebrisCommand.register(event.getDispatcher());
    }

    private void setup(final FMLCommonSetupEvent event) {
        // 注册基岩工具等级到 Forge 的 TierSortingRegistry
        // 这样 requiresCorrectToolForDrops() 才能正确识别基岩镐和下界合金镐的挖掘等级
        event.enqueueWork(BedrockTier::registerTier);
    }
}
