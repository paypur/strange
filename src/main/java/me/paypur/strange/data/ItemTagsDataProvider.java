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
        this.tag(Strange.WEAPONS)
                .addTag(Strange.WEAPONS_MELEE)
                .addTag(Strange.WEAPONS_RANGED);

        final TagAppender<Item> WEAPONS_MELEE = this.tag(Strange.WEAPONS_MELEE);
        ForgeRegistries.ITEMS.getValues().stream()
                .filter(ItemTypeUtil::isWeaponMelee)
                .forEach(WEAPONS_MELEE::add);

        final TagAppender<Item> WEAPONS_RANGED = this.tag(Strange.WEAPONS_RANGED);
        ForgeRegistries.ITEMS.getValues().stream()
                .filter(ItemTypeUtil::isWeaponRanged)
                .forEach(WEAPONS_RANGED::add);

        this.tag(Strange.DEFENSE)
                .addTag(Strange.DEFENSE_ARMOR)
                .addTag(Strange.DEFENSE_SHIELD);

        final TagAppender<Item> DEFENSE_ARMOR = this.tag(Strange.DEFENSE_ARMOR);
        ForgeRegistries.ITEMS.getValues().stream()
                .filter(ItemTypeUtil::isArmor)
                .forEach(DEFENSE_ARMOR::add);

        final TagAppender<Item> DEFENSE_SHIELD = this.tag(Strange.DEFENSE_SHIELD);
        ForgeRegistries.ITEMS.getValues().stream()
                .filter(ItemTypeUtil::isShield)
                .forEach(DEFENSE_SHIELD::add);

        final TagAppender<Item> TOOLS_TIERED = this.tag(Strange.TOOLS_TIERED);
        ForgeRegistries.ITEMS.getValues().stream()
                .filter(ItemTypeUtil::isToolTiered)
                .forEach(TOOLS_TIERED::add);
    }

    @Override
    @Nonnull
    public String getName() {
        return "Strange item tags";
    }
}
