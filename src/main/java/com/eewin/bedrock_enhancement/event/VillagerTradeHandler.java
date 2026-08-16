package com.eewin.bedrock_enhancement.event;

import com.eewin.bedrock_enhancement.BedrockEnhancement;
import com.eewin.bedrock_enhancement.registry.ModEnchants;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = BedrockEnhancement.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VillagerTradeHandler {

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() != VillagerProfession.LIBRARIAN) return;

        // Level 5 = Master (最高等级)
        // 交易：20 个绿宝石 + 1 本书 → 基岩挖掘者 I 附魔书
        event.getTrades().get(5).add((trader, random) -> {
            ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantedBookItem.addEnchantment(book,
                    new net.minecraft.world.item.enchantment.EnchantmentInstance(
                            ModEnchants.BEDROCK_MINER.get(), 1));
            return new MerchantOffer(
                    new ItemStack(Items.EMERALD, 20),   // 第一个输入：20 绿宝石
                    new ItemStack(Items.BOOK),            // 第二个输入：1 本书
                    book,                                  // 输出：基岩挖掘者 I 附魔书
                    12,                                     // maxUses
                    10,                                     // villagerXp
                    0.05f                                   // priceMultiplier
            );
        });

        // 交易：32 个绿宝石 + 1 本书 → 基岩破坏者 I 附魔书（II 级仅附魔台可得）
        event.getTrades().get(5).add((trader, random) -> {
            ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantedBookItem.addEnchantment(book,
                    new net.minecraft.world.item.enchantment.EnchantmentInstance(
                            ModEnchants.BEDROCK_BREAKER.get(), 1));
            return new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),   // 第一个输入：32 绿宝石
                    new ItemStack(Items.BOOK),            // 第二个输入：1 本书
                    book,                                  // 输出：基岩破坏者 I 附魔书
                    12,                                     // maxUses
                    10,                                     // villagerXp
                    0.05f                                   // priceMultiplier
            );
        });

        // 交易：64 个绿宝石 + 1 本书 → 轻裾凛云附魔书
        event.getTrades().get(5).add((trader, random) -> {
            ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
            EnchantedBookItem.addEnchantment(book,
                    new net.minecraft.world.item.enchantment.EnchantmentInstance(
                            ModEnchants.CLOUD_STRIDE.get(), 1));
            return new MerchantOffer(
                    new ItemStack(Items.EMERALD, 64),   // 第一个输入：64 绿宝石
                    new ItemStack(Items.BOOK),            // 第二个输入：1 本书
                    book,                                  // 输出：轻裾凛云附魔书
                    12,                                     // maxUses
                    10,                                     // villagerXp
                    0.05f                                   // priceMultiplier
            );
        });
    }
}
