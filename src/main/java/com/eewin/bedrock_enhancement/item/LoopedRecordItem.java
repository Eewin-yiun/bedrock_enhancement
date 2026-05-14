package com.eewin.bedrock_enhancement.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;

import java.util.function.Supplier;

/**
 * 自定义唱片基类，修复原版 RecordItem 在 Forge 1.20.1 中
 * 对自定义 SoundEvent 处理不正确的问题。
 *
 * 与原版 RecordItem 的区别：
 * - 显式持有 Supplier<SoundEvent>，确保 getSound() 始终返回有效对象
 * - 避免 RegistryObject 延迟解析导致的 null 问题
 */
public class LoopedRecordItem extends RecordItem {

    private final Supplier<SoundEvent> soundSupplier;

    public LoopedRecordItem(int comparatorValue, Supplier<SoundEvent> soundSupplier, Item.Properties properties, int lengthInTicks) {
        super(comparatorValue, soundSupplier, properties, lengthInTicks);
        this.soundSupplier = soundSupplier;
    }

    @Override
    public SoundEvent getSound() {
        return this.soundSupplier.get();
    }
}
