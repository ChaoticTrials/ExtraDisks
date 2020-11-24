package de.melanx.extradisks.items.fluid;

public enum ExtraFluidStorageType {
    TIER_5_FLUID(16_384),
    TIER_6_FLUID(65_536),
    TIER_7_FLUID(262_144),
    TIER_8_FLUID(1_048_576),
    TIER_9_FLUID(-1);

    private final String name;
    private final int capacity;

    ExtraFluidStorageType(int capacity) {
        if (capacity == -1) {
            this.name = "infinite";
            this.capacity = -1;
        } else {
            this.name = capacity + "k";
            this.capacity = capacity * 1000;
        }
    }

    public String getName() {
        return this.name;
    }

    public int getCapacity() {
        return this.capacity;
    }

}
