package me.paypur.strange.event;

import me.paypur.strange.Strange;
import me.paypur.strange.item.StrangePart;
import me.paypur.strange.ILastAttackCrit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.TreeSet;

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

        // this compares the strange part id, which is not necessarily the same as the translation
        for (String key: new TreeSet<>(strange.getAllKeys())) {
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
            // TODO: wrong
            ItemStack stack = player.getItemInHand(player.getUsedItemHand());

            Strange.STRANGE_PART_KILLS.get().incrementTag(stack);

            if (((ILastAttackCrit) player).strange$getIsLastAttackCrit()) {
                Strange.STRANGE_PART_KILLS_CRITICAL.get().incrementTag(stack);
            }

            if (!event.getEntityLiving().isOnGround()) {
                Strange.STRANGE_PART_KILLS_AIRBORNE.get().incrementTag(stack);
            }

            if (player.isUnderWater()) {
                Strange.STRANGE_PART_KILLS_UNDERWATER.get().incrementTag(stack);
            }
        }
    }

    @SubscribeEvent
    // maybe use AttackEntityEvent instead
    void onDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            // TODO: wrong
            ItemStack stack = player.getItemInHand(player.getUsedItemHand());

            float dealt = Math.min(event.getEntityLiving().getHealth(), event.getAmount());
            Strange.STRANGE_PART_DAMAGE_DEALT.get().incrementTag(stack, dealt);

            Strange.STRANGE_PART_HITS.get().incrementTag(stack);

            if (((ILastAttackCrit) player).strange$getIsLastAttackCrit()) {
                Strange.STRANGE_PART_HITS_CRITICAL.get().incrementTag(stack);
            }
        }
        if (event.getEntityLiving() instanceof Player player) {
            for (ItemStack stack : player.getArmorSlots()) {
                if (stack.getItem() instanceof ArmorItem armor) {
                    // This will assume damage reduction is linear, which it isn't but whatever close enough
                    Strange.STRANGE_PART_DAMAGE_TAKEN.get().incrementTag(stack, event.getAmount() * armor.getDefense() / player.getArmorValue());
                }
            }
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
    void onBlock(ShieldBlockEvent event) {
        if (event.getEntityLiving() instanceof Player player) {
            if (player.getMainHandItem().getItem() instanceof ShieldItem) {
                Strange.STRANGE_PART_DAMAGE_BLOCKED.get().incrementTag(player.getMainHandItem(), event.getOriginalBlockedDamage());
            } else if (player.getOffhandItem().getItem() instanceof ShieldItem) {
                Strange.STRANGE_PART_DAMAGE_BLOCKED.get().incrementTag(player.getOffhandItem(), event.getOriginalBlockedDamage());
            }
        }
    }
}
