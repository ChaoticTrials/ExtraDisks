package de.melanx.extradisks.items.item;

import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.items.ExtraItems;
import net.minecraft.item.Item;

public class ExtraStoragePartItem extends Item {

    public ExtraStoragePartItem(ExtraItemStorageType type) {
        super(new Properties().group(ExtraDisks.ModGroup));
        this.setRegistryName(ExtraDisks.MODID, type.getName() + "_storage_part");
    }

    public static ExtraStoragePartItem getByType(ExtraItemStorageType type) {
        switch (type) {
            case TWO_FIFTY_SIX:
                return ExtraItems.TIER_5;
            case ONE_TWENTY_FOUR:
                return ExtraItems.TIER_6;
            case FOUR_NINTY_SIX:
                return ExtraItems.TIER_7;
            case SIXTEEN_THREE_EIGHTY_FOUR:
                return ExtraItems.TIER_8;
            default:
                throw new IllegalArgumentException("Cannot get storage part of " + type);
        }
    }

}
