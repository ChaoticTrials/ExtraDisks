package de.melanx.extradisks.items.fluid;

import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.api.RefinedStorageClientApi;
import com.refinedmods.refinedstorage.common.api.storage.AbstractStorageContainerItem;
import com.refinedmods.refinedstorage.common.api.storage.SerializableStorage;
import com.refinedmods.refinedstorage.common.api.storage.StorageRepository;
import com.refinedmods.refinedstorage.common.storage.StorageTypes;
import com.refinedmods.refinedstorage.common.support.resource.FluidResource;
import de.melanx.extradisks.items.Registration;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ExtraFluidStorageDiskItem extends AbstractStorageContainerItem {

    private final ExtraFluidStorageVariant variant;

    public ExtraFluidStorageDiskItem(ExtraFluidStorageVariant variant) {
        super(new Item.Properties().stacksTo(1), RefinedStorageApi.INSTANCE.getStorageContainerItemHelper());
        this.variant = variant;
    }

    @Nullable
    @Override
    protected Long getCapacity() {
        return this.variant.getCapacity();
    }

    @Nonnull
    @Override
    protected String formatAmount(long amount) {
        return RefinedStorageClientApi.INSTANCE.getResourceRendering(FluidResource.class).formatAmount(amount);
    }

    @Nonnull
    @Override
    protected SerializableStorage createStorage(@Nonnull StorageRepository storageRepository) {
        return StorageTypes.FLUID.create(this.variant.getCapacity(), storageRepository::markAsChanged);
    }

    @Nonnull
    @Override
    protected ItemStack createPrimaryDisassemblyByproduct(int count) {
        return new ItemStack(Registration.ADVANCED_STORAGE_HOUSING.asItem(), count);
    }

    @Nullable
    @Override
    protected ItemStack createSecondaryDisassemblyByproduct(int count) {
        return new ItemStack(Registration.FLUID_STORAGE_PART.get(this.variant).asItem(), count);
    }
}
