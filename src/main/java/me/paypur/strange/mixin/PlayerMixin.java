package me.paypur.strange.mixin;

import me.paypur.strange.ILastAttackCrit;
import me.paypur.strange.Strange;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Player.class)
public abstract class PlayerMixin implements ILastAttackCrit {

    /** Determine the original damage received before armor and resistances
     * <p>
     * Used by {@link Strange#STRANGE_PART_DAMAGE_BLOCKED}
     */

    @Unique
    float strange$pOriginalDamageAmount;

    @Inject(method = "actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V", at = @At("HEAD"))
    void actuallyHurt(DamageSource pDamageSrc, float pDamageAmount, CallbackInfo ci) {
        strange$pOriginalDamageAmount = pDamageAmount;
    }

    @Inject(
        method = "actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V",
        at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraftforge/common/ForgeHooks;onLivingDamage(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/damagesource/DamageSource;F)F"
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    void actuallyHurt2(DamageSource pDamageSrc, float pDamageAmount, CallbackInfo ci, float f2) {
        if (f2 > 0 && !pDamageSrc.isBypassArmor()) {
            float reduction = strange$pOriginalDamageAmount - f2;

            for (ItemStack stack : ((Player) (Object) this).getArmorSlots()) {
                if (stack.getItem() instanceof ArmorItem armor) {
                    // This will assume damage reduction is linear, which it isn't but whatever close enough
                    // a player might have defense points from items that are not armor
                    Strange.STRANGE_PART_DAMAGE_BLOCKED.get().incrementTag(stack, reduction * armor.getDefense() / ((Player) (Object) this).getArmorValue());
                }
            }
        }
    }


    @Inject(
            method = "checkMovementStatistics(DDD)V",
            at = @At("HEAD")
    )
    void checkMovementStatistics(double pDistanceX, double pDistanceY, double pDistanceZ, CallbackInfo ci) {
        if (((Player) (Object) this).isFallFlying()) {
            double dist = Math.sqrt(pDistanceX * pDistanceX + pDistanceY * pDistanceY + pDistanceZ * pDistanceZ);
            for (ItemStack stack : ((Player) (Object) this).getArmorSlots()) {
//                if (stack.getItem() instanceof ElytraItem) {
                    Strange.STRANGE_PART_BLOCKS_FLOWN.get().incrementTag(stack, dist);
//                }
            }
        }
    }


    /** Determine if the last attack was a crit
     * <p>
     * Used by {@link Strange#STRANGE_PART_HITS_CRITICAL} and {@link Strange#STRANGE_PART_KILLS_CRITICAL}
     */

    @Unique
    boolean strange$isLastAttackCrit = false;

    @Override
    public boolean strange$getIsLastAttackCrit() {
        return strange$isLastAttackCrit;
    }

    @Redirect(
            method = "attack(Lnet/minecraft/world/entity/Entity;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraftforge/common/ForgeHooks;getCriticalHit(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;ZF)Lnet/minecraftforge/event/entity/player/CriticalHitEvent;"
            )
    )
    CriticalHitEvent attack(Player player, Entity target, boolean vanillaCritical, float damageModifier) {
        CriticalHitEvent hitResult = ForgeHooks.getCriticalHit(((Player) (Object) this), target, vanillaCritical, vanillaCritical ? 1.5F : 1.0F);
        strange$isLastAttackCrit = hitResult != null;
        return hitResult;
    }

}
