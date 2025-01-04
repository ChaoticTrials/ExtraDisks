package de.melanx.extradisks.content.item;

import com.refinedmods.refinedstorage.common.storage.StorageVariant;
import de.melanx.extradisks.Registration;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum ExtraItemStorageVariant implements StringRepresentable, StorageVariant {
    TIER_5(256L),
    TIER_6(1024L),
    TIER_7(4096L),
    TIER_8(16384L),
    TIER_9(65536L),
    TIER_10(262144L),
    TIER_11(1048576L),
    TIER_12(null);

    private final String name;
    private final Long capacity;

    ExtraItemStorageVariant(Long capacity) {
        if (capacity == null) {
            this.name = "infinite";
            this.capacity = null;
        } else {
            this.name = capacity + "k";
            this.capacity = capacity * 1024;
        }
    }

    @Nullable
    @Override
    public Long getCapacity() {
        return this.capacity;
    }

    @Nonnull
    @Override
    public Item getStoragePart() {
        return Registration.ITEM_STORAGE_PART.get(this).asItem();
    }

    public boolean hasCapacity() {
        return this.capacity != null;
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
