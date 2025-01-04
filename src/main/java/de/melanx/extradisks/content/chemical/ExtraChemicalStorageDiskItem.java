package de.melanx.extradisks.content.chemical;

import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.api.RefinedStorageClientApi;
import com.refinedmods.refinedstorage.common.api.storage.AbstractStorageContainerItem;
import com.refinedmods.refinedstorage.common.api.storage.SerializableStorage;
import com.refinedmods.refinedstorage.common.api.storage.StorageRepository;
import com.refinedmods.refinedstorage.common.api.support.HelpTooltipComponent;
import com.refinedmods.refinedstorage.common.storage.StorageVariant;
import com.refinedmods.refinedstorage.common.storage.UpgradeableStorageContainer;
import com.refinedmods.refinedstorage.common.util.IdentifierUtil;
import com.refinedmods.refinedstorage.mekanism.ChemicalResource;
import com.refinedmods.refinedstorage.mekanism.ChemicalResourceType;
import com.refinedmods.refinedstorage.mekanism.MekanismIntegrationIdentifierUtil;
import de.melanx.extradisks.Registration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class ExtraChemicalStorageDiskItem extends AbstractStorageContainerItem implements UpgradeableStorageContainer {

    private final ExtraChemicalStorageVariant variant;
    private final Component helpText;

    public ExtraChemicalStorageDiskItem(ExtraChemicalStorageVariant variant) {
        super(new Properties().stacksTo(1), RefinedStorageApi.INSTANCE.getStorageContainerItemHelper());
        this.variant = variant;
        this.helpText = variant.getCapacity() == null
                ? MekanismIntegrationIdentifierUtil.createMekanismIntegrationTranslation("item", "creative_chemical_storage_disk.help")
                : MekanismIntegrationIdentifierUtil.createMekanismIntegrationTranslation("item", "chemical_storage_disk.help", IdentifierUtil.format(variant.getCapacity()));
    }

    @Nullable
    @Override
    protected Long getCapacity() {
        return this.variant.getCapacity();
    }

    @Nonnull
    @Override
    protected String formatAmount(long amount) {
        return RefinedStorageClientApi.INSTANCE.getResourceRendering(ChemicalResource.class).formatAmount(amount);
    }

    @Nonnull
    @Override
    protected SerializableStorage createStorage(StorageRepository storageRepository) {
        return ChemicalResourceType.STORAGE_TYPE.create(this.variant.getCapacity(), storageRepository::markAsChanged);
    }

    @Nonnull
    @Override
    protected ItemStack createPrimaryDisassemblyByproduct(int count) {
        return new ItemStack(Registration.ADVANCED_STORAGE_HOUSING.asItem(), count);
    }

    @Nullable
    @Override
    protected ItemStack createSecondaryDisassemblyByproduct(int count) {
        return new ItemStack(Registration.CHEMICAL_STORAGE_PART.get(this.variant).asItem(), count);
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
