package me.paypur.strange.data;

import me.paypur.strange.Strange;
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
        // TODO: maybe should save these results as they are used twice
        ForgeRegistries.ITEMS.getValues().stream()
                .filter(ItemTypeUtil::isStrangifiable)
                .forEach(item -> strangify(pFinishedRecipeConsumer, item));

        ForgeRegistries.ITEMS.getValues().stream()
                .filter(ItemTypeUtil::isWeapon)
                .forEach(item -> strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_DAMAGE_DEALT.get()));

        ForgeRegistries.ITEMS.getValues().stream()
                .filter(Item::canBeDepleted)
                .forEach(item -> strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_TIMES_USED.get()));

        ForgeRegistries.ITEMS.getValues().stream()
                .filter(ItemTypeUtil::isToolTiered)
                .forEach(item -> strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_ORES_BROKEN.get()));
    }

    private void strangify(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item item) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(item), Ingredient.of(Strange.STRANGIFIER.get()), item)
                .unlocks("has_strangifier", has(Strange.STRANGIFIER.get()))
                .save(pFinishedRecipeConsumer, new ResourceLocation(Strange.MOD_ID, "smithing/strangify_" + item));
    }

    private void strangePart(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item item, Item part) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(item), Ingredient.of(part), item)
                .unlocks("has_" + part, has(part))
                .save(pFinishedRecipeConsumer, new ResourceLocation(Strange.MOD_ID, "smithing/" + part + "_" + item));
    }

    @Override
    public @NotNull String getName() {
        return "Strange recipes";
    }
}
