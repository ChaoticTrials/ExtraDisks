package de.melanx.extradisks.items.fluid;

public enum ExtraFluidStorageType {
    TIER_5_FLUID(16_384),
    TIER_6_FLUID(65_536),
    TIER_7_FLUID(262_144),
    TIER_8_FLUID(1_048_576);

    private String name;
    private int capacity;

    private ExtraFluidStorageType(int capacity) {
        this.name = capacity + "k";
        this.capacity = capacity * 1000;
    }

    public String getName() {
        return this.name;
    }

    public int getCapacity() {
        return this.capacity;
    }

}
