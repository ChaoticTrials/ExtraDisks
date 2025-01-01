package de.melanx.extradisks.content.item;

import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.api.RefinedStorageClientApi;
import com.refinedmods.refinedstorage.common.api.storage.AbstractStorageContainerBlockItem;
import com.refinedmods.refinedstorage.common.api.storage.SerializableStorage;
import com.refinedmods.refinedstorage.common.api.storage.StorageRepository;
import com.refinedmods.refinedstorage.common.api.support.HelpTooltipComponent;
import com.refinedmods.refinedstorage.common.storage.StorageTypes;
import com.refinedmods.refinedstorage.common.support.resource.ItemResource;
import com.refinedmods.refinedstorage.common.util.IdentifierUtil;
import de.melanx.extradisks.Registration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class ExtraItemStorageBlockItem extends AbstractStorageContainerBlockItem {

    private final ExtraItemStorageVariant variant;
    private final Component helpText;

    public ExtraItemStorageBlockItem(Block block, ExtraItemStorageVariant variant) {
        super(block, new Item.Properties().stacksTo(1).fireResistant(), RefinedStorageApi.INSTANCE.getStorageContainerItemHelper());
        this.variant = variant;
        this.helpText = variant.hasCapacity()
                ? IdentifierUtil.createTranslation("item", "storage_block.help", IdentifierUtil.format(variant.getCapacity()))
                : IdentifierUtil.createTranslation("item", "creative_storage_block.help");
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
    protected SerializableStorage createStorage(StorageRepository storageRepository) {
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
}
