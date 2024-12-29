package de.melanx.extradisks.items.item;

import javax.annotation.Nullable;

public enum ExtraItemStorageVariant {
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

    public String getName() {
        return this.name;
    }

    @Nullable
    public Long getCapacity() {
        return this.capacity;
    }

    public boolean hasCapacity() {
        return this.capacity != null;
    }
}
