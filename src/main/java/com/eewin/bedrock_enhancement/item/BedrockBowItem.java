package com.eewin.bedrock_enhancement.item;

import com.eewin.bedrock_enhancement.registry.ModItems;
import com.eewin.bedrock_enhancement.util.BedrockEnchantmentFilter;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;

/**
 * 基岩弓 - 高耐久高射速弓
 * 耐久为原版弓的10倍（3840）
 * 防火
 *
 * 射速逻辑：
 * - 原版箭 + 基岩弓：速度 ×5
 * - 基岩箭 + 基岩弓：速度正常（1x）
 * - 基岩箭 + 原版弓：速度 ×0.2（1/5，由 BedrockArrowEventHandler 处理）
 */
public class BedrockBowItem extends BowItem {

    public BedrockBowItem() {
        super(new Properties().durability(3840).fireResistant());
    }

    /**
     * 禁止基岩弓附魔「无限」「经验修复」「耐久」
     * 其余弓类附魔（力量、冲击、火矢等）仍可正常附魔
     */
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.INFINITY_ARROWS
                || enchantment == Enchantments.MENDING
                || enchantment == Enchantments.UNBREAKING) {
            return false;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    // 拒绝附魔书中的「坚不可摧」(光谱世界) 与「轻裾凛云」(非靴子) 等禁止附魔
    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        if (BedrockEnchantmentFilter.isForbiddenBook(stack, book)) {
            return false;
        }
        return super.isBookEnchantable(stack, book);
    }

    @Override
    public void releaseUsing(ItemStack bowStack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (!(entityLiving instanceof Player player)) return;

        boolean infiniteAmmo = player.getAbilities().instabuild;
        ItemStack ammo = player.getProjectile(bowStack);

        if (!ammo.isEmpty() || infiniteAmmo) {
            if (ammo.isEmpty()) {
                ammo = new ItemStack(Items.ARROW);
            }

            int charge = this.getUseDuration(bowStack) - timeLeft;
            float power = getPowerForTime(charge);

            if ((double) power >= 0.1D) {
                boolean isInfinite = infiniteAmmo || (ammo.getItem() instanceof ArrowItem arrowItem && arrowItem.isInfinite(ammo, bowStack, player));

                if (!level.isClientSide()) {
                    ArrowItem arrowItem = (ArrowItem) (ammo.getItem() instanceof ArrowItem ? ammo.getItem() : Items.ARROW);
                    AbstractArrow arrow = arrowItem.createArrow(level, ammo, player);

                    // 根据箭类型设置速度
                    boolean isBedrockArrow = ammo.is(ModItems.BEDROCK_ARROW.get());
                    float velocity;
                    if (isBedrockArrow) {
                        // 基岩箭 + 基岩弓：速度正常（1x），事件处理器通过 instanceof 识别不减速
                        velocity = power * 3.0F;
                    } else {
                        // 原版箭 + 基岩弓：速度 ×5
                        velocity = power * 15.0F;
                    }

                    arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, velocity, 1.0F);

                    if (power == 1.0F) {
                        arrow.setCritArrow(true);
                    }

                    // 基岩箭的伤害已在 BedrockArrowItem.createArrow() 中设置×10
                    // 基岩弓射基岩箭：伤害下调20%（20 → 16）
                    if (isBedrockArrow) {
                        arrow.setBaseDamage(arrow.getBaseDamage() * 0.8F);
                    }

                    int knockback = bowStack.getEnchantmentLevel(Enchantments.PUNCH_ARROWS);
                    if (knockback > 0) {
                        arrow.setKnockback(knockback);
                    }

                    if (bowStack.getEnchantmentLevel(Enchantments.FLAMING_ARROWS) > 0) {
                        arrow.setSecondsOnFire(100);
                    }

                    bowStack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));

                    if (isInfinite || infiniteAmmo && (ammo.getItem() == Items.SPECTRAL_ARROW || ammo.getItem() == Items.TIPPED_ARROW)) {
                        arrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                    } else if (infiniteAmmo) {
                        arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
                    }

                    level.addFreshEntity(arrow);
                }

                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ARROW_SHOOT,
                        SoundSource.PLAYERS,
                        1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F);

                if (!isInfinite && !infiniteAmmo) {
                    ammo.shrink(1);
                    if (ammo.isEmpty()) {
                        player.getInventory().removeItem(ammo);
                    }
                }

                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
    }
}
