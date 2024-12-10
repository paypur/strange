package me.paypur.strange.items;

import me.paypur.strange.Strange;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class StrangePartDouble extends StrangePart {

    public StrangePartDouble(String nbtKey, String descKey, TagKey<Item> tagKey) {
        super(nbtKey, descKey, tagKey);
    }

    @Override
    public Component readTag(CompoundTag strange) {
        return new TextComponent(String.format("%s%.2f", DESC_KEY, strange.getDouble(NBT_KEY)))
                .withStyle(ChatFormatting.DARK_GRAY);
    }

    @Override
    public void createTag(ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        CompoundTag tag = stack.getTag();

        if (tag == null || !tag.contains(Strange.MOD_ID)) {
            cir.setReturnValue(ItemStack.EMPTY);
            return;
        }

        CompoundTag strange = tag.getCompound(Strange.MOD_ID);

        if (strange.contains(NBT_KEY)) {
            cir.setReturnValue(ItemStack.EMPTY);
            return;
        }

        strange.putDouble(NBT_KEY, 0);

        cir.setReturnValue(stack);
    }

    @Override
    public void incrementTag(ItemStack stack, Number num) {
        CompoundTag tag = stack.getTag();

        if (tag == null || !stack.is(TAG_KEY) || !tag.contains(Strange.MOD_ID)) {
            return;
        }

        CompoundTag strange = tag.getCompound(Strange.MOD_ID);

        strange.putDouble(NBT_KEY, strange.getDouble(NBT_KEY) + num.doubleValue());
    }

}
