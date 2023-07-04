package de.melanx.extradisks.blocks.item;

import com.refinedmods.refinedstorage.api.storage.AccessType;
import com.refinedmods.refinedstorage.blockentity.NetworkNodeBlockEntity;
import com.refinedmods.refinedstorage.blockentity.config.IAccessType;
import com.refinedmods.refinedstorage.blockentity.config.IComparable;
import com.refinedmods.refinedstorage.blockentity.config.IPrioritizable;
import com.refinedmods.refinedstorage.blockentity.config.IWhitelistBlacklist;
import com.refinedmods.refinedstorage.blockentity.data.BlockEntitySynchronizationParameter;
import com.refinedmods.refinedstorage.blockentity.data.BlockEntitySynchronizationSpec;
import com.refinedmods.refinedstorage.blockentity.data.RSSerializers;
import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.items.item.ExtraItemStorageType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class ExtraItemStorageBlockEntity extends NetworkNodeBlockEntity<ExtraItemStorageNetworkNode> {

    public static final BlockEntitySynchronizationParameter<Integer, ExtraItemStorageBlockEntity> PRIORITY = IPrioritizable.createParameter(new ResourceLocation(ExtraDisks.MODID, "storage_priority"));
    public static final BlockEntitySynchronizationParameter<Integer, ExtraItemStorageBlockEntity> COMPARE = IComparable.createParameter(new ResourceLocation(ExtraDisks.MODID, "storage_compare"));
    public static final BlockEntitySynchronizationParameter<Integer, ExtraItemStorageBlockEntity> WHITELIST_BLACKLIST = IWhitelistBlacklist.createParameter(new ResourceLocation(ExtraDisks.MODID, "storage_whitelist_blacklist"));
    public static final BlockEntitySynchronizationParameter<AccessType, ExtraItemStorageBlockEntity> ACCESS_TYPE = IAccessType.createParameter(new ResourceLocation(ExtraDisks.MODID, "storage_access_type"));
    public static final BlockEntitySynchronizationParameter<Long, ExtraItemStorageBlockEntity> STORED = new BlockEntitySynchronizationParameter<>(new ResourceLocation(ExtraDisks.MODID, "storage_stored"), RSSerializers.LONG_SERIALIZER, 0L, t -> t.getNode().getStorage() != null ? (long) t.getNode().getStorage().getStored() : 0);
    public static final BlockEntitySynchronizationSpec SPEC = BlockEntitySynchronizationSpec.builder()
            .addWatchedParameter(REDSTONE_MODE)
            .addWatchedParameter(PRIORITY)
            .addWatchedParameter(COMPARE)
            .addWatchedParameter(WHITELIST_BLACKLIST)
            .addWatchedParameter(STORED)
            .addWatchedParameter(ACCESS_TYPE)
            .build();

    @Nonnull
    private final ExtraItemStorageType type;

    public ExtraItemStorageBlockEntity(@Nonnull ExtraItemStorageType type, BlockPos pos, BlockState state) {
        super(Registration.ITEM_STORAGE_TILE.get(type).get(), pos, state, SPEC);
        this.type = type;
    }

    @Nonnull
    public ExtraItemStorageType getItemStorageType() {
        return this.type;
    }

    @Override
    public ExtraItemStorageNetworkNode createNode(Level level, BlockPos pos) {
        return new ExtraItemStorageNetworkNode(level, pos, this.type);
    }
}
