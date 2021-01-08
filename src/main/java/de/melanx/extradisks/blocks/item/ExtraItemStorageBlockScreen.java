package de.melanx.extradisks.blocks.item;

import com.refinedmods.refinedstorage.screen.StorageScreen;
import com.refinedmods.refinedstorage.screen.StorageScreenTileDataParameters;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ExtraItemStorageBlockScreen extends StorageScreen<ExtraItemStorageBlockContainer> {
    public ExtraItemStorageBlockScreen(ExtraItemStorageBlockContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title, "gui/storage.png",
                new StorageScreenTileDataParameters(null,
                        ExtraItemStorageBlockTile.REDSTONE_MODE,
                        ExtraItemStorageBlockTile.COMPARE,
                        ExtraItemStorageBlockTile.WHITELIST_BLACKLIST,
                        ExtraItemStorageBlockTile.PRIORITY,
                        ExtraItemStorageBlockTile.ACCESS_TYPE),
                ExtraItemStorageBlockTile.STORED::getValue,
                () -> (long) ((ExtraItemStorageBlockTile) container.getTile()).getItemStorageType().getCapacity());
    }
}
