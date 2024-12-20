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

public class StrangePartLong extends StrangePart {

    public StrangePartLong(String nbtKey, TagKey<Item> tagKey) {
        super(nbtKey, tagKey);
    }

    @Override
    public void appendComponent(ItemStack stack, List<Component> components) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(Strange.MOD_ID)) {
            components.add(new TextComponent("    " + ForgeI18n.getPattern(TRANSLATION_PREFIX + NBT_KEY) +
                tag.getCompound(Strange.MOD_ID).getLong(NBT_KEY))
                .withStyle(ChatFormatting.DARK_GRAY));
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

        strange.putLong(NBT_KEY, 0);

        cir.setReturnValue(stack);
    }

    @Override
    public void incrementTag(ItemStack stack, Number num) {
        CompoundTag tag = stack.getTag();
        if (tag != null && stack.is(TAG_KEY) && tag.contains(Strange.MOD_ID)) {
            CompoundTag strange = tag.getCompound(Strange.MOD_ID);
            if (strange.contains(NBT_KEY)) {
                strange.putLong(NBT_KEY, strange.getLong(NBT_KEY) + num.longValue());
            }
        }
    }

}
