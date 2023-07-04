package de.melanx.extradisks.blocks.item;

import com.refinedmods.refinedstorage.RS;
import com.refinedmods.refinedstorage.screen.StorageScreen;
import com.refinedmods.refinedstorage.screen.StorageScreenSynchronizationParameters;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ExtraItemStorageBlockScreen extends StorageScreen<ExtraItemStorageBlockContainerMenu> {

    public ExtraItemStorageBlockScreen(ExtraItemStorageBlockContainerMenu menu, Inventory container, Component title) {
        //noinspection ConstantConditions
        super(menu, container, title, new ResourceLocation(RS.ID, "textures/gui/storage.png"),
                new StorageScreenSynchronizationParameters(null,
                        ExtraItemStorageBlockEntity.REDSTONE_MODE,
                        ExtraItemStorageBlockEntity.COMPARE,
                        ExtraItemStorageBlockEntity.WHITELIST_BLACKLIST,
                        ExtraItemStorageBlockEntity.PRIORITY,
                        ExtraItemStorageBlockEntity.ACCESS_TYPE),
                ExtraItemStorageBlockEntity.STORED::getValue,
                () -> (long) ((ExtraItemStorageBlockEntity) menu.getBlockEntity()).getItemStorageType().getCapacity());
    }
}
