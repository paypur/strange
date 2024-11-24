package me.paypur.stranges.mixin;

import me.paypur.stranges.Stranges;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
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

    @Inject(method = "assemble", at = @At(value = "RETURN"), locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
    void thing(Container pInv, CallbackInfoReturnable<ItemStack> cir, ItemStack itemStack){
        ItemStack[] items = this.addition.getItems();
        if (items.length >= 1 && items[0].getItem().equals(Stranges.STRANGIFIER.get())) {
            CompoundTag tag = itemStack.getTag();

            if (tag == null) {
                return;
            }

            if (tag.contains("Strange")) {
                cir.cancel();
            }

            tag.put("Strange", new CompoundTag());

            String name = itemStack.getHoverName().getString();
            CompoundTag display = tag.getCompound("display");

            display.putString("Name", String.format("{\"text\":\"%s\",\"color\":\"#CF6A32\",\"italic\":false}", name));

            tag.put("display", display);

            cir.setReturnValue(itemStack);
        }
    }

}
