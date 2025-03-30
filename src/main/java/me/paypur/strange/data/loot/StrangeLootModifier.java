package me.paypur.strange.data.loot;

import com.google.gson.JsonObject;
import me.paypur.strange.Strange;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StrangeLootModifier extends LootModifier {
    protected StrangeLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull List<ItemStack> doApply(List<ItemStack> list, LootContext lootContext) {
        list.add(new ItemStack(Strange.STRANGIFIER.get()));
        return list;
    }

    public static class Serializer extends GlobalLootModifierSerializer<StrangeLootModifier> {
        @Override
        public StrangeLootModifier read(ResourceLocation resourceLocation, JsonObject jsonObject, LootItemCondition[] lootItemConditions) {
            return new StrangeLootModifier(lootItemConditions);
        }

        @Override
        public JsonObject write(StrangeLootModifier strangeLootModifier) {
            return makeConditions(strangeLootModifier.conditions);
        }
    }

}
