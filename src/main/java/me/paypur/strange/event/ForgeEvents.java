package me.paypur.strange.event;

import me.paypur.strange.Strange;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
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

        // Order does matter as some items have multiple primary tags
        if (stack.is(Strange.WEAPONS)) {
            long kills = strange.getLong(Strange.NBT_KILLS);
            components.add((new TextComponent(String.format("%s - Kills: %d", Strange.rank(kills), kills)).withStyle(ChatFormatting.DARK_GRAY)));
        } else if (stack.is(Strange.TOOLS_TIERED)) {
            long blocks = strange.getLong(Strange.NBT_BLOCKS_BROKEN);
            components.add((new TextComponent(String.format("%s - Blocks Broken: %d", Strange.rank(blocks), blocks)).withStyle(ChatFormatting.DARK_GRAY)));
        } else if (stack.is(Strange.DEFENSE)) {
            long hits = strange.getLong(Strange.NBT_HITS_TAKEN);
            components.add((new TextComponent(String.format("%s - Hits Taken: %d", Strange.rank(hits), hits)).withStyle(ChatFormatting.DARK_GRAY)));
        } else {
            long used = strange.getLong(Strange.NBT_TIMES_USED);
            components.add((new TextComponent(String.format("%s - Times Used: %d", Strange.rank(used), used)).withStyle(ChatFormatting.DARK_GRAY)));
        }

        if (strange.contains(Strange.NBT_DAMAGE_DEALT)) {
            double damage = strange.getDouble(Strange.NBT_DAMAGE_DEALT);
            components.add((new TextComponent(String.format("Damage Dealt: %.2f", damage)).withStyle(ChatFormatting.DARK_GRAY)));
        }

//        if (strange.contains(Strange.NBT_CRITICAL_KIllS)) {
//            long critKills = strange.getLong(Strange.NBT_CRITICAL_KIllS);
//            components.add((new TextComponent(String.format("Critical Kills: %d", critKills)).withStyle(ChatFormatting.DARK_GRAY)));
//        }

        if (strange.contains(Strange.NBT_ORES_BROKEN)) {
            long ores = strange.getLong(Strange.NBT_ORES_BROKEN);
            components.add((new TextComponent(String.format("Ores Broken: %d", ores)).withStyle(ChatFormatting.DARK_GRAY)));
        }

        if (strange.contains(Strange.NBT_TIMES_USED)) {
            long l = strange.getLong(Strange.NBT_TIMES_USED);
            components.add((new TextComponent(String.format("Times Used: %d", l)).withStyle(ChatFormatting.DARK_GRAY)));
        }

    }

    @SubscribeEvent
    void onKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ItemStack stack = player.getMainHandItem();
            CompoundTag tag = stack.getTag();

            if (!stack.is(Strange.WEAPONS) || tag == null || !tag.contains(Strange.MOD_ID)) {
                return;
            }

            CompoundTag strange = tag.getCompound(Strange.MOD_ID);

            long kills = strange.getLong(Strange.NBT_KILLS);
            strange.putLong(Strange.NBT_KILLS, kills + 1);

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
    // TODO: maybe use AttackEntityEvent instead
    void onDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ItemStack stack = player.getMainHandItem();
            CompoundTag tag = stack.getTag();

            if (!stack.is(Strange.WEAPONS) || tag == null || !tag.contains(Strange.MOD_ID)) {
                return;
            }

            CompoundTag strange = tag.getCompound(Strange.MOD_ID);
            
            if (!strange.contains(Strange.NBT_DAMAGE_DEALT)) {
                return;
            }

            float dealt = Math.min(event.getEntityLiving().getHealth(), event.getAmount());
            double damage = strange.getDouble(Strange.NBT_DAMAGE_DEALT);

            strange.putDouble(Strange.NBT_DAMAGE_DEALT, damage + dealt);
        } else if (event.getEntityLiving() instanceof Player player) {
            for (ItemStack armor: player.getArmorSlots()) {
                CompoundTag tag = armor.getTag();

                if (!(armor.getItem() instanceof ArmorItem) || tag == null || !tag.contains(Strange.MOD_ID)) {
                    continue;
                }

                CompoundTag strange = tag.getCompound(Strange.MOD_ID);

                long hits = strange.getLong(Strange.NBT_HITS_TAKEN);
                strange.putLong(Strange.NBT_HITS_TAKEN, hits + 1);
            }
        }

    }

    @SubscribeEvent
    void onMine(BlockEvent.BreakEvent event) {
        ItemStack stack = event.getPlayer().getMainHandItem();
        CompoundTag tag = stack.getTag();

        if (tag == null || !stack.is(Strange.TOOLS_TIERED) || !tag.contains(Strange.MOD_ID)) {
            return;
        }

        CompoundTag strange = tag.getCompound(Strange.MOD_ID);

        long blocks = strange.getLong(Strange.NBT_BLOCKS_BROKEN);
        strange.putLong(Strange.NBT_BLOCKS_BROKEN, blocks + 1);

        if (event.getState().getBlock() instanceof OreBlock) {
            long ores = strange.getLong(Strange.NBT_ORES_BROKEN);
            strange.putLong(Strange.NBT_ORES_BROKEN, ores + 1);
        }
    }

    @SubscribeEvent
    void onBucket(FillBucketEvent event) {
        if (event.getTarget().getType() != HitResult.Type.MISS) {
            ItemStack stack = event.getEmptyBucket();
            CompoundTag tag = stack.getTag();

            if (tag == null || !stack.is(Strange.TOOLS_TIERED) || !tag.contains(Strange.MOD_ID)) {
                return;
            }

            CompoundTag strange = tag.getCompound(Strange.MOD_ID);

            long used = strange.getLong(Strange.NBT_TIMES_USED);
            strange.putLong(Strange.NBT_TIMES_USED, used + 1);
        }
    }

}
