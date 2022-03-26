package de.melanx.extradisks.blocks.fluid;

import com.refinedmods.refinedstorage.container.BaseContainerMenu;
import com.refinedmods.refinedstorage.container.slot.filter.FluidFilterSlot;
import de.melanx.extradisks.items.Registration;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public class ExtraFluidStorageBlockContainerMenu extends BaseContainerMenu {

    public ExtraFluidStorageBlockContainerMenu(int windowId, Player player, @Nullable ExtraFluidStorageBlockEntity tile) {
        //noinspection ConstantConditions
        super(Registration.FLUID_STORAGE_CONTAINER.get(tile.getFluidStorageType()).get(), tile, player, windowId);

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new FluidFilterSlot(tile.getNode().getFilters(), i, 8 + (18 * i), 20));
        }

        this.addPlayerInventory(8, 141);
        this.transferManager.addFluidFilterTransfer(player.getInventory(), tile.getNode().getFilters());
    }
}
