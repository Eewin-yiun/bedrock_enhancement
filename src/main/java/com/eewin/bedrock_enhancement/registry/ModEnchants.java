package com.eewin.bedrock_enhancement.registry;

import com.eewin.bedrock_enhancement.BedrockEnhancement;
import com.eewin.bedrock_enhancement.enchantment.BedrockBreakerEnchantment;
import com.eewin.bedrock_enhancement.enchantment.BedrockMinerEnchantment;
import com.eewin.bedrock_enhancement.enchantment.CloudStrideEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchants {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, BedrockEnhancement.MOD_ID);

    public static final RegistryObject<Enchantment> BEDROCK_MINER =
            ENCHANTMENTS.register("bedrock_miner", BedrockMinerEnchantment::new);

    // 基岩破坏者 I/II：仅基岩镐，每等级挖掘基岩速度 +50%
    public static final RegistryObject<Enchantment> BEDROCK_BREAKER =
            ENCHANTMENTS.register("bedrock_breaker", BedrockBreakerEnchantment::new);

    // 轻裾凛云：任何靴子，移速 +10%
    public static final RegistryObject<Enchantment> CLOUD_STRIDE =
            ENCHANTMENTS.register("cloud_stride", CloudStrideEnchantment::new);

    public static void register(net.minecraftforge.eventbus.api.IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
