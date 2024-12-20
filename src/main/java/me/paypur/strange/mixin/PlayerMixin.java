package me.paypur.strange.mixin;

import me.paypur.strange.Strange;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Unique
    public float strange$pOriginalDamageAmount;

    @Inject(method = "actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V", at = @At("HEAD"))
    void actuallyHurt(DamageSource pDamageSrc, float pDamageAmount, CallbackInfo ci) {
        strange$pOriginalDamageAmount = pDamageAmount;
    }

    @Inject(method = "actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V",
        at = @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraftforge/common/ForgeHooks;onLivingDamage(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/damagesource/DamageSource;F)F"
        ),
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    void actuallyHurt2(DamageSource pDamageSrc, float pDamageAmount, CallbackInfo ci, float f2) {
        if (f2 > 0 && !pDamageSrc.isBypassArmor()) {
            float reduction = strange$pOriginalDamageAmount - f2;
            // a player might have defense from items that are not armor
            float totalDefense = 0;
            for (ItemStack stack : ((Player) (Object) this).getArmorSlots()) {
            if (stack.getItem() instanceof ArmorItem armor) {
                    totalDefense += armor.getDefense();
                }
            }

            for (ItemStack stack : ((Player) (Object) this).getArmorSlots()) {
                if (stack.getItem() instanceof ArmorItem armor) {
                    // This will assume damage reduction is linear, which it isn't but whatever close enough
                    Strange.STRANGE_PART_DAMAGE_BLOCKED.get().incrementTag(stack, reduction * armor.getDefense() / totalDefense);
                }
            }
        }
    }

}
