package me.paypur.strange.mixin;

import me.paypur.strange.Strange;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin {

    @Redirect(
            method = "createResult()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;setHoverName(Lnet/minecraft/network/chat/Component;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    ItemStack setHoverName(ItemStack stack, Component pNameComponent) {
        if (stack.getTag() != null && stack.getTag().contains(Strange.MOD_ID)) {
            pNameComponent = pNameComponent.copy().withStyle(s -> s.withColor(Strange.COLOR));
        }
        // vanilla ignores any existing style
        stack.setHoverName(pNameComponent);
        return stack;
    }

}
