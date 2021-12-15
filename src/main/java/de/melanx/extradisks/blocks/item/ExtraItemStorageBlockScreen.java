package de.melanx.extradisks.blocks.item;

import com.refinedmods.refinedstorage.screen.StorageScreen;
import com.refinedmods.refinedstorage.screen.StorageScreenTileDataParameters;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ExtraItemStorageBlockScreen extends StorageScreen<ExtraItemStorageBlockContainerMenu> {
    public ExtraItemStorageBlockScreen(ExtraItemStorageBlockContainerMenu menu, Inventory container, Component title) {
        super(menu, container, title, "gui/storage.png",
                new StorageScreenTileDataParameters(null,
                        ExtraItemStorageBlockEntity.REDSTONE_MODE,
                        ExtraItemStorageBlockEntity.COMPARE,
                        ExtraItemStorageBlockEntity.WHITELIST_BLACKLIST,
                        ExtraItemStorageBlockEntity.PRIORITY,
                        ExtraItemStorageBlockEntity.ACCESS_TYPE),
                ExtraItemStorageBlockEntity.STORED::getValue,
                () -> (long) ((ExtraItemStorageBlockEntity) menu.getTile()).getItemStorageType().getCapacity());
    }
}
