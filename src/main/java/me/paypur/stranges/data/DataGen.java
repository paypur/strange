package me.paypur.stranges.data;

import me.paypur.stranges.Stranges;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.UpgradeRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static me.paypur.stranges.Stranges.MOD_ID;

public class DataGen extends RecipeProvider {

    static final ResourceLocation test = new ResourceLocation(MOD_ID, "test");

    public DataGen(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        UpgradeRecipeBuilder.smithing(Ingredient.of(Items.DIAMOND_SWORD), Ingredient.of(Stranges.STRANGIFIER.get()), Items.DIAMOND_SWORD)
                .unlocks("has_strangifier", has(Stranges.STRANGIFIER.get()))
                .save(pFinishedRecipeConsumer, test);
    }

    protected static InventoryChangeTrigger.TriggerInstance has(ItemLike pItemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pItemLike).build());
    }

    @Override
    public @NotNull String getName() {
        return "Stranges crafting recipes";
    }
}
