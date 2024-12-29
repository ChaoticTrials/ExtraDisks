package de.melanx.extradisks.items.storageblocks;

import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.api.RefinedStorageClientApi;
import com.refinedmods.refinedstorage.common.api.storage.AbstractStorageContainerBlockItem;
import com.refinedmods.refinedstorage.common.api.storage.SerializableStorage;
import com.refinedmods.refinedstorage.common.api.storage.StorageRepository;
import com.refinedmods.refinedstorage.common.api.support.HelpTooltipComponent;
import com.refinedmods.refinedstorage.common.storage.StorageTypes;
import com.refinedmods.refinedstorage.common.support.resource.FluidResource;
import com.refinedmods.refinedstorage.common.util.IdentifierUtil;
import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageVariant;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class ExtraFluidStorageBlockItem extends AbstractStorageContainerBlockItem {

    private final ExtraFluidStorageVariant variant;
    private final Component helpText;

    public ExtraFluidStorageBlockItem(Block block, ExtraFluidStorageVariant variant) {
        super(block, new Item.Properties().stacksTo(1).fireResistant(), RefinedStorageApi.INSTANCE.getStorageContainerItemHelper());
        this.variant = variant;
        this.helpText = variant.hasCapacity() ? IdentifierUtil.createTranslation("fluid", "storage_block.help", IdentifierUtil.format(variant.getCapacity())) : IdentifierUtil.createTranslation("fluid", "creative_storage_block.help");
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
    protected SerializableStorage createStorage(StorageRepository storageRepository) {
        return StorageTypes.FLUID.create(this.variant.getCapacity(), storageRepository::markAsChanged);
    }

    @Nonnull
    @Override
    protected ItemStack createPrimaryDisassemblyByproduct(int count) {
        return new ItemStack(Registration.ADVANCED_MACHINE_CASING.asItem(), count);
    }

    @Nullable
    @Override
    protected ItemStack createSecondaryDisassemblyByproduct(int count) {
        return new ItemStack(Registration.FLUID_STORAGE_PART.get(this.variant).asItem(), count);
    }

    @Nonnull
    @Override
    public Optional<TooltipComponent> getTooltipImage(@Nonnull ItemStack stack) {
        return Optional.of(new HelpTooltipComponent(this.helpText));
    }
}
