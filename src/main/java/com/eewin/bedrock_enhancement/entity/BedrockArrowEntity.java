package com.eewin.bedrock_enhancement.entity;

import com.eewin.bedrock_enhancement.registry.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * 基岩箭实体
 * 覆写 getPickUpItem() 让拾取时返回基岩箭（数量=1）
 * 客户端渲染使用基岩箭贴图
 */
public class BedrockArrowEntity extends Arrow {

    // 三个构造器（Forge 1.20.1 实体注册要求）
    public BedrockArrowEntity(EntityType<? extends Arrow> type, Level level) {
        super(type, level);
    }

    public BedrockArrowEntity(Level level, LivingEntity shooter) {
        super(level, shooter);
    }

    public BedrockArrowEntity(Level level, double x, double y, double z) {
        super(level, x, y, z);
    }

    // 覆写 getPickupItem（SRG 映射后的方法名，小写 p）
    // 确保拾取时返回基岩箭物品（数量=1）
    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(ModItems.BEDROCK_ARROW.get());
    }
}
