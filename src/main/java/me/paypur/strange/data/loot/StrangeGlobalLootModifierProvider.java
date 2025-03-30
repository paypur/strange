package me.paypur.strange.data.loot;

import me.paypur.strange.Strange;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class StrangeGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLOBAL_LOOT_MODIFIER = DeferredRegister.create(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS, Strange.MOD_ID);
    private static final RegistryObject<StrangeLootModifier.Serializer> STRANGE_PARTS = GLOBAL_LOOT_MODIFIER.register("strange_parts", StrangeLootModifier.Serializer::new);

    public StrangeGlobalLootModifierProvider(DataGenerator gen) {
        super(gen, Strange.MOD_ID);
    }

    @Override
    protected void start() {
        add("strange_parts", STRANGE_PARTS.get(),
                new StrangeLootModifier(
                    new LootItemCondition[] {
                            LootTableIdCondition.builder(new ResourceLocation("chests/abandoned_mineshaft")).build(),
                            LootTableIdCondition.builder(new ResourceLocation("chests/buried_treasure")).build(),
                            LootTableIdCondition.builder(new ResourceLocation("chests/desert_pyramid")).build(),
                            LootTableIdCondition.builder(new ResourceLocation("chests/end_city_treasure")).build(),
                            LootTableIdCondition.builder(new ResourceLocation("chests/igloo_chest")).build(),
                            LootTableIdCondition.builder(new ResourceLocation("chests/jungle_temple")).build(),
                            LootTableIdCondition.builder(new ResourceLocation("chests/nether_bridge")).build(),
                            LootTableIdCondition.builder(new ResourceLocation("chests/pillager_outpost")).build(),
                            LootTableIdCondition.builder(new ResourceLocation("chests/shipwreck_map")).build(),
                            LootTableIdCondition.builder(new ResourceLocation("chests/shipwreck_supply")).build(),
                            LootTableIdCondition.builder(new ResourceLocation("chests/shipwreck_treasure")).build(),
                            LootTableIdCondition.builder(new ResourceLocation("chests/simple_dungeon")).build(),
                            LootTableIdCondition.builder(new ResourceLocation("chests/stronghold_corridor")).build(),
                            LootTableIdCondition.builder(new ResourceLocation("chests/stronghold_crossing")).build(),
                            LootTableIdCondition.builder(new ResourceLocation("chests/stronghold_library")).build(),
                            LootTableIdCondition.builder(new ResourceLocation("chests/underwater_ruin_big")).build(),
                            LootTableIdCondition.builder(new ResourceLocation("chests/underwater_ruin_small")).build(),
                            LootTableIdCondition.builder(new ResourceLocation("chests/woodland_mansion")).build()
                    },
                    0.2f
                )
        );
    }
}
