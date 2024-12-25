package me.paypur.strange.event;

import me.paypur.strange.Strange;
import me.paypur.strange.item.StrangePart;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ForgeEvents {

    // note: runs every tick
    @SubscribeEvent(priority = EventPriority.HIGH)
    void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        CompoundTag tag = stack.getTag();

        if (tag == null || !tag.contains(Strange.MOD_ID)) {
            return;
        }

        CompoundTag strange = tag.getCompound(Strange.MOD_ID);
        List<Component> components = event.getToolTip();

        Strange.STRANGIFIER.get().appendComponent(stack, components);

        String nbtKey = Strange.STRANGIFIER.get().getNbtKey(stack);

        for (String key: strange.getAllKeys()) {
            if (!nbtKey.equals(key)) {
                StrangePart part = Strange.STRANGE_PART_MAP.get(key);
                if (part != null) {
                    part.appendComponent(stack, components);
                }
            }
        }
    }

    @SubscribeEvent
    void onKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            InteractionHand hand = player.getUsedItemHand();
            ItemStack stack = player.getItemInHand(hand);

            Strange.STRANGE_PART_KILLS.get().incrementTag(stack);

            if (!event.getEntityLiving().isOnGround()) {
                Strange.STRANGE_PART_KILLS_AIRBORNE.get().incrementTag(stack);
            }

            if (player.isUnderWater()) {
                Strange.STRANGE_PART_KILLS_UNDERWATER.get().incrementTag(stack);
            }

            // Crit check from Player.attack()
            // pTarget instanceof LivingEntity is implied to be true
            // TODO: the attackStrengthScale is set to 0 before this event is called, so it doesn't work
//            boolean isCrit = player.getAttackStrengthScale(0.5F) > 0.9F && player.fallDistance > 0.0F &&
//                    !player.isOnGround() && !player.onClimbable() &&
//                    !player.isInWater() && !player.hasEffect(MobEffects.BLINDNESS) &&
//                    !player.isPassenger() && !player.isSprinting();
//
//            if (strange.contains(Strange.NBT_CRITICAL_KIllS) && isCrit) {
//                long critKills = strange.getLong(Strange.NBT_CRITICAL_KIllS);
//                strange.putLong(Strange.NBT_CRITICAL_KIllS, critKills + 1);
//            }
        }
    }

    @SubscribeEvent
    // maybe use AttackEntityEvent instead
    void onDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            InteractionHand hand = player.getUsedItemHand();
            ItemStack stack = player.getItemInHand(hand);

            float dealt = Math.min(event.getEntityLiving().getHealth(), event.getAmount());
            Strange.STRANGE_PART_DAMAGE_DEALT.get().incrementTag(stack, dealt);

            Strange.STRANGE_PART_TARGETS_HIT.get().incrementTag(stack);
        }
    }

    @SubscribeEvent
    void onBowRelease(ArrowLooseEvent event) {
        Strange.STRANGE_PART_TIMES_FIRED.get().incrementTag(event.getBow());
    }

    @SubscribeEvent
    void onMine(BlockEvent.BreakEvent event) {
        ItemStack stack = event.getPlayer().getMainHandItem();
        Strange.STRANGE_PART_BLOCKS_BROKEN.get().incrementTag(stack);

        if (event.getState().getTags().anyMatch(t -> t.equals(Tags.Blocks.ORES))) {
            Strange.STRANGE_PART_ORES_BROKEN.get().incrementTag(stack);
        }
    }

    @SubscribeEvent
    void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        event.getItemStack();
        event.getUseBlock();
    }

    @SubscribeEvent
    void onBlock(ShieldBlockEvent event) {
        if (event.getEntityLiving() instanceof Player player) {
            if (player.getMainHandItem().is(Strange.DEFENSE_SHIELD)) {
                Strange.STRANGE_PART_DAMAGE_BLOCKED.get().incrementTag(player.getMainHandItem(), event.getOriginalBlockedDamage());
            } else if (player.getOffhandItem().is(Strange.DEFENSE_SHIELD)) {
                Strange.STRANGE_PART_DAMAGE_BLOCKED.get().incrementTag(player.getOffhandItem(), event.getOriginalBlockedDamage());
            }
        }
    }
}
