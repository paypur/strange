package me.paypur.strange;

import me.paypur.strange.event.ForgeEvents;
import me.paypur.strange.event.ModEvents;
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


import java.util.function.Supplier;

import static me.paypur.strange.Strange.MOD_ID;

@Mod(MOD_ID)
public class Strange {

    // The value here should match an entry in the META-INF/mods.toml file
    public static final String MOD_ID = "strange";

    public static final int COLOR = 0xCF6A32;

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    // TODO: same instance might cause problems
    private static final Supplier<? extends Item> ITEM_SUPPLIER = () -> new Item(new Item.Properties().rarity(Rarity.EPIC).tab(CreativeModeTab.TAB_MISC));

    public static final RegistryObject<Item> STRANGIFIER = ITEMS.register("strangifier", ITEM_SUPPLIER);

    // weapons
    // TODO: tech limitations for now
//    public static final RegistryObject<Item> STRANGE_PART_CRITICAL_KIllS = ITEMS.register("strange_part_critical_kills", ITEM_SUPPLIER);

    // TODO: not useable yet
    public static final RegistryObject<Item> STRANGE_PART_KILLS_AIRBORNE = ITEMS.register("strange_part_kills_airborne", ITEM_SUPPLIER);
    public static final RegistryObject<Item> STRANGE_PART_KILLS_UNDERWATER = ITEMS.register("strange_part_kills_underwater", ITEM_SUPPLIER);
    public static final RegistryObject<Item> STRANGE_PART_KILLS_PLAYERS = ITEMS.register("strange_part_kills_players", ITEM_SUPPLIER);
    public static final RegistryObject<Item> STRANGE_PART_KILLS_MOBS = ITEMS.register("strange_part_kills_mobs", ITEM_SUPPLIER);
    public static final RegistryObject<Item> STRANGE_PART_KILLS_ONE_SHOT = ITEMS.register("strange_part_kills_one_shot", ITEM_SUPPLIER);

    public static final RegistryObject<Item> STRANGE_PART_DAMAGE_DEALT = ITEMS.register("strange_part_damage_dealt", ITEM_SUPPLIER);

    // tools
    public static final RegistryObject<Item> STRANGE_PART_ORES_BROKEN = ITEMS.register("strange_part_ores_broken", ITEM_SUPPLIER);

    // armor
    // TODO: not useable yet
    public static final RegistryObject<Item> STRANGE_PART_DAMAGE_REDUCED = ITEMS.register("strange_part_damage_reduced", ITEM_SUPPLIER);

    // general
    public static final RegistryObject<Item> STRANGE_PART_DURABILITY_USED = ITEMS.register("strange_part_durability_used", ITEM_SUPPLIER);
    public static final RegistryObject<Item> STRANGE_PART_TIMES_USED = ITEMS.register("strange_part_times_used", ITEM_SUPPLIER);
    public static final RegistryObject<Item> STRANGE_PART_TIMES_REPAIRED = ITEMS.register("strange_part_times_repaired", ITEM_SUPPLIER);
    public static final RegistryObject<Item> STRANGE_PART_TIMES_ENCHANTED = ITEMS.register("strange_part_times_enchanted", ITEM_SUPPLIER);

    public static final TagKey<Item> DEFENSE = ItemTags.create(new ResourceLocation(MOD_ID, "defense"));
    public static final TagKey<Item> DEFENSE_ARMOR = ItemTags.create(new ResourceLocation(MOD_ID, "defense/armor"));
    public static final TagKey<Item> DEFENSE_SHIELD = ItemTags.create(new ResourceLocation(MOD_ID, "defense/shield"));

    public static final TagKey<Item> WEAPONS = ItemTags.create(new ResourceLocation(MOD_ID, "weapons"));
    public static final TagKey<Item> WEAPONS_MELEE = ItemTags.create(new ResourceLocation(MOD_ID, "weapons/melee"));
    public static final TagKey<Item> WEAPONS_RANGED = ItemTags.create(new ResourceLocation(MOD_ID, "weapons/ranged"));

    public static final TagKey<Item> TOOLS = ItemTags.create(new ResourceLocation(MOD_ID, "tools"));
    public static final TagKey<Item> TOOLS_TIERED = ItemTags.create(new ResourceLocation(MOD_ID, "tools_tiered"));

    public static final TagKey<Item> ITEM_DAMAGEABLE = ItemTags.create(new ResourceLocation(MOD_ID, "item_damageable"));

    // general
    public static final String NBT_DURABILITY_USED = "durability_used";
    public static final String NBT_TIMES_USED = "times_used";
    public static final String NBT_TIMES_REPAIRED = "times_repaired";

    // weapons
    public static final String NBT_KILLS = "kills";
//    public static final String NBT_CRITICAL_KIllS = "critical_hits";
    public static final String NBT_KILLS_AIRBORNE = "kills_airborne";
    public static final String NBT_KILLS_UNDERWATER = "kills_underwater";
    public static final String NBT_KILLS_PLAYERS = "kills_players";
    public static final String NBT_KILLS_MOBS = "kills_mobs";
    public static final String NBT_KILLS_ONE_SHOT = "kills_one_shot";

    public static final String NBT_DAMAGE_DEALT = "damage_dealt";

    // tools
    public static final String NBT_BLOCKS_BROKEN = "blocks_broken";
    public static final String NBT_ORES_BROKEN = "ores_broken";
    public static final String NBT_BLOCKS_TILLED = "blocks_tilled";

    // armor
    public static final String NBT_HITS_TAKEN = "hits_taken";
    public static final String NBT_DAMAGE_ABSORBED = "damage_absorbed";

    // misc

    public Strange() {
        IEventBus forge = MinecraftForge.EVENT_BUS;
        IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();

        forge.register(new ForgeEvents());
        mod.register(new ModEvents());

        ITEMS.register(mod);
    }

    // surely theres a better way to do this
    public static String rank(long n) {
        if (n < 10) {
            return "Strange";
        } else if (n < 25) {
            return "Unremarkable";
        } else if (n < 45) {
            return "Scarcely Lethal";
        } else if (n < 70) {
            return "Mildly Menacing";
        } else if (n < 100) {
            return "Somewhat Threatening";
        } else if (n < 135) {
            return "Uncharitable";
        } else if (n < 175) {
            return "Notably Dangerous";
        } else if (n < 225) {
            return "Sufficiently Lethal";
        } else if (n < 275) {
            return "Truly Feared";
        } else if (n < 350) {
            return "Spectacularly Lethal";
        } else if (n < 500) {
            return "Gore-Spattered";
        } else if (n < 750) {
            return "Wicked Nasty";
        } else if (n < 999) {
            return "Positively Inhumane";
        } else if (n < 1000) {
            return "Totally Ordinary";
        } else if (n < 1500) {
            return "Face-Melting";
        } else if (n < 2500) {
            return "Rage-Inducing";
        } else if (n < 5000) {
            return "Server-Clearing";
        } else if (n < 7500) {
            return "Epic";
        } else if (n < 7616) {
            return "Legendary";
        } else if (n < 8500) {
            return "Australian";
        } else {
            return "Hale's Own";
        }
    }

}
