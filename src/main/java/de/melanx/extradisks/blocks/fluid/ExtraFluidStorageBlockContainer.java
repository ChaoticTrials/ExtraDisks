package de.melanx.extradisks.blocks.fluid;

import com.refinedmods.refinedstorage.container.BaseContainer;
import com.refinedmods.refinedstorage.container.slot.filter.FluidFilterSlot;
import de.melanx.extradisks.items.Registration;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;

public class ExtraFluidStorageBlockContainer extends BaseContainer {
    public ExtraFluidStorageBlockContainer(int windowId, PlayerEntity player, @Nullable ExtraFluidStorageBlockTile tile) {
        super(Registration.FLUID_STORAGE_CONTAINER.get(tile.getFluidStorageType()).get(), tile, player, windowId);

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new FluidFilterSlot(tile.getNode().getFilters(), i, 8 + (18 * i), 20));
        }
        this.addPlayerInventory(8, 141);
        this.transferManager.addFluidFilterTransfer(player.inventory, tile.getNode().getFilters());
    }
}
