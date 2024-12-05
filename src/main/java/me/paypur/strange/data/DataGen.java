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

import static me.paypur.strange.Strange.MOD_ID;

public class DataGen extends RecipeProvider {

    public DataGen(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        ForgeRegistries.ITEMS.getValues().stream()
                .filter(Strange::isStrangifiable)
                .forEach(item -> strangify(pFinishedRecipeConsumer, item));

        ForgeRegistries.ITEMS.getValues().stream()
                .filter(Strange::isWeapon)
                .forEach(item -> strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_DAMAGE_DEALT.get()));

        ForgeRegistries.ITEMS.getValues().stream()
                .filter(Strange::isTool)
                .forEach(item -> strangePart(pFinishedRecipeConsumer, item, Strange.STRANGE_PART_ORES_BROKEN.get()));
    }

    private void strangify(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item item) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(item), Ingredient.of(Strange.STRANGIFIER.get()), item)
                .unlocks("has_strangifier", has(Strange.STRANGIFIER.get()))
                .save(pFinishedRecipeConsumer, new ResourceLocation(MOD_ID, "smithing/strangify_" + item));
    }

    private void strangePart(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item item, Item part) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(item), Ingredient.of(part), item)
                .unlocks("has_" + part, has(part))
                .save(pFinishedRecipeConsumer, new ResourceLocation(MOD_ID, "smithing/" + part + "_" + item));
    }

    @Override
    public @NotNull String getName() {
        return "Stranges crafting recipes";
    }
}
