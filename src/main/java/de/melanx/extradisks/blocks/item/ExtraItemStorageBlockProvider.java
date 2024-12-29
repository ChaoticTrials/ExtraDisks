package de.melanx.extradisks.blocks.item;

import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.api.storage.SerializableStorage;
import com.refinedmods.refinedstorage.common.api.storage.StorageBlockProvider;
import com.refinedmods.refinedstorage.common.api.support.resource.ResourceFactory;
import com.refinedmods.refinedstorage.common.content.Menus;
import com.refinedmods.refinedstorage.common.storage.StorageTypes;
import de.melanx.extradisks.ModConfig;
import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.items.item.ExtraItemStorageVariant;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;

import javax.annotation.Nonnull;

public class ExtraItemStorageBlockProvider implements StorageBlockProvider {

    private final ExtraItemStorageVariant variant;
    private final Component displayName;

    public ExtraItemStorageBlockProvider(ExtraItemStorageVariant variant) {
        this.variant = variant;
        this.displayName = Component.translatable("block.extradisks." + variant.getName() + "_storage_block");
    }

    @Nonnull
    @Override
    public SerializableStorage createStorage(@Nonnull Runnable runnable) {
        return StorageTypes.ITEM.create(this.variant.getCapacity(), runnable);
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return this.displayName;
    }

    @Override
    public long getEnergyUsage() {
        return switch (this.variant) {
            case TIER_5 -> ModConfig.tier5usage.get();
            case TIER_6 -> ModConfig.tier6usage.get();
            case TIER_7 -> ModConfig.tier7usage.get();
            case TIER_8 -> ModConfig.tier8usage.get();
            case TIER_9 -> ModConfig.tier9usage.get();
            case TIER_10 -> ModConfig.tier10usage.get();
            case TIER_11 -> ModConfig.tier11usage.get();
            case TIER_12 -> ModConfig.tier12usage.get();
        };
    }

    @Nonnull
    @Override
    public ResourceFactory getResourceFactory() {
        return RefinedStorageApi.INSTANCE.getItemResourceFactory();
    }

    @Nonnull
    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return Registration.ITEM_STORAGE_TILE.get(this.variant).get();
    }

    @Nonnull
    @Override
    public MenuType<?> getMenuType() {
        return Menus.INSTANCE.getItemStorage();
    }
}
