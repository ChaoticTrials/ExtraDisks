package de.melanx.extradisks.content.item;

import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.api.RefinedStorageClientApi;
import com.refinedmods.refinedstorage.common.api.storage.AbstractStorageContainerItem;
import com.refinedmods.refinedstorage.common.api.storage.SerializableStorage;
import com.refinedmods.refinedstorage.common.api.storage.StorageRepository;
import com.refinedmods.refinedstorage.common.api.support.HelpTooltipComponent;
import com.refinedmods.refinedstorage.common.storage.StorageTypes;
import com.refinedmods.refinedstorage.common.storage.StorageVariant;
import com.refinedmods.refinedstorage.common.storage.UpgradeableStorageContainer;
import com.refinedmods.refinedstorage.common.support.resource.ItemResource;
import com.refinedmods.refinedstorage.common.util.IdentifierUtil;
import de.melanx.extradisks.Registration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class ExtraItemStorageDiskItem extends AbstractStorageContainerItem implements UpgradeableStorageContainer {

    private final ExtraItemStorageVariant variant;
    private final Component helpText;

    public ExtraItemStorageDiskItem(ExtraItemStorageVariant variant) {
        super(new Item.Properties().stacksTo(1).fireResistant(), RefinedStorageApi.INSTANCE.getStorageContainerItemHelper());
        this.variant = variant;
        this.helpText = variant.getCapacity() == null
                ? IdentifierUtil.createTranslation("item", "creative_storage_disk.help")
                : IdentifierUtil.createTranslation("item", "storage_disk.help", IdentifierUtil.format(variant.getCapacity()));
    }

    @Nullable
    @Override
    protected Long getCapacity() {
        return this.variant.getCapacity();
    }

    @Nonnull
    @Override
    protected String formatAmount(long amount) {
        return RefinedStorageClientApi.INSTANCE.getResourceRendering(ItemResource.class).formatAmount(amount);
    }

    @Nonnull
    @Override
    protected SerializableStorage createStorage(@Nonnull StorageRepository storageRepository) {
        return StorageTypes.ITEM.create(this.variant.getCapacity(), storageRepository::markAsChanged);
    }

    @Nonnull
    @Override
    protected ItemStack createPrimaryDisassemblyByproduct(int count) {
        return new ItemStack(Registration.ADVANCED_MACHINE_CASING.asItem(), count);
    }

    @Nullable
    @Override
    protected ItemStack createSecondaryDisassemblyByproduct(int count) {
        return new ItemStack(Registration.ITEM_STORAGE_PART.get(this.variant).asItem(), count);
    }

    @Nonnull
    @Override
    public Optional<TooltipComponent> getTooltipImage(@Nonnull ItemStack stack) {
        return Optional.of(new HelpTooltipComponent(this.helpText));
    }

    @Nonnull
    @Override
    public StorageVariant getVariant() {
        return this.variant;
    }

    @Override
    public void transferTo(@Nonnull ItemStack from, @Nonnull ItemStack to) {
        this.helper.markAsToTransfer(from, to);
    }
}
