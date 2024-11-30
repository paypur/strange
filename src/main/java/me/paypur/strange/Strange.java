package me.paypur.strange;

import me.paypur.strange.event.ForgeEvents;
import me.paypur.strange.event.ModEvents;
import net.minecraft.world.item.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


import static me.paypur.strange.Strange.MOD_ID;

@Mod(MOD_ID)
public class Strange {

    // The value here should match an entry in the META-INF/mods.toml file
    public static final String MOD_ID = "strange";

    public static final int COLOR = 0xCF6A32;

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> STRANGIFIER = ITEMS.register("strangifier", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> STRANGE_PART_DAMAGE_DEALT = ITEMS.register("strange_part_damage_dealt", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));


    // weapons
    public static final String TAG_DAMAGE_DEALT = "damage_dealt";
    public static final String TAG_KILLS = "kills";

    // tools
    public static final String TAG_BLOCKS_BROKEN = "blocks_broken";

    // armor
    public static final String TAG_HITS_TAKEN = "hits_taken";

    // misc

    public Strange() {
        IEventBus forge = MinecraftForge.EVENT_BUS;
        IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();

        forge.register(new ForgeEvents());
        mod.register(new ModEvents());

        ITEMS.register(mod);
    }

    // TODO: should also add
    // fishing rod
    // shield
    // bucket
    // flint and steel
    // shears
    // also custom item types like tinkers
    public static boolean isStrangifiable(Item item) {
        return isWeapon(item) || isTool(item) || isArmor(item) ||
                item instanceof ElytraItem ||
                item instanceof ShieldItem ||
                item instanceof FishingRodItem ||
                item instanceof ShearsItem ||
                item instanceof BucketItem ||
                item instanceof FlintAndSteelItem;
    }

    public static boolean isArmor(Item item) {
        return item instanceof ArmorItem;
    }

    public static boolean isWeapon(Item item) {
        return item instanceof SwordItem || item instanceof BowItem || item instanceof CrossbowItem || item instanceof TridentItem;
    }

    public static boolean isTool(Item item) {
        return item instanceof TieredItem && !(item instanceof SwordItem);
    }

    public static String rankAnnouncement(long n) {
        String pre = rank(n - 1);
        String post = rank(n);

        if (!pre.equals(post)) {
            // send chat message
        }

        return post;
    }

    // surely these a better way to do this
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
