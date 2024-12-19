package me.paypur.strange.mixin;

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

//        if (add.equals(Strange.STRANGIFIER.get())) {
//            // TODO
//
//        } if (add instanceof StrangePart part) {
//            part.createTag(stack, cir);
//        }

    }

}
