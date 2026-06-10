package com.eewin.bedrock_enhancement.registry;

import com.eewin.bedrock_enhancement.BedrockEnhancement;
import com.eewin.bedrock_enhancement.block.ModBedrockBlock;
import com.eewin.bedrock_enhancement.block.QuasiBedrockBlock;
import com.eewin.bedrock_enhancement.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

// =======================================
// 物品注册类
// 所有自定义物品、工具、盔甲都在这里注册
// =======================================

public class ModItems {

    // 创建物品延迟注册器
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BedrockEnhancement.MOD_ID);

    // ======== 基础材料物品 ========

    // 基岩碎渣：挖掘基岩残骸获得
    public static final RegistryObject<Item> BEDROCK_SCRAP = ITEMS.register("bedrock_scrap",
            () -> new Item(new Item.Properties()));

    // 基岩粉末：9个碎渣合成，或挖掘真基岩获得
    public static final RegistryObject<Item> BEDROCK_POWDER = ITEMS.register("bedrock_powder",
            () -> new Item(new Item.Properties()));

    // 基岩粉锭：9个基岩粉末合成
    public static final RegistryObject<Item> BEDROCK_POWDER_INGOT = ITEMS.register("bedrock_powder_ingot",
            () -> new Item(new Item.Properties()));

    // 基岩锭：烧制基岩粉锭获得
    public static final RegistryObject<Item> BEDROCK_INGOT = ITEMS.register("bedrock_ingot",
            () -> new Item(new Item.Properties()));

    // 准基岩锭：烧制准基岩块获得
    public static final RegistryObject<Item> QUASI_BEDROCK_INGOT = ITEMS.register("quasi_bedrock_ingot",
            () -> new Item(new Item.Properties()));

    // ======== 基岩工具（1.5倍下界合金属性） ========

    // 基岩镐：可以挖掘真基岩，挖掘速度参考钻石镐挖黑曜石
    public static final RegistryObject<Item> BEDROCK_PICKAXE = ITEMS.register("bedrock_pickaxe",
            BedrockPickaxeItem::new);

    // 基岩斧
    public static final RegistryObject<Item> BEDROCK_AXE = ITEMS.register("bedrock_axe",
            BedrockAxeItem::new);

    // 基岩锹
    public static final RegistryObject<Item> BEDROCK_SHOVEL = ITEMS.register("bedrock_shovel",
            BedrockShovelItem::new);

    // 基岩剑
    public static final RegistryObject<Item> BEDROCK_SWORD = ITEMS.register("bedrock_sword",
            BedrockSwordItem::new);

    // 基岩锄
    public static final RegistryObject<Item> BEDROCK_HOE = ITEMS.register("bedrock_hoe",
            BedrockHoeItem::new);

    // ======== 基岩盔甲（1.5倍下界合金属性） ========

    // 基岩头盔
    public static final RegistryObject<Item> BEDROCK_HELMET = ITEMS.register("bedrock_helmet",
            () -> new BedrockArmorItem(net.minecraft.world.item.ArmorItem.Type.HELMET));

    // 基岩胸甲
    public static final RegistryObject<Item> BEDROCK_CHESTPLATE = ITEMS.register("bedrock_chestplate",
            () -> new BedrockArmorItem(net.minecraft.world.item.ArmorItem.Type.CHESTPLATE));

    // 基岩护腿
    public static final RegistryObject<Item> BEDROCK_LEGGINGS = ITEMS.register("bedrock_leggings",
            () -> new BedrockArmorItem(net.minecraft.world.item.ArmorItem.Type.LEGGINGS));

    // 基岩靴子
    public static final RegistryObject<Item> BEDROCK_BOOTS = ITEMS.register("bedrock_boots",
            () -> new BedrockArmorItem(net.minecraft.world.item.ArmorItem.Type.BOOTS));

    // ======== 特殊物品 ========

    // 基岩苹果：吃下后获得强大的正面效果和负面效果
    public static final RegistryObject<Item> BEDROCK_APPLE = ITEMS.register("bedrock_apple",
            BedrockAppleItem::new);

    // 磐基黏土球：类似黏土球，挖掘磐基黏土获得4个
    public static final RegistryObject<Item> BEDROCK_CLAY_BALL = ITEMS.register("bedrock_clay_ball",
            BedrockClayBallItem::new);

    // 基岩砖：烧制磐基黏土球获得，用于合成基岩砖块
    public static final RegistryObject<Item> BEDROCK_BRICK = ITEMS.register("bedrock_brick",
            BedrockBrickItem::new);

    // 基岩棍：2个基岩竖着排合成4个，防火
    public static final RegistryObject<Item> BEDROCK_STICK = ITEMS.register("bedrock_stick",
            BedrockStickItem::new);

    // 基岩准线：3个磐基黏土球对角线排列合成，烧炼后获得基岩线
    public static final RegistryObject<Item> BEDROCK_STRING_ROUGH = ITEMS.register("bedrock_string_rough",
            BedrockStringRoughItem::new);

    // 基岩线：烧炼基岩准线获得
    public static final RegistryObject<Item> BEDROCK_STRING = ITEMS.register("bedrock_string",
            BedrockStringItem::new);

    // 基岩弓：高耐久高射速弓，耐久3840（原版384×10）
    public static final RegistryObject<Item> BEDROCK_BOW = ITEMS.register("bedrock_bow",
            BedrockBowItem::new);

    // 基岩箭：高伤害箭矢，伤害为原版箭的10倍
    public static final RegistryObject<Item> BEDROCK_ARROW = ITEMS.register("bedrock_arrow",
            BedrockArrowItem::new);

    // ======== 方块物品（让方块能放在物品栏里） ========

    // 基岩残骸的物品形式
    public static final RegistryObject<Item> BEDROCK_DEBRIS_ITEM = ITEMS.register("bedrock_debris",
            () -> new BlockItem(ModBlocks.BEDROCK_DEBRIS.get(),
                    new Item.Properties()));

    // 基岩块的物品形式
    public static final RegistryObject<Item> BEDROCK_BLOCK_ITEM = ITEMS.register("bedrock_block",
            () -> new BlockItem(ModBlocks.BEDROCK_BLOCK.get(),
                    new Item.Properties()));

    // 准基岩块的物品形式
    public static final RegistryObject<Item> QUASI_BEDROCK_BLOCK_ITEM = ITEMS.register("quasi_bedrock_block",
            () -> new BlockItem(ModBlocks.QUASI_BEDROCK_BLOCK.get(),
                    new Item.Properties()));

    // 基岩沙的物品形式
    public static final RegistryObject<Item> BEDROCK_SAND_ITEM = ITEMS.register("bedrock_sand",
            () -> new BlockItem(ModBlocks.BEDROCK_SAND.get(),
                    new Item.Properties()));

    // 基岩玻璃的物品形式
    public static final RegistryObject<Item> BEDROCK_GLASS_ITEM = ITEMS.register("bedrock_glass",
            () -> new BlockItem(ModBlocks.BEDROCK_GLASS.get(),
                    new Item.Properties()));

    // 基岩半砖的物品形式
    public static final RegistryObject<Item> BEDROCK_SLAB_ITEM = ITEMS.register("bedrock_slab",
            () -> new BlockItem(ModBlocks.BEDROCK_SLAB.get(),
                    new Item.Properties()));

    // 基岩楼梯的物品形式
    public static final RegistryObject<Item> BEDROCK_STAIRS_ITEM = ITEMS.register("bedrock_stairs",
            () -> new BlockItem(ModBlocks.BEDROCK_STAIRS.get(),
                    new Item.Properties()));

    // 基岩门的物品形式
    public static final RegistryObject<Item> BEDROCK_DOOR_ITEM = ITEMS.register("bedrock_door",
            () -> new BlockItem(ModBlocks.BEDROCK_DOOR.get(),
                    new Item.Properties()));

    // 基岩砂岩的物品形式
    public static final RegistryObject<Item> BEDROCK_SANDSTONE_ITEM = ITEMS.register("bedrock_sandstone",
            () -> new BlockItem(ModBlocks.BEDROCK_SANDSTONE.get(),
                    new Item.Properties()));

    // 磐基黏土的物品形式
    public static final RegistryObject<Item> BEDROCK_CLAY_ITEM = ITEMS.register("bedrock_clay",
            () -> new BlockItem(ModBlocks.BEDROCK_CLAY.get(),
                    new Item.Properties()));

    // 基岩砖块的物品形式
    public static final RegistryObject<Item> BEDROCK_BRICKS_ITEM = ITEMS.register("bedrock_bricks",
            () -> new BlockItem(ModBlocks.BEDROCK_BRICKS.get(),
                    new Item.Properties()));

    // ======== 音乐唱片 ========

    // 《好想变成毛毛》唱片 — 歌手：逸云 / Eewin
    // 放入唱片机（Jukebox）即可播放，稀有度 RARE
    public static final RegistryObject<Item> WANT_TO_BE_FURRY_RECORD = ITEMS.register("want_to_be_furry_record",
            WantToFurryRecordItem::new);

    // 注册方法，在主类中调用
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
