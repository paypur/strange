package me.paypur.strange;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class StrangeCreativeTab {
    public static final CreativeModeTab TAB = new CreativeModeTab("strange") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return Strange.STRANGIFIER.get().getDefaultInstance();
        }
    };
}
