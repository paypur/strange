package me.paypur.strange.mixin;

import me.paypur.strange.Strange;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "hurt(ILjava/util/Random;Lnet/minecraft/server/level/ServerPlayer;)Z", at = @At("TAIL"))
    public void hurt(int pAmount, Random pRandom, ServerPlayer pUser, CallbackInfoReturnable<Boolean> cir) {
         CompoundTag tag = ((ItemStack) (Object) this).getTag();
         if (tag != null && tag.contains(Strange.MOD_ID)) {
            CompoundTag strange = tag.getCompound(Strange.MOD_ID);
            strange.putLong(Strange.NBT_TIMES_USED, strange.getLong(Strange.NBT_TIMES_USED) + 1);
         }
    }

}
