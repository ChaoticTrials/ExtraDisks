package de.melanx.extradisks.blocks.fluid;

import com.refinedmods.refinedstorage.api.storage.AccessType;
import com.refinedmods.refinedstorage.tile.NetworkNodeTile;
import com.refinedmods.refinedstorage.tile.config.IAccessType;
import com.refinedmods.refinedstorage.tile.config.IComparable;
import com.refinedmods.refinedstorage.tile.config.IPrioritizable;
import com.refinedmods.refinedstorage.tile.config.IWhitelistBlacklist;
import com.refinedmods.refinedstorage.tile.data.RSSerializers;
import com.refinedmods.refinedstorage.tile.data.TileDataParameter;
import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

public class ExtraFluidStorageBlockEntity extends NetworkNodeTile<ExtraFluidStorageNetworkNode> {
    public static final TileDataParameter<Integer, ExtraFluidStorageBlockEntity> PRIORITY = IPrioritizable.createParameter();
    public static final TileDataParameter<Integer, ExtraFluidStorageBlockEntity> COMPARE = IComparable.createParameter();
    public static final TileDataParameter<Integer, ExtraFluidStorageBlockEntity> WHITELIST_BLACKLIST = IWhitelistBlacklist.createParameter();
    public static final TileDataParameter<AccessType, ExtraFluidStorageBlockEntity> ACCESS_TYPE = IAccessType.createParameter();
    public static final TileDataParameter<Long, ExtraFluidStorageBlockEntity> STORED = new TileDataParameter<>(RSSerializers.LONG_SERIALIZER, 0L, t -> t.getNode().getStorage() != null ? (long) t.getNode().getStorage().getStored() : 0);

    @Nonnull
    private final ExtraFluidStorageType type;

    public ExtraFluidStorageBlockEntity(@Nonnull ExtraFluidStorageType type, BlockPos pos, BlockState state) {
        super(Registration.FLUID_STORAGE_TILE.get(type).get(), pos, state);
        this.type = type;

        this.dataManager.addWatchedParameter(PRIORITY);
        this.dataManager.addWatchedParameter(COMPARE);
        this.dataManager.addWatchedParameter(WHITELIST_BLACKLIST);
        this.dataManager.addWatchedParameter(ACCESS_TYPE);
        this.dataManager.addWatchedParameter(STORED);
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
