package me.paypur.stranges.event;

import me.paypur.stranges.Stranges;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ForgeEvents {

    // note: runs every tick
    @SubscribeEvent
    void tooltip(ItemTooltipEvent event) {

        if (event.getItemStack().getItem().equals(Items.WOODEN_AXE)) {
            CompoundTag tag = event.getItemStack().getTag();

            // CompoundTag id is 10
            ListTag tags = tag != null ? tag.getList("Strange", 10) : new ListTag();

            long kills = ((LongTag) tags.getCompound(0).get("kills")).getAsLong();

            List<Component> components = event.getToolTip();
            components.add(TextComponent.EMPTY);
            components.add((new TextComponent(String.format("%s - Kills: %d", Stranges.rank(kills), kills)).withStyle(ChatFormatting.GRAY)));

        }

    }

    @SubscribeEvent
    void attack(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ItemStack item = player.getMainHandItem();

            CompoundTag tag = item.getTag();

            if (tag == null) {
                return;
            }

            if (tag.getList("Strange", 10).isEmpty()) {
                tag.put("Strange", new ListTag());
            }

            ListTag strange = tag.getList("Strange", 10);

            if (strange.isEmpty()) {
                CompoundTag killsTag = new CompoundTag();
                killsTag.putLong("kills", 0);
                strange.add(killsTag);
            }

            CompoundTag killsTag = (CompoundTag) strange.get(0);
            long kills = ((LongTag) killsTag.get("kills")).getAsLong();
            killsTag.putLong("kills", kills + 1);

            tag.put("Strange", strange);
        }
    }

}
