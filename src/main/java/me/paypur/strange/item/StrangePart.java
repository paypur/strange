package me.paypur.strange.item;

import me.paypur.strange.Strange;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;

public abstract class StrangePart extends Item {

    protected final String NBT_KEY;

    public StrangePart(@Nullable String nbtKey) {
        super(new Item.Properties().rarity(Rarity.EPIC).tab(CreativeModeTab.TAB_MISC));
        Strange.STRANGE_PART_MAP.put(nbtKey, this);
        this.NBT_KEY = nbtKey;
    }

    public abstract Number getValue(ItemStack stack);

    public String getNbtKey() {
        return NBT_KEY;
    }

    public abstract Component getComponent(ItemStack stack);

    public void appendComponent(ItemStack stack, List<Component> components) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(Strange.MOD_ID)) {
            components.add(new TextComponent("    ").append(getComponent(stack)));
        }
    }

    public abstract void createTag(ItemStack stack, CallbackInfoReturnable<ItemStack> cir);

    public void incrementTag(ItemStack stack) {
        incrementTag(stack, 1);
    }

    public abstract void incrementTag(ItemStack stack, Number num);

}
