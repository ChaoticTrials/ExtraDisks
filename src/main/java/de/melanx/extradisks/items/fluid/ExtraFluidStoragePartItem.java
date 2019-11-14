package de.melanx.extradisks.items.fluid;

import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.items.ExtraItems;
import net.minecraft.item.Item;

public class ExtraFluidStoragePartItem extends Item {

    public ExtraFluidStoragePartItem(ExtraFluidStorageType type) {
        super(new Properties().group(ExtraDisks.ModGroup));
        this.setRegistryName(ExtraDisks.MODID, type.getName() + "_fluid_storage_part");
    }

    public static ExtraFluidStoragePartItem getByType(ExtraFluidStorageType type) {
        switch (type) {
            case TIER_5_FLUID:
                return ExtraItems.TIER_5_FLUID_PART;
            case TIER_6_FLUID:
                return ExtraItems.TIER_6_FLUID_PART;
            case TIER_7_FLUID:
                return ExtraItems.TIER_7_FLUID_PART;
            case TIER_8_FLUID:
                return ExtraItems.TIER_8_FLUID_PART;
            default:
                throw new IllegalArgumentException("Cannot get storage part of " + type);
        }
    }

}
