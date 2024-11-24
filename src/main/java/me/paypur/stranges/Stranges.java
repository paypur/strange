package me.paypur.stranges;

import me.paypur.stranges.event.ForgeEvents;
import me.paypur.stranges.event.ModEvents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


import static me.paypur.stranges.Stranges.MOD_ID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MOD_ID)
public class Stranges {

    public static final String MOD_ID = "stranges";

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final RegistryObject<Item> STRANGIFIER = ITEMS.register("strangifier", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public Stranges() {
        IEventBus forge = MinecraftForge.EVENT_BUS;
        IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();

        forge.register(new ForgeEvents());
        mod.register(new ModEvents());

        ITEMS.register(mod);
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
