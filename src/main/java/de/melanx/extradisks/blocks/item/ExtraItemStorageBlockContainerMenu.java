package de.melanx.extradisks.blocks.item;

import com.refinedmods.refinedstorage.container.BaseContainer;
import com.refinedmods.refinedstorage.container.slot.filter.FilterSlot;
import de.melanx.extradisks.items.Registration;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ExtraItemStorageBlockContainerMenu extends BaseContainer {
    public ExtraItemStorageBlockContainerMenu(int windowId, Player player, @Nullable ExtraItemStorageBlockEntity tile) {
        super(Registration.ITEM_STORAGE_CONTAINER.get(tile.getItemStorageType()).get(), tile, player, windowId);

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new FilterSlot(tile.getNode().getFilters(), i, 8 + (18 * i), 20));
        }
        this.addPlayerInventory(8, 141);
        this.transferManager.addItemFilterTransfer(player.getInventory(), tile.getNode().getFilters());
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return true;
    }
}
