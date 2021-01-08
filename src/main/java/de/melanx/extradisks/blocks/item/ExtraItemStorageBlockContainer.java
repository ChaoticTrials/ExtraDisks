package de.melanx.extradisks.blocks.item;

import com.refinedmods.refinedstorage.container.BaseContainer;
import com.refinedmods.refinedstorage.container.slot.filter.FilterSlot;
import de.melanx.extradisks.items.Registration;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;

public class ExtraItemStorageBlockContainer extends BaseContainer {
    public ExtraItemStorageBlockContainer(int windowId, PlayerEntity player, @Nullable ExtraItemStorageBlockTile tile) {
        super(Registration.ITEM_STORAGE_CONTAINER.get(tile.getItemStorageType()).get(), tile, player, windowId);

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new FilterSlot(tile.getNode().getFilters(), i, 8 + (18 * i), 20));
        }
        this.addPlayerInventory(8, 141);
        this.transferManager.addItemFilterTransfer(player.inventory, tile.getNode().getFilters());
    }
}
