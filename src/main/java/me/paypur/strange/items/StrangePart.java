package me.paypur.strange.items;

import me.paypur.strange.Strange;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public abstract class StrangePart extends Item {

    protected final String NBT_KEY;
    // TODO: replace with translation key
    protected final String DESC_KEY;
    protected final TagKey<Item> TAG_KEY;

    public StrangePart(String nbtKey, String descKey, TagKey<Item> tagKey) {
        super(new Item.Properties().rarity(Rarity.EPIC).tab(CreativeModeTab.TAB_MISC));
        Strange.STRANGE_PART_MAP.put(nbtKey, this);
        this.NBT_KEY = nbtKey;
        this.DESC_KEY = descKey;
        this.TAG_KEY = tagKey;
    }

    public abstract Component readTag(CompoundTag strange);

    public abstract void createTag(ItemStack stack, CallbackInfoReturnable<ItemStack> cir);

    public void incrementTag(ItemStack stack) {
        incrementTag(stack, 1);
    }

    public abstract void incrementTag(ItemStack stack, Number num);

}
