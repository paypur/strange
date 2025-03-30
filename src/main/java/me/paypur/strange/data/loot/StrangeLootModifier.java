package me.paypur.strange.data.loot;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import me.paypur.strange.Strange;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditions;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Predicate;

public class StrangeLootModifier implements IGlobalLootModifier {
    protected final LootItemCondition[] conditions;
    protected final Predicate<LootContext> orConditions;
    protected float chance;

    protected StrangeLootModifier(LootItemCondition[] conditionsIn, float chance) {
        this.conditions = conditionsIn;
        this.orConditions = LootItemConditions.orConditions(conditionsIn);
        this.chance = chance;
    }

    @Override
    @Nonnull
    public final List<ItemStack> apply(List<ItemStack> generatedLoot, LootContext context) {
        return this.orConditions.test(context) ? this.doApply(generatedLoot, context) : generatedLoot;
    }

    protected @NotNull List<ItemStack> doApply(List<ItemStack> list, LootContext lootContext) {
        if (lootContext.getRandom().nextFloat(1) <= chance) list.add(new ItemStack(Strange.STRANGIFIER.get()));
        return list;
    }

    public static class Serializer extends GlobalLootModifierSerializer<StrangeLootModifier> {
        @Override
        public StrangeLootModifier read(ResourceLocation resourceLocation, JsonObject jsonObject, LootItemCondition[] lootItemConditions) {
            final float chance = GsonHelper.getAsFloat(jsonObject, "chance");
            if (chance > 1) throw new JsonParseException("Unable to set chance to a number greater than 1");
            if (chance < 0) throw new JsonParseException("Unable to set chance to a number less than 0");
            return new StrangeLootModifier(lootItemConditions, chance);
        }

        @Override
        public JsonObject write(StrangeLootModifier strangeLootModifier) {
            final JsonObject jsonObject = this.makeConditions(strangeLootModifier.conditions);
            jsonObject.addProperty("chance", strangeLootModifier.chance);
            return jsonObject;
        }
    }

}
