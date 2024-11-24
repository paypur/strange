package me.paypur.stranges.event;

import me.paypur.stranges.Stranges;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ForgeEvents {

    // note: runs every tick
    @SubscribeEvent
    void onTooltip(ItemTooltipEvent event) {
        if (Stranges.isStrangifiable(event.getItemStack().getItem())) {
            CompoundTag tag = event.getItemStack().getTag();

            if (tag == null) {
                return;
            }

            if (!tag.contains("Strange")) {
                return;
            }

            CompoundTag strange = tag.getCompound("Strange");

            List<Component> components = event.getToolTip();
            components.add(TextComponent.EMPTY);

            if (Stranges.isWeapon(event.getItemStack().getItem())) {
                long kills = strange.getLong("kills");
                components.add((new TextComponent(String.format("%s - Kills: %d", Stranges.rank(kills), kills)).withStyle(ChatFormatting.GRAY)));

                double damage = strange.getDouble("damage");
                components.add((new TextComponent(String.format("Damage Dealt: %.2f", damage)).withStyle(ChatFormatting.GRAY)));
            } else if (Stranges.isArmor(event.getItemStack().getItem())) {
                long hits = strange.getLong("hits");
                components.add((new TextComponent(String.format("%s - Hits Taken: %d", Stranges.rank(hits), hits)).withStyle(ChatFormatting.GRAY)));
            }
        }

    }

    @SubscribeEvent
    void onKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ItemStack item = player.getMainHandItem();

            if (!Stranges.isWeapon(item.getItem())) {
                return;
            }

            CompoundTag tag = item.getTag();

            if (tag == null) {
                return;
            }

            if (!tag.contains("Strange")) {
                return;
            }

            CompoundTag strange = tag.getCompound("Strange");

            if (!strange.contains("kills")) {
                strange.putLong("kills", 0);
            }

            long kills = strange.getLong("kills");
            strange.putLong("kills", kills + 1);

            tag.put("Strange", strange);
        }
    }

    @SubscribeEvent
    void onDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ItemStack weapon = player.getMainHandItem();

            if (!Stranges.isWeapon(weapon.getItem())) {
                return;
            }

            CompoundTag tag = weapon.getTag();

            if (tag == null) {
                return;
            }

            if (!tag.contains("Strange")) {
                return;
            }

            CompoundTag strange = tag.getCompound("Strange");

            float dealt = Math.min(event.getEntityLiving().getHealth(), event.getAmount());
            double damage = strange.getDouble("damage");

            strange.putDouble("damage", damage + dealt);
            tag.put("Strange", strange);
        } else if (event.getEntityLiving() instanceof Player player) {
            for (ItemStack armor: player.getArmorSlots()) {
                if (!(armor.getItem() instanceof ArmorItem)) {
                    return;
                }

                CompoundTag tag = armor.getTag();

                if (tag == null) {
                    return;
                }

                CompoundTag strange = tag.getCompound("Strange");

                long hits = strange.getLong("hits");
                strange.putLong("hits", hits + 1);

                tag.put("Strange", strange);
            }
        }

    }

}
