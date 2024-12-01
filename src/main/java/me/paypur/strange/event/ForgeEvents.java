package me.paypur.strange.event;

import me.paypur.strange.Strange;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ForgeEvents {

    // note: runs every tick
    @SubscribeEvent
    void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        CompoundTag tag = event.getItemStack().getTag();

        if (tag == null || !tag.contains(Strange.MOD_ID)) {
            return;
        }

        CompoundTag strange = tag.getCompound(Strange.MOD_ID);

        List<Component> components = event.getToolTip();
        components.add(TextComponent.EMPTY);

        if (Strange.isWeapon(stack.getItem())) {
            long kills = strange.getLong(Strange.TAG_KILLS);
            components.add((new TextComponent(String.format("%s - Kills: %d", Strange.rank(kills), kills)).withStyle(ChatFormatting.DARK_GRAY)));

            if (strange.contains(Strange.TAG_DAMAGE_DEALT)) {
                double damage = strange.getDouble(Strange.TAG_DAMAGE_DEALT);
                components.add((new TextComponent(String.format("Damage Dealt: %.2f", damage)).withStyle(ChatFormatting.DARK_GRAY)));
            }

        } else if (Strange.isTool(stack.getItem())) {
            long blocks = strange.getLong(Strange.TAG_BLOCKS_BROKEN);
            components.add((new TextComponent(String.format("%s - Blocks Broken: %d", Strange.rank(blocks), blocks)).withStyle(ChatFormatting.DARK_GRAY)));
        } else if (Strange.isArmor(stack.getItem())) {
            long hits = strange.getLong(Strange.TAG_HITS_TAKEN);
            components.add((new TextComponent(String.format("%s - Hits Taken: %d", Strange.rank(hits), hits)).withStyle(ChatFormatting.DARK_GRAY)));
        }

    }

    @SubscribeEvent
    void onKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ItemStack stack = player.getMainHandItem();
            CompoundTag tag = stack.getTag();

            if (!Strange.isWeapon(stack.getItem()) || tag == null || !tag.contains(Strange.MOD_ID)) {
                return;
            }

            CompoundTag strange = tag.getCompound(Strange.MOD_ID);

            long kills = strange.getLong(Strange.TAG_KILLS);
            strange.putLong(Strange.TAG_KILLS, kills + 1);

            tag.put(Strange.TAG_HITS_TAKEN, strange);
        }
    }

    @SubscribeEvent
    void onDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ItemStack stack = player.getMainHandItem();
            CompoundTag tag = stack.getTag();

            if (!Strange.isWeapon(stack.getItem()) || tag == null || !tag.contains(Strange.MOD_ID)) {
                return;
            }

            CompoundTag strange = tag.getCompound(Strange.MOD_ID);
            
            if (!strange.contains(Strange.TAG_DAMAGE_DEALT)) {
                return;
            }

            float dealt = Math.min(event.getEntityLiving().getHealth(), event.getAmount());
            double damage = strange.getDouble(Strange.TAG_DAMAGE_DEALT);

            strange.putDouble(Strange.TAG_DAMAGE_DEALT, damage + dealt);

            tag.put(Strange.TAG_DAMAGE_DEALT, strange);
        } else if (event.getEntityLiving() instanceof Player player) {
            for (ItemStack armor: player.getArmorSlots()) {
                CompoundTag tag = armor.getTag();

                if (!(armor.getItem() instanceof ArmorItem) || tag == null) {
                    continue;
                }

                CompoundTag strange = tag.getCompound(Strange.MOD_ID);

                long hits = strange.getLong(Strange.TAG_HITS_TAKEN);
                strange.putLong(Strange.TAG_HITS_TAKEN, hits + 1);

                tag.put(Strange.TAG_HITS_TAKEN, strange);
            }
        }

    }

    @SubscribeEvent
    void onMine(BlockEvent.BreakEvent event) {
        ItemStack stack = event.getPlayer().getMainHandItem();
        CompoundTag tag = stack.getTag();

        if (tag == null || !Strange.isTool(stack.getItem()) || !tag.contains(Strange.TAG_HITS_TAKEN)) {
            return;
        }

        CompoundTag strange = tag.getCompound(Strange.TAG_HITS_TAKEN);

        long blocks = strange.getLong(Strange.TAG_BLOCKS_BROKEN);
        strange.putLong(Strange.TAG_BLOCKS_BROKEN, blocks + 1);
    }

}
