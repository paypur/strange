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
public abstract class AnvilMenuMixin extends ItemCombinerMenu {

    public AnvilMenuMixin(@Nullable MenuType<?> pType, int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
        super(pType, pContainerId, pPlayerInventory, pAccess);
    }

    @Redirect(
            method = "Lnet/minecraft/world/inventory/AnvilMenu;createResult()V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;setHoverName(Lnet/minecraft/network/chat/Component;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    ItemStack setHoverName(ItemStack stack, Component pNameComponent) {
        assert stack.getTag() != null;
        if (stack.getTag().contains(Strange.MOD_ID)) {
            stack.setHoverName(pNameComponent.copy().withStyle(s -> s.withColor(Strange.COLOR)));
        } else {
            // vanilla ignores any existing style
            stack.setHoverName(pNameComponent);
        }
        return stack;
    }

}
