package me.paypur.strange.item;

import me.paypur.strange.Strange;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.List;

public abstract class StrangePart extends Item {

    protected static final String TRANSLATION_PREFIX = "strange.part.";
    protected final TagKey<Item> TAG_KEY;
    protected final String NBT_KEY;

    public StrangePart(@Nullable String nbtKey, @Nullable TagKey<Item> tagKey) {
        super(new Item.Properties().rarity(Rarity.EPIC).tab(CreativeModeTab.TAB_MISC));
        Strange.STRANGE_PART_MAP.put(nbtKey, this);
        this.NBT_KEY = nbtKey;
        this.TAG_KEY = tagKey;
    }

    public abstract void appendComponent(ItemStack stack, List<Component> components);

    public abstract void createTag(ItemStack stack, CallbackInfoReturnable<ItemStack> cir);

    public void incrementTag(ItemStack stack) {
        incrementTag(stack, 1);
    }

    public abstract void incrementTag(ItemStack stack, Number num);

}
