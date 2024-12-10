package me.paypur.strange.mixin;

import me.paypur.strange.Strange;
import me.paypur.strange.items.StrangePart;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;


@Mixin(UpgradeRecipe.class)
public abstract class UpgradeRecipeMixin implements Recipe<Container> {

    @Inject(method = "assemble", at = @At(value = "RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    void strange(Container pInv, CallbackInfoReturnable<ItemStack> cir, ItemStack stack){
        Item add = pInv.getItem(1).getItem();

        if (add.equals(Strange.STRANGIFIER.get())) {
            if (stack.getTag() == null) {
                // TODO: storing the tag as a var causes problems if updated later
                stack.setTag(new CompoundTag());
            }

            if (stack.getTag().contains(Strange.MOD_ID)) {
                cir.setReturnValue(ItemStack.EMPTY);
                return;
            }

            stack.getTag().put(Strange.MOD_ID, new CompoundTag());

            stack.setHoverName(stack.getHoverName().copy().withStyle(s -> s.withColor(Strange.COLOR).withItalic(false)));

            cir.setReturnValue(stack);
        } else if (add instanceof StrangePart part) {
            part.createTag(stack, cir);
        }

    }

}
