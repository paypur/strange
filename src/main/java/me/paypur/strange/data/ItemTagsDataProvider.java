package me.paypur.strange.data;

import me.paypur.strange.Strange;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class ItemTagsDataProvider extends ItemTagsProvider {

    public ItemTagsDataProvider(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, new BlockTagsProvider(pGenerator, Strange.MOD_ID, existingFileHelper), Strange.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        final TagAppender<Item> weapons = this.tag(Strange.WEAPONS);
        final TagAppender<Item> tools = this.tag(Strange.TOOLS);

        for (Item item: ForgeRegistries.ITEMS.getValues()) {
            if (ItemTypeUtil.isWeapon(item)) {
                weapons.add(item);
            }
            if (ItemTypeUtil.isToolTiered(item)) {
                tools.add(item);
            }
        }
    }

    @Override
    @Nonnull
    public String getName() {
        return "Strange item tags";
    }
}
