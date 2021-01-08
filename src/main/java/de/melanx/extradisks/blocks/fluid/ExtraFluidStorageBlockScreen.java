package de.melanx.extradisks.blocks.fluid;

import com.refinedmods.refinedstorage.screen.StorageScreen;
import com.refinedmods.refinedstorage.screen.StorageScreenTileDataParameters;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ExtraFluidStorageBlockScreen extends StorageScreen<ExtraFluidStorageBlockContainer> {
    public ExtraFluidStorageBlockScreen(ExtraFluidStorageBlockContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title, "gui/storage.png",
                new StorageScreenTileDataParameters(null,
                        ExtraFluidStorageBlockTile.REDSTONE_MODE,
                        ExtraFluidStorageBlockTile.COMPARE,
                        ExtraFluidStorageBlockTile.WHITELIST_BLACKLIST,
                        ExtraFluidStorageBlockTile.PRIORITY,
                        ExtraFluidStorageBlockTile.ACCESS_TYPE),
                ExtraFluidStorageBlockTile.STORED::getValue,
                () -> (long) ((ExtraFluidStorageBlockTile) container.getTile()).getFluidStorageType().getCapacity());
    }
}
