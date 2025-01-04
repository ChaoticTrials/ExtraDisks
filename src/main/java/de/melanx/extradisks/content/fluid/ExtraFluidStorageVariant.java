package de.melanx.extradisks.content.fluid;

import com.refinedmods.refinedstorage.common.storage.StorageVariant;
import de.melanx.extradisks.Registration;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;

import javax.annotation.Nonnull;

public enum ExtraFluidStorageVariant implements StringRepresentable, StorageVariant {
    TIER_5_FLUID(16_384L),
    TIER_6_FLUID(65_536L),
    TIER_7_FLUID(262_144L),
    TIER_8_FLUID(1_048_576L),
    TIER_9_FLUID(null);

    private final String name;
    private final Long capacityInBuckets;

    ExtraFluidStorageVariant(Long capacityInBuckets) {
        if (capacityInBuckets == null) {
            this.name = "infinite";
            this.capacityInBuckets = null;
        } else {
            this.name = capacityInBuckets + "b";
            this.capacityInBuckets = capacityInBuckets;
        }
    }

    public Long getCapacityInBuckets() {
        return this.capacityInBuckets;
    }

    @Override
    public Long getCapacity() {
        return this.capacityInBuckets == null ? null : this.capacityInBuckets * 1000;
    }

    @Nonnull
    @Override
    public Item getStoragePart() {
        return Registration.FLUID_STORAGE_PART.get(this).asItem();
    }

    public boolean hasCapacity() {
        return this.capacityInBuckets != null;
    }

    public String getName() {
        return this.name;
    }

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
    }
}
