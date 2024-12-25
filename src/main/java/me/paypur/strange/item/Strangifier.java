package me.paypur.strange.item;

import me.paypur.strange.Strange;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

public class Strangifier extends StrangePart {

    public Strangifier() {
        super("strangifier", null);
    }

    public String getNbtKey(ItemStack stack) {
        return getStrangePart(stack).getNbtKey();
    }

    @Override
    public Number getValue(ItemStack stack) {
        StrangePart part = getStrangePart(stack);
        return part.getValue(stack);
    }

    @Override
    public Component getComponent(ItemStack stack) {
        StrangePart part = getStrangePart(stack);
        return new TextComponent(rank(getValue(stack).longValue()) + " - ").append(part.getComponent(stack)).withStyle(ChatFormatting.DARK_GRAY);
    }

    @Override
    public void appendComponent(ItemStack stack, List<Component> components) {
        components.add(getComponent(stack));
    }

    @Override
    public void createTag(ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (stack.getTag() == null) {
            // TODO: storing the tag as a var causes problems if updated later
            stack.setTag(new CompoundTag());
        }

        if (stack.getTag().contains(Strange.MOD_ID)) {
            cir.setReturnValue(ItemStack.EMPTY);
            return;
        }

        stack.getTag().put(Strange.MOD_ID, new CompoundTag());
        stack.setHoverName(stack.getHoverName().copy().withStyle(s -> s.withColor(Strange.COLOR).withItalic(false)));

        StrangePart part = getStrangePart(stack);
        part.createTag(stack, cir);
    }

    @Override
    public void incrementTag(ItemStack stack, Number num) {
        StrangePart part = getStrangePart(stack);
        part.incrementTag(stack, num);
    }

    public StrangePart getStrangePart(ItemStack stack) {
        // order matters
        if (stack.is(Strange.TOOLS_BREAKING)) {
            return Strange.STRANGE_PART_BLOCKS_BROKEN.get();
        }
        if (stack.is(Strange.WEAPONS)) {
            return Strange.STRANGE_PART_KILLS.get();
        }
        if (stack.is(Strange.DAMAGEABLE)) {
            return Strange.STRANGE_PART_TIMES_USED.get();
        }
        throw new RuntimeException();
    }

    // surely theres a better way to do this
    public String rank(long n) {
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
