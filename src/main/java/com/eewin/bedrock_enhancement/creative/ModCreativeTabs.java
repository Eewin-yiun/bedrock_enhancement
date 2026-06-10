package com.eewin.bedrock_enhancement.creative;

import com.eewin.bedrock_enhancement.BedrockEnhancement;
import com.eewin.bedrock_enhancement.registry.ModBlocks;
import com.eewin.bedrock_enhancement.registry.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// ========================================
// 创造模式物品栏注册类
// ========================================

public class ModCreativeTabs {

    // 用 ResourceKey 直接构造，避免引用 Registries.CREATIVE_MODE_TAB 字段
    // （该字段在 SRG 映射下不存在，会导致 NoSuchFieldError 崩溃）
    private static final ResourceKey<Registry<CreativeModeTab>> CREATIVE_MODE_TAB_KEY =
            ResourceKey.createRegistryKey(new ResourceLocation("minecraft", "creative_mode_tab"));

    // 创建创造栏延迟注册器
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(CREATIVE_MODE_TAB_KEY, BedrockEnhancement.MOD_ID);

    // 基岩增强创造栏
    public static final RegistryObject<CreativeModeTab> BEDROCK_TAB = CREATIVE_MODE_TABS.register(
            "bedrock_tab",
            () -> CreativeModeTab.builder()
                    // 图标使用基岩锭
                    .icon(() -> new ItemStack(ModItems.BEDROCK_INGOT.get()))
                    // 显示名称
                    .title(Component.translatable("itemGroup.bedrock_enhancement"))
                    // 物品显示顺序
                    .displayItems((parameters, output) -> {
                        // --- 基础材料 ---
                        output.accept(ModItems.BEDROCK_SCRAP.get());
                        output.accept(ModItems.BEDROCK_POWDER.get());
                        output.accept(ModItems.BEDROCK_POWDER_INGOT.get());
                        output.accept(ModItems.BEDROCK_INGOT.get());
                        output.accept(ModItems.QUASI_BEDROCK_INGOT.get());

                        // --- 方块 ---
                        output.accept(ModItems.BEDROCK_DEBRIS_ITEM.get());
                        output.accept(ModItems.BEDROCK_BLOCK_ITEM.get());
                        output.accept(ModItems.BEDROCK_SLAB_ITEM.get());
                        output.accept(ModItems.BEDROCK_STAIRS_ITEM.get());
                        output.accept(ModItems.QUASI_BEDROCK_BLOCK_ITEM.get());
                        output.accept(ModItems.BEDROCK_SAND_ITEM.get());
                        output.accept(ModItems.BEDROCK_CLAY_ITEM.get());
                        output.accept(ModItems.BEDROCK_BRICKS_ITEM.get());

                        // --- 工具 ---
                        output.accept(ModItems.BEDROCK_PICKAXE.get());
                        output.accept(ModItems.BEDROCK_AXE.get());
                        output.accept(ModItems.BEDROCK_SHOVEL.get());
                        output.accept(ModItems.BEDROCK_HOE.get());
                        output.accept(ModItems.BEDROCK_SWORD.get());

                        // --- 盔甲 ---
                        output.accept(ModItems.BEDROCK_HELMET.get());
                        output.accept(ModItems.BEDROCK_CHESTPLATE.get());
                        output.accept(ModItems.BEDROCK_LEGGINGS.get());
                        output.accept(ModItems.BEDROCK_BOOTS.get());

                        // --- 玻璃方块 ---
                        output.accept(ModItems.BEDROCK_GLASS_ITEM.get());

                        // --- 基岩门 ---
                        output.accept(ModItems.BEDROCK_DOOR_ITEM.get());

                        // --- 基岩砂岩 ---
                        output.accept(ModItems.BEDROCK_SANDSTONE_ITEM.get());

                        // --- 特殊物品 ---
                        output.accept(ModItems.BEDROCK_APPLE.get());
                        output.accept(ModItems.BEDROCK_CLAY_BALL.get());
                        output.accept(ModItems.BEDROCK_BRICK.get());
                        output.accept(ModItems.BEDROCK_STICK.get());
                        output.accept(ModItems.BEDROCK_STRING_ROUGH.get());
                        output.accept(ModItems.BEDROCK_STRING.get());
                        output.accept(ModItems.BEDROCK_BOW.get());
                        output.accept(ModItems.BEDROCK_ARROW.get());

                        // --- 音乐唱片 ---
                        output.accept(ModItems.WANT_TO_BE_FURRY_RECORD.get());

                        // --- 附魔书 ---
                        ItemStack minerBook = new ItemStack(Items.ENCHANTED_BOOK);
                        net.minecraft.world.item.enchantment.Enchantment minerEnch =
                                ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(BedrockEnhancement.MOD_ID, "bedrock_miner"));
                        if (minerEnch != null) {
                            CompoundTag enchTag = new CompoundTag();
                            enchTag.putString("id", ForgeRegistries.ENCHANTMENTS.getKey(minerEnch).toString());
                            enchTag.putShort("lvl", (short)1);
                            ListTag stored = new ListTag();
                            stored.add(enchTag);
                            minerBook.getOrCreateTag().put("StoredEnchantments", stored);
                            output.accept(minerBook);
                        }
                    })
                    .build());

    // 注册方法
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
