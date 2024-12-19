package me.paypur.strange.data;

import net.minecraft.world.item.*;

public class ItemTypeUtil {

    public static boolean isStrangifiable(Item item) {
        return isWeapon(item) || isToolTiered(item) || isArmor(item) ||
                item instanceof ElytraItem ||
                isShield(item) ||
                item instanceof FishingRodItem ||
                item instanceof ShearsItem ||
    // TODO: idk if i want to keep this, only non damageable
//                item instanceof BucketItem ||
                item instanceof FlintAndSteelItem;
    }

    public static boolean isShield(Item item) {
        return item instanceof ShieldItem;
    }

    // TODO
    public static boolean isWearable(Item item) {
        return false;
    }

    public static boolean isArmor(Item item) {
        return item instanceof ArmorItem;
    }

    public static boolean isWeapon(Item item) {
        return isWeaponMelee(item) || isWeaponRanged(item);
    }

    public static boolean isWeaponMelee(Item item) {
        return item instanceof SwordItem || item instanceof AxeItem || item instanceof TridentItem;
    }

    public static boolean isWeaponRanged(Item item) {
        return item instanceof BowItem || item instanceof CrossbowItem || item instanceof TridentItem;
    }

    public static boolean isToolTiered(Item item) {
        return item instanceof TieredItem;
    }

}
