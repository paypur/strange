package me.paypur.stranges.event;

import me.paypur.stranges.Stranges;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
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
            ListTag e = tag != null ? tag.getList("Strange", 10) : new ListTag();

            long kills = ((LongTag) e.getCompound(0).get("kills")).getAsLong();

            List<Component> x = event.getToolTip();
            x.add(TextComponent.EMPTY);
            x.add((new TextComponent(String.format("%s - Kills: %d", Stranges.rank(kills), kills)).withStyle(ChatFormatting.GRAY)));

//            for(int i = 0; i < e.size(); ++i) {
//            }

        }

    }

    @SubscribeEvent
    void attack(AttackEntityEvent event) {
        if (event.getTarget().isAlive()) {
            Minecraft.getInstance().player.chat("ded");
        }
    }

}
