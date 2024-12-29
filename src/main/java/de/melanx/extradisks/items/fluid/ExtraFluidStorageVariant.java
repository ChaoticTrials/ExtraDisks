package de.melanx.extradisks.items.fluid;

public enum ExtraFluidStorageVariant {
    TIER_5_FLUID(16_384L),
    TIER_6_FLUID(65_536L),
    TIER_7_FLUID(262_144L),
    TIER_8_FLUID(1_048_576L),
    TIER_9_FLUID(null);

    private final String name;
    private final Long capacity;

    ExtraFluidStorageVariant(Long capacity) {
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

    public Long getCapacity() {
        return this.capacity;
    }

    public boolean hasCapacity() {
        return this.capacity != null;
    }
}
