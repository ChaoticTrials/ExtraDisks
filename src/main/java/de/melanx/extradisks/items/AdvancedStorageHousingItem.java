package de.melanx.extradisks.items;

import de.melanx.extradisks.ExtraDisks;
import net.minecraft.item.Item;

public class AdvancedStorageHousingItem extends Item {
    public AdvancedStorageHousingItem() {
        super(new Properties().group(ExtraDisks.ModGroup));
        this.setRegistryName(ExtraDisks.MODID, "advanced_storage_housing");
    }
}
