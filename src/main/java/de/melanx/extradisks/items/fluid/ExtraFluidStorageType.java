package de.melanx.extradisks.items.fluid;

import de.melanx.extradisks.ConfigHandler;

public enum ExtraFluidStorageType {
    TIER_5_FLUID(16_384),
    TIER_6_FLUID(65_536),
    TIER_7_FLUID(262_144),
    TIER_8_FLUID(1_048_576),
    TIER_9_FLUID(Integer.MAX_VALUE / 1000);

    private String name;
    private int capacity;

    private ExtraFluidStorageType(int capacity) {
        if (capacity != 16384 && ConfigHandler.retro.get() && !ConfigHandler.moreDisks.get())
            capacity /= 2;
        this.name = capacity + "k";
        if (capacity == Integer.MAX_VALUE / 1000)
            this.name = "infinite";
        this.capacity = capacity * 1000;
    }

    public String getName() {
        return this.name;
    }

    public int getCapacity() {
        return this.capacity;
    }

}
