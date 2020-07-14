package de.melanx.extradisks.items.item;

import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.items.ExtraItems;
import net.minecraft.item.Item;

public class ExtraStoragePartItem extends Item {

    public ExtraStoragePartItem() {
        super(new Properties().group(ExtraDisks.ModGroup));
    }

    public static ExtraStoragePartItem getByType(ExtraItemStorageType type) {
        switch (type) {
            case TIER_5:
                return (ExtraStoragePartItem) ExtraItems.TIER_5_PART.get();
            case TIER_6:
                return (ExtraStoragePartItem) ExtraItems.TIER_6_PART.get();
            case TIER_7:
                return (ExtraStoragePartItem) ExtraItems.TIER_7_PART.get();
            case TIER_8:
                return (ExtraStoragePartItem) ExtraItems.TIER_8_PART.get();
            case TIER_9:
                return (ExtraStoragePartItem) ExtraItems.TIER_9_PART.get();
            case TIER_10:
                return (ExtraStoragePartItem) ExtraItems.TIER_10_PART.get();
            case TIER_11:
                return (ExtraStoragePartItem) ExtraItems.TIER_11_PART.get();
            case TIER_12:
                return (ExtraStoragePartItem) ExtraItems.TIER_12_PART.get();
            default:
                throw new IllegalArgumentException("Cannot get storage part of " + type);
        }
    }

}
