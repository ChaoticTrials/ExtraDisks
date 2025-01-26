package de.melanx.extradisks;

import com.refinedmods.refinedstorage.common.api.RefinedStorageClientApi;
import de.melanx.extradisks.content.chemical.ExtraChemicalStorageDiskItem;
import de.melanx.extradisks.content.fluid.ExtraFluidStorageDiskItem;
import de.melanx.extradisks.content.item.ExtraItemStorageDiskItem;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
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
                    value.asItem(), ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "block/disk/item_disk")
            );
        }

        for (DeferredItem<ExtraFluidStorageDiskItem> value : Registration.FLUID_STORAGE_DISK.values()) {
            RefinedStorageClientApi.INSTANCE.registerDiskModel(
                    value.asItem(), ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "block/disk/fluid_disk")
            );
        }

        if (ModList.get().isLoaded("mekanism") && ModList.get().isLoaded("refinedstorage_mekanism_integration")) {
            for (DeferredItem<ExtraChemicalStorageDiskItem> value : Registration.CHEMICAL_STORAGE_DISK.values()) {
                RefinedStorageClientApi.INSTANCE.registerDiskModel(
                        value.asItem(), ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "block/disk/chemical_disk")
                );
            }
        }
    }
}
