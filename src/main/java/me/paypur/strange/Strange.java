package me.paypur.strange;

import me.paypur.strange.event.ForgeEvents;
import me.paypur.strange.event.ModEvents;
import me.paypur.strange.item.*;
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
    public static final TagKey<Item> TOOLS_BREAKING = ItemTags.create(new ResourceLocation(MOD_ID, "tools/breaking"));
    public static final TagKey<Item> TOOLS_PICKAXE = ItemTags.create(new ResourceLocation(MOD_ID, "tools/pickaxe"));
    public static final TagKey<Item> TOOLS_AXE = ItemTags.create(new ResourceLocation(MOD_ID, "tools/axe"));
    public static final TagKey<Item> TOOLS_SHOVEL = ItemTags.create(new ResourceLocation(MOD_ID, "tools/shovel"));
    public static final TagKey<Item> TOOLS_HOE = ItemTags.create(new ResourceLocation(MOD_ID, "tools/hoe"));

    public static final TagKey<Item> DAMAGEABLE = ItemTags.create(new ResourceLocation(MOD_ID, "damageable"));


    public static final HashMap<String, StrangePart> STRANGE_PART_MAP = new HashMap<>();

    public static final RegistryObject<Strangifier> STRANGIFIER = ITEMS.register("strangifier", Strangifier::new);

    // weapons
    // TODO: tech limitations for now

    // TODO: steal some stuff from the statistics tab
    public static final RegistryObject<StrangePart> STRANGE_PART_HITS = strangePartLong("hits", WEAPONS);
    public static final RegistryObject<StrangePart> STRANGE_PART_HITS_CRITICAL = strangePartLong("hits_critical", WEAPONS);
//    public static final RegistryObject<StrangePart> STRANGE_PART_HITS_AIRBORNE = strangePartLong("hits_airborne", WEAPONS_RANGED);
//    public static final RegistryObject<StrangePart> STRANGE_PART_HITS_UNDERWATER = strangePartLong("hits_underwater", WEAPONS_MELEE);
    public static final RegistryObject<StrangePart> STRANGE_PART_TIMES_FIRED = strangePartLong("times_fired", WEAPONS);

    public static final RegistryObject<StrangePart> STRANGE_PART_KILLS = strangePartLong("kills", WEAPONS);
    public static final RegistryObject<StrangePart> STRANGE_PART_KILLS_CRITICAL = strangePartLong("kills_critical", WEAPONS_MELEE);
    public static final RegistryObject<StrangePart> STRANGE_PART_KILLS_AIRBORNE = strangePartLong("kills_airborne", WEAPONS_RANGED);
    public static final RegistryObject<StrangePart> STRANGE_PART_KILLS_UNDERWATER = strangePartLong("kills_underwater", WEAPONS_MELEE);
    public static final RegistryObject<StrangePart> STRANGE_PART_KILLS_MOBS = strangePartLong("kills_mobs", WEAPONS);
    public static final RegistryObject<StrangePart> STRANGE_PART_KILLS_PLAYERS = strangePartLong("kills_players", WEAPONS);
//    public static final RegistryObject<StrangePart> STRANGE_PART_KILLS_ONE_SHOT = strangePartLong("kills_one_shot", WEAPONS));
    // TODO: bosses killed, how to differentiate a boss?
//    public static final RegistryObject<StrangePart> STRANGE_PART_KILLS_BOSSES = strangePartLong("kills_bosses", WEAPONS));
    // TODO: maybe also do every vanilla mob

    public static final RegistryObject<StrangePart> STRANGE_PART_DAMAGE_DEALT = strangePartDouble("damage_dealt", WEAPONS);

    // tools
    public static final RegistryObject<StrangePart> STRANGE_PART_BLOCKS_BROKEN = strangePartLong("blocks_broken", TOOLS);

    public static final RegistryObject<StrangePart> STRANGE_PART_ORES_BROKEN = strangePartLong("ores_broken", TOOLS_PICKAXE);
    public static final RegistryObject<StrangePart> STRANGE_PART_BLOCKS_STRIPPED = strangePartLong("blocks_stripped", TOOLS_AXE);
    public static final RegistryObject<StrangePart> STRANGE_PART_BLOCKS_TILLED = strangePartLong("blocks_tilled", TOOLS_HOE);
    public static final RegistryObject<StrangePart> STRANGE_PART_BLOCKS_PATHED = strangePartLong("blocks_pathed", TOOLS_SHOVEL);

    // armor

    // TODO: add damage taken (also specific damage types?)
    public static final RegistryObject<StrangePart> STRANGE_PART_DAMAGE_BLOCKED = strangePartDouble("damage_blocked", DEFENSE);
    public static final RegistryObject<StrangePart> STRANGE_PART_DAMAGE_TAKEN = strangePartDouble("damage_taken", DEFENSE);

    public static final RegistryObject<StrangePart> STRANGE_PART_BLOCKS_FLOWN = strangePartDouble("blocks_flown", ELYTRA);

    // general
    public static final RegistryObject<StrangePart> STRANGE_PART_DURABILITY_USED = strangePartLong("durability_used", DAMAGEABLE);
    public static final RegistryObject<StrangePart> STRANGE_PART_TIMES_USED = strangePartLong("times_used", DAMAGEABLE);
//    public static final RegistryObject<StrangePart> STRANGE_PART_TIMES_REPAIRED = strangePartLong("times_repaired", DAMAGEABLE));
//    public static final RegistryObject<StrangePart> STRANGE_PART_TIMES_ENCHANTED = strangePartLong("times_enchanted", DAMAGEABLE));

    public Strange() {
        IEventBus forge = MinecraftForge.EVENT_BUS;
        IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();

        forge.register(new ForgeEvents());
        mod.register(new ModEvents());

        ITEMS.register(mod);
    }

    private static RegistryObject<StrangePart> strangePartLong(String id, TagKey<Item> tag) {
        return ITEMS.register("strange_part_" + id, () -> new StrangePartLong(id, tag));
    }

    private static RegistryObject<StrangePart> strangePartDouble(String id, TagKey<Item> tag) {
        return ITEMS.register("strange_part_" + id, () -> new StrangePartDouble(id, tag));
    }
}
