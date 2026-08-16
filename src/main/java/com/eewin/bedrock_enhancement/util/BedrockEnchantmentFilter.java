package com.eewin.bedrock_enhancement.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;

// =================================================================
// 基岩装备附魔书过滤
// 用于铁砧的 isBookEnchantable 钩子（最后阶段无条件执行，不受创造模式影响），
// 拦截基岩装备不应附魔的外来附魔。适配而非前置：全部用注册名字符串判断，不依赖目标模组类。
// =================================================================
public class BedrockEnchantmentFilter {

    // 光谱世界（Spectrum）「坚不可摧」附魔
    private static final ResourceLocation SPECTRUM_INDESTRUCTIBLE =
            new ResourceLocation("spectrum", "indestructible");

    // 本模组「轻裾凛云」附魔（仅靴子可附）
    private static final ResourceLocation CLOUD_STRIDE =
            new ResourceLocation("bedrock_enhancement", "cloud_stride");

    /**
     * 判断一本附魔书是否包含「基岩装备禁止」的附魔。
     *
     * @param target 被附魔的目标物品
     * @param book   附魔书
     * @return true 表示禁止（应拒绝附魔）
     */
    public static boolean isForbiddenBook(ItemStack target, ItemStack book) {
        boolean isBoots = target.getItem() instanceof ArmorItem armor
                && armor.getType() == ArmorItem.Type.BOOTS;

        Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(book);
        for (Enchantment enchantment : enchants.keySet()) {
            ResourceLocation key = ForgeRegistries.ENCHANTMENTS.getKey(enchantment);
            if (key == null) continue;

            // 坚不可摧：任何基岩装备都禁止
            if (SPECTRUM_INDESTRUCTIBLE.equals(key)) {
                return true;
            }
            // 轻裾凛云：仅靴子可附，非靴子基岩装备禁止
            if (CLOUD_STRIDE.equals(key) && !isBoots) {
                return true;
            }
        }
        return false;
    }
}
