package com.eewin.bedrock_enhancement;

import com.eewin.bedrock_enhancement.client.renderer.entity.BedrockArrowRenderer;
import com.eewin.bedrock_enhancement.registry.ModBlocks;
import com.eewin.bedrock_enhancement.registry.ModEntities;
import com.eewin.bedrock_enhancement.registry.ModItems;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
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

        // 设置基岩门为 cutout 渲染层（否则透明区域不透明）
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.BEDROCK_DOOR.get(), RenderType.cutout());

        // 注册基岩弓的拉弓动画属性（pull / pulling）
        ItemProperties.register(ModItems.BEDROCK_BOW.get(),
                new ResourceLocation("pull"),
                (stack, level, entity, seed) -> {
                    if (entity == null) return 0.0F;
                    return entity.getUseItem() != stack ? 0.0F
                            : (float) (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
                });
        ItemProperties.register(ModItems.BEDROCK_BOW.get(),
                new ResourceLocation("pulling"),
                (stack, level, entity, seed) -> {
                    if (entity == null) return 0.0F;
                    return entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
                });

        // 注册基岩箭实体渲染器
        EntityRenderers.register(ModEntities.BEDROCK_ARROW.get(), BedrockArrowRenderer::new);

        // 调试输出
        System.out.println("[BedrockEnhancement] ClientSetup: 已设置基岩玻璃为透明渲染层，基岩门为 cutout 渲染层，基岩弓动画属性已注册，基岩箭渲染器已注册");
    }
}
