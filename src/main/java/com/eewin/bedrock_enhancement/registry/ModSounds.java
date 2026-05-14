package com.eewin.bedrock_enhancement.registry;

import com.eewin.bedrock_enhancement.BedrockEnhancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// ========================================
// 声音事件注册类
// 所有自定义声音事件都在这里注册
// ========================================

public class ModSounds {

    // 创建声音事件延迟注册器
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BedrockEnhancement.MOD_ID);

    // ========== 音乐声音事件 ==========

    // 《好想变成毛毛》— 歌手：逸云 / Eewin
    // 声音文件路径：assets/bedrock_enhancement/sounds/records/want_to_be_furry.ogg
    public static final RegistryObject<SoundEvent> WANT_TO_BE_FURRY =
            SOUND_EVENTS.register("records/want_to_be_furry",
                    () -> SoundEvent.createFixedRangeEvent(
                            new ResourceLocation(BedrockEnhancement.MOD_ID, "records/want_to_be_furry"),
                            16.0F));

    // 注册方法，在主类中调用
    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
