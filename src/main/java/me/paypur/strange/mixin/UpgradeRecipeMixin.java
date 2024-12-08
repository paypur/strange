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
            return;
        }

        if (add.equals(Strange.STRANGE_PART_DAMAGE_DEALT.get())) {
            addDouble(stack, Strange.NBT_DAMAGE_DEALT, cir);
//        } else if (add.equals(Strange.STRANGE_PART_CRITICAL_KIllS.get())) {
//            addLong(stack, Strange.NBT_CRITICAL_KIllS, cir);
        } else if (add.equals(Strange.STRANGE_PART_ORES_BROKEN.get())) {
            addLong(stack, Strange.NBT_ORES_BROKEN, cir);
        }  else if (add.equals(Strange.STRANGE_PART_TIMES_USED.get())) {
            addLong(stack, Strange.NBT_TIMES_USED, cir);
        } else if (add.equals(Strange.STRANGE_PART_DURABILITY_USED.get())) {
            addLong(stack, Strange.NBT_DURABILITY_USED, cir);
        }
    }

    private void addLong(ItemStack stack, String nbt, CallbackInfoReturnable cir) {
        CompoundTag tag = stack.getTag();

        if (tag == null || !tag.contains(Strange.MOD_ID)) {
            cir.setReturnValue(ItemStack.EMPTY);
            return;
        }

        CompoundTag strange = tag.getCompound(Strange.MOD_ID);

        if (strange.contains(nbt)) {
            cir.setReturnValue(ItemStack.EMPTY);
            return;
        }

        strange.putLong(nbt, 0);

        cir.setReturnValue(stack);
    }

    private void addDouble(ItemStack stack, String nbt, CallbackInfoReturnable cir) {
        CompoundTag tag = stack.getTag();

        if (tag == null || !tag.contains(Strange.MOD_ID)) {
            cir.setReturnValue(ItemStack.EMPTY);
            return;
        }

        CompoundTag strange = tag.getCompound(Strange.MOD_ID);

        if (strange.contains(nbt)) {
            cir.setReturnValue(ItemStack.EMPTY);
            return;
        }

        strange.putDouble(nbt, 0);

        cir.setReturnValue(stack);
    }
}
