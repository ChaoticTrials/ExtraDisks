package de.melanx.extradisks.items.fluid;

import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.items.ExtraItems;
import net.minecraft.item.Item;

public class ExtraFluidStoragePartItem extends Item {

    public ExtraFluidStoragePartItem() {
        super(new Properties().group(ExtraDisks.ModGroup));
    }

    public static ExtraFluidStoragePartItem getByType(ExtraFluidStorageType type) {
        switch (type) {
            case TIER_5_FLUID:
                return (ExtraFluidStoragePartItem) ExtraItems.TIER_5_FLUID_PART.get();
            case TIER_6_FLUID:
                return (ExtraFluidStoragePartItem) ExtraItems.TIER_6_FLUID_PART.get();
            case TIER_7_FLUID:
                return (ExtraFluidStoragePartItem) ExtraItems.TIER_7_FLUID_PART.get();
            case TIER_8_FLUID:
                return (ExtraFluidStoragePartItem) ExtraItems.TIER_8_FLUID_PART.get();
            case TIER_9_FLUID:
                return (ExtraFluidStoragePartItem) ExtraItems.TIER_9_FLUID_PART.get();
            default:
                throw new IllegalArgumentException("Cannot get storage part of " + type);
        }
    }
}
