package de.melanx.extradisks.items.item;

public enum ExtraItemStorageType {
    TWO_FIFTY_SIX(256),
    ONE_TWENTY_FOUR(1024),
    FOUR_NINTY_SIX(4096),
    SIXTEEN_THREE_EIGHTY_FOUR(16384);

    private String name;
    private int capacity;

    private ExtraItemStorageType(int capacity) {
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
