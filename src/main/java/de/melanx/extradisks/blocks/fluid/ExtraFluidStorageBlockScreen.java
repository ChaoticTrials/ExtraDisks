package de.melanx.extradisks.blocks.fluid;

import com.refinedmods.refinedstorage.screen.StorageScreen;
import com.refinedmods.refinedstorage.screen.StorageScreenTileDataParameters;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ExtraFluidStorageBlockScreen extends StorageScreen<ExtraFluidStorageBlockContainerMenu> {
    public ExtraFluidStorageBlockScreen(ExtraFluidStorageBlockContainerMenu menu, Inventory container, Component title) {
        super(menu, container, title, "gui/storage.png",
                new StorageScreenTileDataParameters(null,
                        ExtraFluidStorageBlockEntity.REDSTONE_MODE,
                        ExtraFluidStorageBlockEntity.COMPARE,
                        ExtraFluidStorageBlockEntity.WHITELIST_BLACKLIST,
                        ExtraFluidStorageBlockEntity.PRIORITY,
                        ExtraFluidStorageBlockEntity.ACCESS_TYPE),
                ExtraFluidStorageBlockEntity.STORED::getValue,
                () -> (long) ((ExtraFluidStorageBlockEntity) menu.getTile()).getFluidStorageType().getCapacity());
    }
}
