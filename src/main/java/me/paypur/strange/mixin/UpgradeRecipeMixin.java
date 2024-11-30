package me.paypur.strange.mixin;

import me.paypur.strange.Strange;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;


@Mixin(UpgradeRecipe.class)
public abstract class UpgradeRecipeMixin implements Recipe<Container> {

    @Shadow
    @Final
    Ingredient addition;

    @Inject(method = "assemble", at = @At(value = "RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    void strange(Container pInv, CallbackInfoReturnable<ItemStack> cir, ItemStack stack){
        ItemStack[] items = this.addition.getItems();
        if (items.length != 1) {
            return;
        }

        Item add = items[0].getItem();

        if (add.equals(Strange.STRANGIFIER.get())) {
            CompoundTag tag = stack.getTag();

            if (tag == null) {
                return;
            }

            if (tag.contains(Strange.MOD_ID)) {
                cir.setReturnValue(ItemStack.EMPTY);
                return;
            }

            tag.put(Strange.MOD_ID, new CompoundTag());

            stack.setHoverName(stack.getHoverName().copy().withStyle(s -> s.withColor(Strange.COLOR)));

            cir.setReturnValue(stack);
        } else if (add.equals(Strange.STRANGE_PART_DAMAGE_DEALT.get())) {
            CompoundTag tag = stack.getTag();

            if (tag == null) {
                return;
            }

            if (!tag.contains(Strange.MOD_ID)) {
                cir.setReturnValue(ItemStack.EMPTY);
                return;
            }

            CompoundTag strange = tag.getCompound(Strange.MOD_ID);

            if (tag.contains(Strange.TAG_KILLS)) {
                cir.setReturnValue(ItemStack.EMPTY);
                return;
            }

            strange.putLong(Strange.TAG_KILLS, 0);
            tag.put(Strange.TAG_KILLS, strange);

            cir.setReturnValue(stack);
        }

    }

}
