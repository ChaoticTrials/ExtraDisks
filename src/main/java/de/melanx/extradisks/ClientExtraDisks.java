package de.melanx.extradisks;

import com.refinedmods.refinedstorage.common.api.RefinedStorageClientApi;
import de.melanx.extradisks.content.fluid.ExtraFluidStorageDiskItem;
import de.melanx.extradisks.content.item.ExtraItemStorageDiskItem;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.DeferredItem;

@Mod(value = ExtraDisks.MODID, dist = Dist.CLIENT)
public class ClientExtraDisks {

    public ClientExtraDisks(IEventBus modBus) {
        modBus.addListener(this::onClientSetup);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        for (DeferredItem<ExtraItemStorageDiskItem> value : Registration.ITEM_STORAGE_DISK.values()) {
            RefinedStorageClientApi.INSTANCE.registerDiskModel(
                    value.asItem(), Identifier.fromNamespaceAndPath(ExtraDisks.MODID, "block/disk/item_disk")
            );
        }

        for (DeferredItem<ExtraFluidStorageDiskItem> value : Registration.FLUID_STORAGE_DISK.values()) {
            RefinedStorageClientApi.INSTANCE.registerDiskModel(
                    value.asItem(), Identifier.fromNamespaceAndPath(ExtraDisks.MODID, "block/disk/fluid_disk")
            );
        }

//        if (ModList.get().isLoaded("mekanism") && ModList.get().isLoaded("refinedstorage_mekanism_integration")) { todo Mekanism
//            for (DeferredItem<ExtraChemicalStorageDiskItem> value : Registration.CHEMICAL_STORAGE_DISK.values()) {
//                RefinedStorageClientApi.INSTANCE.registerDiskModel(
//                        value.asItem(), Identifier.fromNamespaceAndPath(ExtraDisks.MODID, "block/disk/chemical_disk")
//                );
//            }
//        }
    }
}
