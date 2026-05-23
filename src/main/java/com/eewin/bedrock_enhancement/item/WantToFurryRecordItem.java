package com.eewin.bedrock_enhancement.item;

import com.eewin.bedrock_enhancement.registry.ModSounds;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

// =======================================
// 《好想变成毛毛》唱片物品
// 歌手：逸云 / Eewin
// 放入唱片机（Jukebox）即可播放
// 继承 LoopedRecordItem，修复 Forge 1.20.1 自定义 SoundEvent 无声问题
// =======================================

public class WantToFurryRecordItem extends LoopedRecordItem {

    // comparator 参数：15（与原版唱片相同，控制比较器输出强度）
    private static final int COMPARATOR_VALUE = 15;
    // 唱片时长（游戏刻）：4640 tick = 232秒 = 3分52秒
    private static final int RECORD_LENGTH = 4640;

    @SuppressWarnings("deprecation")
    public WantToFurryRecordItem() {
        super(COMPARATOR_VALUE,
                ModSounds.WANT_TO_BE_FURRY,
                new Properties()
                        .stacksTo(1)
                        .rarity(Rarity.RARE),
                RECORD_LENGTH);
    }

    // 控制物品名称（物品提示和"正在播放"均使用此方法）
    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable("item.bedrock_enhancement.want_to_be_furry_record");
    }

    // 控制"正在播放"显示的名称
    @Override
    public String getDescriptionId() {
        return "item.bedrock_enhancement.want_to_be_furry_record";
    }

    // 自定义物品提示
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("subtitle.bedrock_enhancement.music.want_to_be_furry"));
    }
}
