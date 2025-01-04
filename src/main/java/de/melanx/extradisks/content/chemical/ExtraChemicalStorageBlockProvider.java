package de.melanx.extradisks.content.chemical;

import com.refinedmods.refinedstorage.common.api.storage.SerializableStorage;
import com.refinedmods.refinedstorage.common.api.storage.StorageBlockProvider;
import com.refinedmods.refinedstorage.common.api.support.resource.ResourceFactory;
import com.refinedmods.refinedstorage.mekanism.ChemicalResourceFactory;
import com.refinedmods.refinedstorage.mekanism.ChemicalResourceType;
import com.refinedmods.refinedstorage.mekanism.content.Menus;
import de.melanx.extradisks.ModConfig;
import de.melanx.extradisks.Registration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;

import javax.annotation.Nonnull;

public class ExtraChemicalStorageBlockProvider implements StorageBlockProvider {

    private final ExtraChemicalStorageVariant variant;
    private final Component displayName;

    public ExtraChemicalStorageBlockProvider(ExtraChemicalStorageVariant variant) {
        this.variant = variant;
        this.displayName = Component.translatable("block.extradisks." + variant.getName() + "_chemical_storage_block");
    }

    @Nonnull
    @Override
    public SerializableStorage createStorage(@Nonnull Runnable runnable) {
        return ChemicalResourceType.STORAGE_TYPE.create(this.variant.getCapacity(), runnable);
    }

    @Nonnull
    @Override
    public Component getDisplayName() {
        return this.displayName;
    }

    @Override
    public long getEnergyUsage() {
        return switch (this.variant) {
            case TIER_5_CHEMICAL -> ModConfig.chemical_tier5usage.get();
            case TIER_6_CHEMICAL -> ModConfig.chemical_tier6usage.get();
            case TIER_7_CHEMICAL -> ModConfig.chemical_tier7usage.get();
            case TIER_8_CHEMICAL -> ModConfig.chemical_tier8usage.get();
            case TIER_9_CHEMICAL -> ModConfig.chemical_tier9usage.get();
        };
    }

    @Nonnull
    @Override
    public ResourceFactory getResourceFactory() {
        return ChemicalResourceFactory.INSTANCE;
    }

    @Nonnull
    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return Registration.CHEMICAL_STORAGE_TILE.get(this.variant).get();
    }

    @Nonnull
    @Override
    public MenuType<?> getMenuType() {
        return Menus.getChemicalStorage();
    }
}
