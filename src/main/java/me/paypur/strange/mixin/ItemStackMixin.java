package me.paypur.strange.mixin;

import me.paypur.strange.Strange;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "hurt(ILjava/util/Random;Lnet/minecraft/server/level/ServerPlayer;)Z", at = @At("RETURN"))
    public void hurt(int pAmount, Random pRandom, ServerPlayer pUser, CallbackInfoReturnable<Boolean> cir) {
         Strange.STRANGE_PART_TIMES_USED.get().incrementTag((ItemStack) (Object) this);
         Strange.STRANGE_PART_DURABILITY_USED.get().incrementTag((ItemStack) (Object) this, pAmount);
    }

}
