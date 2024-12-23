package me.paypur.strange.item;

import me.paypur.strange.Strange;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeI18n;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

public class StrangePartDouble extends StrangePart {

    public StrangePartDouble(String nbtKey, TagKey<Item> tagKey) {
        super(nbtKey, tagKey);
    }

    @Override
    public Number getValue(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(Strange.MOD_ID)) {
            return tag.getCompound(Strange.MOD_ID).getDouble(NBT_KEY);
        }
        return 0;
    }

    @Override
    public Component getComponent(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(Strange.MOD_ID) && tag.getCompound(Strange.MOD_ID).contains(NBT_KEY)) {
            return new TextComponent(ForgeI18n.getPattern(getDescriptionId() + ".short") +
                    String.format("%.2f", tag.getCompound(Strange.MOD_ID).getDouble(NBT_KEY)))
                    .withStyle(ChatFormatting.DARK_GRAY);
        } else {
            return TextComponent.EMPTY;
        }
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
        if (tag != null && tag.contains(Strange.MOD_ID)) {
            CompoundTag strange = tag.getCompound(Strange.MOD_ID);
            if (strange.contains(NBT_KEY)) {
                strange.putDouble(NBT_KEY, strange.getDouble(NBT_KEY) + num.doubleValue());
            }
        }
    }

}
