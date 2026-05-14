package com.eewin.bedrock_enhancement.item;

import com.eewin.bedrock_enhancement.BedrockEnhancement;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

// ======================================
// 基岩盔甲物品类
// 全套穿戴时：防火 + 免疫击退（击退抗性已在材质中设为1.0）
// ======================================

public class BedrockArmorItem extends ArmorItem {

    public BedrockArmorItem(ArmorItem.Type type) {
        super(BedrockArmorMaterial.BEDROCK,
                type,
                new net.minecraft.world.item.Item.Properties()
                        .durability(BedrockArmorMaterial.BEDROCK.getDurabilityForType(type))
                        .fireResistant());  // 盔甲防火：不会被岩浆/火焰烧毁
    }

    // 基岩盔甲防火：不会被岩浆或火焰烧毁
    @Override
    public boolean isFireResistant() {
        return true;
    }

    // Forge 1.20.1: IForgeItem.getArmorTexture() 返回 String
    // 用 EquipmentSlot 判断使用哪个贴图层：
    //   LEGS → layer_2（护腿）
    //   其他 → layer_1（头盔、胸甲、靴子）
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        String layerFile = (slot == EquipmentSlot.LEGS) ? "bedrock_layer_2.png" : "bedrock_layer_1.png";
        return BedrockEnhancement.MOD_ID + ":textures/models/armor/" + layerFile;
    }

    // 穿戴者每 tick 给予防火效果（由 BedrockArmorEventHandler 统一处理全套检测）
}
