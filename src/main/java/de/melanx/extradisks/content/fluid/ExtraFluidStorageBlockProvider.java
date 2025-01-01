package de.melanx.extradisks.content.fluid;

import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.api.storage.SerializableStorage;
import com.refinedmods.refinedstorage.common.api.storage.StorageBlockProvider;
import com.refinedmods.refinedstorage.common.api.support.resource.ResourceFactory;
import com.refinedmods.refinedstorage.common.content.Menus;
import com.refinedmods.refinedstorage.common.storage.StorageTypes;
import de.melanx.extradisks.ModConfig;
import de.melanx.extradisks.Registration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;

import javax.annotation.Nonnull;

public class ExtraFluidStorageBlockProvider implements StorageBlockProvider {

    private final ExtraFluidStorageVariant variant;
    private final Component displayName;

    public ExtraFluidStorageBlockProvider(ExtraFluidStorageVariant variant) {
        this.variant = variant;
        this.displayName = Component.translatable("block.extradisks." + variant.getName() + "_fluid_storage_block");
    }

    @Nonnull
    @Override
    public SerializableStorage createStorage(@Nonnull Runnable runnable) {
        return StorageTypes.FLUID.create(this.variant.getCapacity(), runnable);
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return this.displayName;
    }

    @Override
    public long getEnergyUsage() {
        return switch (this.variant) {
            case TIER_5_FLUID -> ModConfig.fluid_tier5usage.get();
            case TIER_6_FLUID -> ModConfig.fluid_tier6usage.get();
            case TIER_7_FLUID -> ModConfig.fluid_tier7usage.get();
            case TIER_8_FLUID -> ModConfig.fluid_tier8usage.get();
            case TIER_9_FLUID -> ModConfig.fluid_tier9usage.get();
        };
    }

    @Nonnull
    @Override
    public ResourceFactory getResourceFactory() {
        return RefinedStorageApi.INSTANCE.getFluidResourceFactory();
    }

    @Nonnull
    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return Registration.FLUID_STORAGE_TILE.get(this.variant).get();
    }

    @Nonnull
    @Override
    public MenuType<?> getMenuType() {
        return Menus.INSTANCE.getFluidStorage();
    }
}
