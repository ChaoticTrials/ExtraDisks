package de.melanx.extradisks.items.item;

import de.melanx.extradisks.items.Registration;
import net.minecraft.world.item.Item;

public class ExtraStoragePartItem extends Item {

    public ExtraStoragePartItem() {
        super(new Properties());
    }

    public static ExtraStoragePartItem getByType(ExtraItemStorageType type) {
        return Registration.ITEM_STORAGE_PART.get(type).get();
    }
}
