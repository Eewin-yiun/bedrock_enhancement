package com.eewin.bedrock_enhancement.registry;

import com.eewin.bedrock_enhancement.entity.BedrockArrowEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * 实体注册类
 */
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "bedrock_enhancement");

    // 基岩箭实体
    public static final Supplier<EntityType<BedrockArrowEntity>> BEDROCK_ARROW =
            ENTITIES.register("bedrock_arrow",
                    () -> EntityType.Builder.<BedrockArrowEntity>of(BedrockArrowEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .build("bedrock_arrow"));

    public static void register(IEventBus bus) {
        ENTITIES.register(bus);
    }
}
