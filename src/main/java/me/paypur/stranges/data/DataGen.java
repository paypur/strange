package me.paypur.stranges.data;

import me.paypur.stranges.Stranges;
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

import static me.paypur.stranges.Stranges.MOD_ID;

public class DataGen extends RecipeProvider {

    public DataGen(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        ForgeRegistries.ITEMS.getValues().stream()
                .filter(Stranges::isStrangifiable)
                .forEach(item -> strangify(pFinishedRecipeConsumer, item));
    }

    private void strangify(Consumer<FinishedRecipe> pFinishedRecipeConsumer, Item item) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(item), Ingredient.of(Stranges.STRANGIFIER.get()), item)
                .unlocks("has_strangifier", has(Stranges.STRANGIFIER.get()))
                .save(pFinishedRecipeConsumer, new ResourceLocation(MOD_ID, "smithing/strangify_" + item));
    }


    @Override
    public @NotNull String getName() {
        return "Stranges crafting recipes";
    }
}
