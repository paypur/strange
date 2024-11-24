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
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ForgeEvents {

    // note: runs every tick
    @SubscribeEvent
    void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        CompoundTag tag = event.getItemStack().getTag();

        if (tag == null || !tag.contains("Strange")) {
            return;
        }

        CompoundTag strange = tag.getCompound("Strange");

        List<Component> components = event.getToolTip();
        components.add(TextComponent.EMPTY);

        if (Stranges.isWeapon(stack.getItem())) {
            long kills = strange.getLong("kills");
            components.add((new TextComponent(String.format("%s - Kills: %d", Stranges.rank(kills), kills)).withStyle(ChatFormatting.DARK_GRAY)));

            if (strange.contains(Stranges.KEY_DAMAGE)) {
                double damage = strange.getDouble(Stranges.KEY_DAMAGE);
                components.add((new TextComponent(String.format("Damage Dealt: %.2f", damage)).withStyle(ChatFormatting.DARK_GRAY)));
            }

        } else if (Stranges.isTool(stack.getItem())) {
            long blocks = strange.getLong(Stranges.KEY_BLOCKS);
            components.add((new TextComponent(String.format("%s - Blocks Broken: %d", Stranges.rank(blocks), blocks)).withStyle(ChatFormatting.DARK_GRAY)));
        } else if (Stranges.isArmor(stack.getItem())) {
            long hits = strange.getLong("hits");
            components.add((new TextComponent(String.format("%s - Hits Taken: %d", Stranges.rank(hits), hits)).withStyle(ChatFormatting.DARK_GRAY)));
        }

    }

    @SubscribeEvent
    void onKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ItemStack item = player.getMainHandItem();
            CompoundTag tag = item.getTag();

            if (!Stranges.isWeapon(item.getItem()) || tag == null || !tag.contains("Strange")) {
                return;
            }

            CompoundTag strange = tag.getCompound("Strange");

            long kills = strange.getLong("kills");
            strange.putLong("kills", kills + 1);

            tag.put("Strange", strange);
        }
    }

    @SubscribeEvent
    void onDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ItemStack stack = player.getMainHandItem();
            CompoundTag tag = stack.getTag();

            if (!Stranges.isWeapon(stack.getItem()) || tag == null || !tag.contains("Strange")) {
                return;
            }

            CompoundTag strange = tag.getCompound("Strange");
            
            if (!strange.contains(Stranges.KEY_DAMAGE)) {
                return;
            }

            float dealt = Math.min(event.getEntityLiving().getHealth(), event.getAmount());
            double damage = strange.getDouble(Stranges.KEY_DAMAGE);

            strange.putDouble(Stranges.KEY_DAMAGE, damage + dealt);
            tag.put(Stranges.KEY_DAMAGE, strange);
        } else if (event.getEntityLiving() instanceof Player player) {
            for (ItemStack armor: player.getArmorSlots()) {
                CompoundTag tag = armor.getTag();

                if (!(armor.getItem() instanceof ArmorItem) || tag == null) {
                    return;
                }

                CompoundTag strange = tag.getCompound("Strange");

                long hits = strange.getLong("hits");
                strange.putLong("hits", hits + 1);

                tag.put("Strange", strange);
            }
        }

    }

    @SubscribeEvent
    void onMine(BlockEvent.BreakEvent event) {
        ItemStack stack = event.getPlayer().getMainHandItem();
        CompoundTag tag = stack.getTag();

        if (tag == null || !Stranges.isTool(stack.getItem()) || !tag.contains("Strange")) {
            return;
        }

        CompoundTag strange = tag.getCompound("Strange");

        long blocks = strange.getLong(Stranges.KEY_BLOCKS);
        strange.putLong(Stranges.KEY_BLOCKS, blocks + 1);
    }

}
