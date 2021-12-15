package de.melanx.extradisks;

import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.items.item.ExtraItemStorageType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class CreativeTab extends CreativeModeTab {

    public CreativeTab() {
        super(ExtraDisks.MODID);
    }

    @Nonnull
    @Override
    public ItemStack makeIcon() {
        return new ItemStack(Registration.ITEM_STORAGE_DISK.get(ExtraItemStorageType.TIER_8).get());
    }
}
