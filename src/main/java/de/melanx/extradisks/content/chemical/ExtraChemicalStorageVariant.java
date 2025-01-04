package de.melanx.extradisks.content.chemical;

import com.refinedmods.refinedstorage.common.storage.StorageVariant;
import de.melanx.extradisks.Registration;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum ExtraChemicalStorageVariant implements StringRepresentable, StorageVariant {
    TIER_5_CHEMICAL(65_536L),
    TIER_6_CHEMICAL(262_144L),
    TIER_7_CHEMICAL(1_048_576L),
    TIER_8_CHEMICAL(8_388_608L),
    TIER_9_CHEMICAL(null);

    private final String name;
    private final Long capacityInBuckets;

    ExtraChemicalStorageVariant(Long capacityInBuckets) {
        if (capacityInBuckets == null) {
            this.name = "infinite";
            this.capacityInBuckets = null;
        } else {
            this.name = capacityInBuckets + "b";
            this.capacityInBuckets = capacityInBuckets;
        }
    }

    public String getName() {
        return this.name;
    }

    @Nullable
    public Long getCapacityInBuckets() {
        return this.capacityInBuckets;
    }

    @Nullable
    public Long getCapacity() {
        return this.capacityInBuckets == null ? null : this.capacityInBuckets * 1000;
    }

    @Nonnull
    @Override
    public Item getStoragePart() {
        return Registration.CHEMICAL_STORAGE_PART.get(this).asItem();
    }

    public boolean hasCapacity() {
        return this.capacityInBuckets != null;
    }

    @Nonnull
    @Override
    public String getSerializedName() {
        return this.name;
    }
}
