package me.paypur.strange;

import me.paypur.strange.event.ForgeEvents;
import me.paypur.strange.event.ModEvents;
import me.paypur.strange.item.StrangePart;
import me.paypur.strange.item.StrangePartDouble;
import me.paypur.strange.item.StrangePartLong;
import me.paypur.strange.item.Strangifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


import java.util.HashMap;

import static me.paypur.strange.Strange.MOD_ID;

@Mod(MOD_ID)
public class Strange {

    // The value here should match an entry in the META-INF/mods.toml file
    public static final String MOD_ID = "strange";

    public static final int COLOR = 0xCF6A32;

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final TagKey<Item> DEFENSE = ItemTags.create(new ResourceLocation(MOD_ID, "defense"));
    public static final TagKey<Item> DEFENSE_ARMOR = ItemTags.create(new ResourceLocation(MOD_ID, "defense/armor"));
    public static final TagKey<Item> DEFENSE_SHIELD = ItemTags.create(new ResourceLocation(MOD_ID, "defense/shield"));
    public static final TagKey<Item> ELYTRA = ItemTags.create(new ResourceLocation(MOD_ID, "elytra"));

    public static final TagKey<Item> WEAPONS = ItemTags.create(new ResourceLocation(MOD_ID, "weapons"));
    public static final TagKey<Item> WEAPONS_MELEE = ItemTags.create(new ResourceLocation(MOD_ID, "weapons/melee"));
    public static final TagKey<Item> WEAPONS_RANGED = ItemTags.create(new ResourceLocation(MOD_ID, "weapons/ranged"));

    public static final TagKey<Item> TOOLS = ItemTags.create(new ResourceLocation(MOD_ID, "tools"));
    public static final TagKey<Item> TOOLS_TIERED = ItemTags.create(new ResourceLocation(MOD_ID, "tools/tiered"));
    public static final TagKey<Item> TOOLS_AXE = ItemTags.create(new ResourceLocation(MOD_ID, "tools/axe"));
    public static final TagKey<Item> TOOLS_HOE = ItemTags.create(new ResourceLocation(MOD_ID, "tools/hoe"));

    public static final TagKey<Item> DAMAGEABLE = ItemTags.create(new ResourceLocation(MOD_ID, "damageable"));


    public static final HashMap<String, StrangePart> STRANGE_PART_MAP = new HashMap<>();
    public static final RegistryObject<Strangifier> STRANGIFIER = ITEMS.register("strangifier", Strangifier::new);
    // weapons
    // TODO: tech limitations for now
//    public static final RegistryObject<Item> STRANGE_PART_CRITICAL_KILLS = ITEMS.register("strange_part_critical_kills", ITEM_SUPPLIER);

    // TODO: steal some stuff from the statistics tab

//    public static final RegistryObject<StrangePart> STRANGE_PART_ACCURACY = ITEMS.register("strange_part_accuracy", () -> new StrangePartDouble("accuracy", WEAPONS));

    public static final RegistryObject<StrangePart> STRANGE_PART_KILLS = ITEMS.register("strange_part_kills", () -> new StrangePartLong("kills", WEAPONS));
    public static final RegistryObject<StrangePart> STRANGE_PART_KILLS_AIRBORNE = ITEMS.register("strange_part_kills_airborne", () -> new StrangePartLong("kills_airborne", WEAPONS_RANGED));
    public static final RegistryObject<StrangePart> STRANGE_PART_KILLS_UNDERWATER = ITEMS.register("strange_part_kills_underwater", () -> new StrangePartLong("kills_underwater", WEAPONS));
    public static final RegistryObject<StrangePart> STRANGE_PART_KILLS_MOBS = ITEMS.register("strange_part_kills_mobs", () -> new StrangePartLong("kills_mobs", WEAPONS));
    public static final RegistryObject<StrangePart> STRANGE_PART_KILLS_PLAYERS = ITEMS.register("strange_part_kills_players", () -> new StrangePartLong("kills_players", WEAPONS));
//    public static final RegistryObject<StrangePart> STRANGE_PART_KILLS_ONE_SHOT = ITEMS.register("strange_part_kills_one_shot", () -> new StrangePartLong("kills_one_shot", WEAPONS));
    // TODO: bosses killed, how to differentiate a boss?
//    public static final RegistryObject<StrangePart> STRANGE_PART_KILLS_BOSSES = ITEMS.register("strange_part_kills_bosses", () -> new StrangePartLong("kills_bosses", WEAPONS));
    // TODO: maybe also do every vanilla mob

    public static final RegistryObject<StrangePart> STRANGE_PART_DAMAGE_DEALT = ITEMS.register("strange_part_damage_dealt", () -> new StrangePartDouble("damage_dealt", WEAPONS));

    // tools
    public static final RegistryObject<StrangePart> STRANGE_PART_BLOCKS_BROKEN = ITEMS.register("strange_part_blocks_broken", () -> new StrangePartLong("blocks_broken", TOOLS_TIERED));
    public static final RegistryObject<StrangePart> STRANGE_PART_ORES_BROKEN = ITEMS.register("strange_part_ores_broken", () -> new StrangePartLong("ores_broken", TOOLS_TIERED));
//    public static final RegistryObject<StrangePart> STRANGE_PART_BLOCKS_TILLED = ITEMS.register("strange_part_blocks_tilled", () -> new StrangePartLong("blocks_tilled", TOOLS_HOE));

    // armor
//    public static final RegistryObject<StrangePart> STRANGE_PART_DAMAGE_REDUCED = ITEMS.register("strange_part_damage_reduced", () -> new StrangePartDouble("damage_reduced", DEFENSE_ARMOR));
    public static final RegistryObject<StrangePart> STRANGE_PART_DAMAGE_BLOCKED = ITEMS.register("strange_part_damage_blocked", () -> new StrangePartDouble("damage_blocked", DEFENSE));
    public static final RegistryObject<StrangePart> STRANGE_PART_BLOCKS_FLOWN = ITEMS.register("strange_part_blocks_flown", () -> new StrangePartDouble("blocks_flown", ELYTRA));

    // general
    public static final RegistryObject<StrangePart> STRANGE_PART_DURABILITY_USED = ITEMS.register("strange_part_durability_used", () -> new StrangePartLong("durability_used", DAMAGEABLE));
    public static final RegistryObject<StrangePart> STRANGE_PART_TIMES_USED = ITEMS.register("strange_part_times_used", () -> new StrangePartLong("times_used", DAMAGEABLE));
//    public static final RegistryObject<StrangePart> STRANGE_PART_TIMES_REPAIRED = ITEMS.register("strange_part_times_repaired", () -> new StrangePartLong("times_repaired", DAMAGEABLE));
//    public static final RegistryObject<StrangePart> STRANGE_PART_TIMES_ENCHANTED = ITEMS.register("strange_part_times_enchanted", () -> new StrangePartLong("times_enchanted", DAMAGEABLE));

    public Strange() {
        IEventBus forge = MinecraftForge.EVENT_BUS;
        IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();

        forge.register(new ForgeEvents());
        mod.register(new ModEvents());

        ITEMS.register(mod);
    }

}
