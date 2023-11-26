package de.melanx.extradisks.blocks.fluid;

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
import de.melanx.extradisks.items.fluid.ExtraFluidStorageType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class ExtraFluidStorageBlockEntity extends NetworkNodeBlockEntity<ExtraFluidStorageNetworkNode> {

    public static final BlockEntitySynchronizationParameter<Integer, ExtraFluidStorageBlockEntity> PRIORITY = IPrioritizable.createParameter(new ResourceLocation(ExtraDisks.MODID, "fluid_storage_priority"));
    public static final BlockEntitySynchronizationParameter<Integer, ExtraFluidStorageBlockEntity> COMPARE = IComparable.createParameter(new ResourceLocation(ExtraDisks.MODID, "fluid_storage_compare"));
    public static final BlockEntitySynchronizationParameter<Integer, ExtraFluidStorageBlockEntity> WHITELIST_BLACKLIST = IWhitelistBlacklist.createParameter(new ResourceLocation(ExtraDisks.MODID, "fluid_storage_whitelist_blacklist"));
    public static final BlockEntitySynchronizationParameter<AccessType, ExtraFluidStorageBlockEntity> ACCESS_TYPE = IAccessType.createParameter(new ResourceLocation(ExtraDisks.MODID, "fluid_storage_access_type"));
    public static final BlockEntitySynchronizationParameter<Long, ExtraFluidStorageBlockEntity> STORED = new BlockEntitySynchronizationParameter<>(new ResourceLocation(ExtraDisks.MODID, "fluid_storage_stored"), RSSerializers.LONG_SERIALIZER, 0L, t -> t.getNode().getStorage() != null ? (long) t.getNode().getStorage().getStored() : 0);
    public static final BlockEntitySynchronizationSpec SPEC = BlockEntitySynchronizationSpec.builder()
            .addWatchedParameter(REDSTONE_MODE)
            .addWatchedParameter(PRIORITY)
            .addWatchedParameter(COMPARE)
            .addWatchedParameter(WHITELIST_BLACKLIST)
            .addWatchedParameter(STORED)
            .addWatchedParameter(ACCESS_TYPE)
            .build();

    @Nonnull
    private final ExtraFluidStorageType type;

    public ExtraFluidStorageBlockEntity(@Nonnull ExtraFluidStorageType type, BlockPos pos, BlockState state) {
        super(Registration.FLUID_STORAGE_TILE.get(type).get(), pos, state, SPEC, ExtraFluidStorageNetworkNode.class);
        this.type = type;
    }

    @Nonnull
    public ExtraFluidStorageType getFluidStorageType() {
        return this.type;
    }

    @Override
    public ExtraFluidStorageNetworkNode createNode(Level level, BlockPos pos) {
        return new ExtraFluidStorageNetworkNode(level, pos, this.type);
    }
}
