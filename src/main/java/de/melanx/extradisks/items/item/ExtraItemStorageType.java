package de.melanx.extradisks.items.item;

public enum ExtraItemStorageType {
    TIER_5(256),
    TIER_6(1024),
    TIER_7(4096),
    TIER_8(16384),
    TIER_9(65536),
    TIER_10(262144),
    TIER_11(1048576),
    TIER_12(Integer.MAX_VALUE / 1000);

    private String name;
    private final int capacity;

    ExtraItemStorageType(int capacity) {
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
