package com.eewin.bedrock_enhancement.registry;

import com.eewin.bedrock_enhancement.BedrockEnhancement;
import com.eewin.bedrock_enhancement.enchantment.BedrockMinerEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchants {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, BedrockEnhancement.MOD_ID);

    public static final RegistryObject<Enchantment> BEDROCK_MINER =
            ENCHANTMENTS.register("bedrock_miner", BedrockMinerEnchantment::new);

    public static void register(net.minecraftforge.eventbus.api.IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
