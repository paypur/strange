package me.paypur.strange.mixin;

import me.paypur.strange.Strange;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(AxeItem.class)
public class AxeItemMixin {

    @Inject(
            method = "useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/context/UseOnContext;getItemInHand()Lnet/minecraft/world/item/ItemStack;"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void useOn(UseOnContext pContext, CallbackInfoReturnable<InteractionResult> cir, Level level, BlockPos blockpos, Player player, BlockState blockstate, Optional<BlockState> optional, Optional<BlockState> optional1, Optional<BlockState> optional2) {
        if (optional.isPresent()) {
            Strange.STRANGE_PART_BLOCKS_STRIPPED.get().incrementTag(pContext.getItemInHand());
        } else if (optional1.isPresent()) {
            Strange.STRANGE_PART_BLOCKS_SCRAPED.get().incrementTag(pContext.getItemInHand());
        } else if (optional2.isPresent()) {
            Strange.STRANGE_PART_BLOCKS_DEWAXED.get().incrementTag(pContext.getItemInHand());
        }
    }

}
