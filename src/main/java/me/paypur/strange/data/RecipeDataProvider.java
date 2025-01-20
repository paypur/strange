package me.paypur.strange.data;

import me.paypur.strange.Strange;
import me.paypur.strange.item.StrangePart;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class RecipeDataProvider extends RecipeProvider {

    public RecipeDataProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        ForgeRegistries.ITEMS.getValues().stream()
                .filter(ItemTypeUtil::isStrangifiable)
                .forEach(item -> {
                    strangify(pFinishedRecipeConsumer, item);
                    // every strange should have durability
                    strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_DURABILITY_USED.get());

                    if (ItemTypeUtil.isWeapon(item)) {
                        strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_DAMAGE_DEALT.get());
                        strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_KILLS.get());
                        strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_KILLS_PLAYERS.get());
                        strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_KILLS_MOBS.get());
                        strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_HITS.get());
                        strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_TIMES_FIRED.get());
    //                    strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_KILLS_ONE_SHOT.get());
                    }

                    if (ItemTypeUtil.isWeaponMelee(item)) {
                        strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_HITS_CRITICAL.get());
                        strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_KILLS_CRITICAL.get());
                        strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_KILLS_UNDERWATER.get());
                    }

                    if (ItemTypeUtil.isWeaponRanged(item)) {
                        strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_KILLS_AIRBORNE.get());
                    }

                    if (ItemTypeUtil.isDefense(item)) {
                        strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_DAMAGE_BLOCKED.get());
                    }

                    if (item instanceof ElytraItem) {
                        strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_BLOCKS_FLOWN.get());
                    }

                    if (ItemTypeUtil.isToolTiered(item)) {
                        strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_BLOCKS_BROKEN.get());
                    }

                    if (item instanceof PickaxeItem) {
                        strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_ORES_BROKEN.get());
                    }

                    if (item instanceof AxeItem) {
                        strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_BLOCKS_STRIPPED.get());
                        strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_BLOCKS_SCRAPED.get());
                        strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_BLOCKS_DEWAXED.get());
                    }

                    if (item instanceof ShovelItem) {
                        strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_BLOCKS_PATHED.get());
                    }

                    if (item instanceof HoeItem) {
                        strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_BLOCKS_TILLED.get());
                    }
                });

//        ForgeRegistries.ITEMS.getValues().stream()
//                .filter(Item::canBeDepleted)
//                .forEach(item -> strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_DURABILITY_USED.get()));
    }

    private void strangify(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item item) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(item), Ingredient.of(Strange.STRANGIFIER.get()), item)
                .unlocks("has_strangifier", has(Strange.STRANGIFIER.get()))
                .save(pFinishedRecipeConsumer, new ResourceLocation(Strange.MOD_ID, "smithing/strangify_" + item));
    }

    private void strangePart(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item item, StrangePart part) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(item), Ingredient.of(part), item)
                .unlocks("has_" + part, has(part))
                .save(pFinishedRecipeConsumer, new ResourceLocation(Strange.MOD_ID, "smithing/" + part + "_" + item));
    }

    @Override
    public @NotNull String getName() {
        return "Strange recipes";
    }
}
