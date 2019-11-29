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
            case TIER_5:
                return ExtraItems.TIER_5;
            case TIER_6:
                return ExtraItems.TIER_6;
            case TIER_7:
                return ExtraItems.TIER_7;
            case TIER_8:
                return ExtraItems.TIER_8;
            case TIER_9:
                return ExtraItems.TIER_9;
            case TIER_10:
                return ExtraItems.TIER_10;
            case TIER_11:
                return ExtraItems.TIER_11;
            case TIER_12:
                return ExtraItems.TIER_12;
            default:
                throw new IllegalArgumentException("Cannot get storage part of " + type);
        }
    }

}
