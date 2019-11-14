package de.melanx.extradisks;

import de.melanx.extradisks.items.ExtraItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;

public class CreativeTab extends ItemGroup {

    public CreativeTab() {
        super(ExtraDisks.MODID);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ExtraItems.TIER_8_DISK);
    }
}
