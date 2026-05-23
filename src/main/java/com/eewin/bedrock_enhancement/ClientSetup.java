package com.eewin.bedrock_enhancement;

import com.eewin.bedrock_enhancement.registry.ModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * 客户端设置类 - 处理需要仅在客户端运行的初始化逻辑
 * 例如：设置透明方块的渲染层
 */
@Mod.EventBusSubscriber(modid = BedrockEnhancement.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // 设置基岩玻璃为透明渲染层（方块 + 物品）
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BEDROCK_GLASS.get(), RenderType.translucent());

        // 调试输出
        System.out.println("[BedrockEnhancement] ClientSetup: 已设置基岩玻璃为透明渲染层");
    }
}
