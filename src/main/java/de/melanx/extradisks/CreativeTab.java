package de.melanx.extradisks;

import de.melanx.extradisks.items.ExtraItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class CreativeTab extends ItemGroup {

    public CreativeTab() {
        super(ExtraDisks.MODID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ExtraItems.TIER_8_DISK.get());
    }
}
