package me.paypur.strange.mixin;

import me.paypur.strange.Strange;
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

            if (tag.contains(Strange.NBT_DAMAGE_DEALT)) {
                cir.setReturnValue(ItemStack.EMPTY);
                return;
            }

            strange.putLong(Strange.NBT_DAMAGE_DEALT, 0);
            tag.put(Strange.NBT_DAMAGE_DEALT, strange);

            cir.setReturnValue(stack);
        } else if (add.equals(Strange.STRANGE_PART_ORES_BROKEN.get())) {
            CompoundTag tag = stack.getTag();

            if (tag == null) {
                return;
            }

            if (!tag.contains(Strange.MOD_ID)) {
                cir.setReturnValue(ItemStack.EMPTY);
                return;
            }

            CompoundTag strange = tag.getCompound(Strange.MOD_ID);

            if (tag.contains(Strange.NBT_ORES_BROKEN)) {
                cir.setReturnValue(ItemStack.EMPTY);
                return;
            }

            strange.putLong(Strange.NBT_ORES_BROKEN, 0);
            tag.put(Strange.NBT_ORES_BROKEN, strange);

            cir.setReturnValue(stack);
        }  else if (add.equals(Strange.STRANGE_PART_TIMES_USED.get())) {
            CompoundTag tag = stack.getTag();

            if (tag == null) {
                return;
            }

            if (!tag.contains(Strange.MOD_ID)) {
                cir.setReturnValue(ItemStack.EMPTY);
                return;
            }

            CompoundTag strange = tag.getCompound(Strange.MOD_ID);

            if (tag.contains(Strange.NBT_TIMES_USED)) {
                cir.setReturnValue(ItemStack.EMPTY);
                return;
            }

            strange.putLong(Strange.NBT_TIMES_USED, 0);
            tag.put(Strange.NBT_TIMES_USED, strange);

            cir.setReturnValue(stack);
        }
    }

}
