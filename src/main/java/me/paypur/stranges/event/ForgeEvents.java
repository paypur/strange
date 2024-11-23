package me.paypur.stranges.event;

import me.paypur.stranges.Stranges;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ForgeEvents {

    // note: runs every tick
    @SubscribeEvent
    void onTooltip(ItemTooltipEvent event) {

        if (event.getItemStack().getItem().equals(Items.WOODEN_AXE)) {

            CompoundTag tag = event.getItemStack().getTag();

            // CompoundTag id is 10
            CompoundTag tags = tag.getCompound("Strange");

            List<Component> components = event.getToolTip();
            components.add(TextComponent.EMPTY);

            long kills = tags.getLong("kills");
            components.add((new TextComponent(String.format("%s - Kills: %d", Stranges.rank(kills), kills)).withStyle(ChatFormatting.GRAY)));

            double damage = tags.getDouble("damage");
            components.add((new TextComponent(String.format("Damange: %.2f", damage)).withStyle(ChatFormatting.GRAY)));

        }

    }

    @SubscribeEvent
    void onKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ItemStack item = player.getMainHandItem();

            CompoundTag tag = item.getTag();

            if (tag == null) {
                return;
            }

            if (tag.getCompound("Strange").isEmpty()) {
                tag.put("Strange", new CompoundTag());
            }

            CompoundTag strange = tag.getCompound("Strange");

            if (strange.isEmpty()) {
                strange.putLong("kills", 0);
            }

            long kills = strange.getLong("kills");
            strange.putLong("kills", kills + 1);

            tag.put("Strange", strange);
        }
    }

    @SubscribeEvent
    void onDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player && event.getEntity() instanceof LivingEntity livingEntity) {
            ItemStack item = player.getMainHandItem();

            CompoundTag tag = item.getTag();

            if (tag == null) {
                return;
            }

            if (tag.getCompound("Strange").isEmpty()) {
                tag.put("Strange", new CompoundTag());
            }

            CompoundTag strange = tag.getCompound("Strange");

            if (!strange.contains("damage")) {
                strange.putDouble("damage", 0);
            }

            float dealt = Math.min(livingEntity.getHealth(), event.getAmount());
            double damage = strange.getDouble("damage");
            strange.putDouble("damage", damage + dealt);
            tag.put("Strange", strange);
        }
    }

}
