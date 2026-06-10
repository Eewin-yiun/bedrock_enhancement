package com.eewin.bedrock_enhancement.item;

import net.minecraft.world.item.Item;

/**
 * 基岩棍 - 类似木棍但防火
 * 用于合成基岩工具和其他物品
 * 合成：2个基岩竖着排 → 4个基岩棍
 */
public class BedrockStickItem extends Item {

    public BedrockStickItem() {
        super(new Item.Properties().fireResistant());
    }
}
