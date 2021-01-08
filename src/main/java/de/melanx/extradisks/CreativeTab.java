package de.melanx.extradisks;

import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.items.item.ExtraItemStorageType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class CreativeTab extends ItemGroup {

    public CreativeTab() {
        super(ExtraDisks.MODID);
    }

    @Nonnull
    @Override
    public ItemStack createIcon() {
        return new ItemStack(Registration.ITEM_STORAGE_DISK.get(ExtraItemStorageType.TIER_8).get());
    }
}
