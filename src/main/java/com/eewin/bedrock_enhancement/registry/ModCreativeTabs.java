package com.eewin.bedrock_enhancement.registry;

import com.eewin.bedrock_enhancement.BedrockEnhancement;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

// =====================================
// 创造模式标签注册类
// 控制物品在创造模式菜单中的显示
// =====================================

public class ModCreativeTabs {
    // 创建创造标签延迟注册器
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BedrockEnhancement.MOD_ID);

    // 基岩增强主标签
    public static final RegistryObject<CreativeModeTab> BEDROCK_ENHANCEMENT_TAB =
            CREATIVE_MODE_TABS.register("bedrock_enhancement_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.BEDROCK_PICKAXE.get()))
                    .title(Component.translatable("creativetab.bedrock_enhancement_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        // 基础材料
                        pOutput.accept(ModItems.BEDROCK_SCRAP.get());
                        pOutput.accept(ModItems.BEDROCK_POWDER.get());
                        pOutput.accept(ModItems.BEDROCK_POWDER_INGOT.get());
                        pOutput.accept(ModItems.BEDROCK_INGOT.get());
                        pOutput.accept(ModItems.QUASI_BEDROCK_INGOT.get());

                        // 工具
                        pOutput.accept(ModItems.BEDROCK_PICKAXE.get());
                        pOutput.accept(ModItems.BEDROCK_AXE.get());
                        pOutput.accept(ModItems.BEDROCK_SHOVEL.get());
                        pOutput.accept(ModItems.BEDROCK_SWORD.get());
                        pOutput.accept(ModItems.BEDROCK_HOE.get());

                        // 盔甲
                        pOutput.accept(ModItems.BEDROCK_HELMET.get());
                        pOutput.accept(ModItems.BEDROCK_CHESTPLATE.get());
                        pOutput.accept(ModItems.BEDROCK_LEGGINGS.get());
                        pOutput.accept(ModItems.BEDROCK_BOOTS.get());

                        // 方块物品
                        pOutput.accept(ModItems.BEDROCK_DEBRIS_ITEM.get());
                        pOutput.accept(ModItems.BEDROCK_BLOCK_ITEM.get());
                        pOutput.accept(ModItems.BEDROCK_SLAB_ITEM.get());
                        pOutput.accept(ModItems.BEDROCK_STAIRS_ITEM.get());
                        pOutput.accept(ModItems.QUASI_BEDROCK_BLOCK_ITEM.get());
                        pOutput.accept(ModItems.BEDROCK_SAND_ITEM.get());
                        pOutput.accept(ModItems.BEDROCK_GLASS_ITEM.get());

                        // 音乐唱片
                        pOutput.accept(ModItems.WANT_TO_BE_FURRY_RECORD.get());
                    })
                    .build());

    // 注册方法，在主类中调用
    public static void register(net.minecraftforge.eventbus.api.IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
