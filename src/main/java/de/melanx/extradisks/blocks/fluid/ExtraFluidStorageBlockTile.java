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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExtraFluidStorageBlockTile extends NetworkNodeTile<ExtraFluidStorageNetworkNode> {
    public static final TileDataParameter<Integer, ExtraFluidStorageBlockTile> PRIORITY = IPrioritizable.createParameter();
    public static final TileDataParameter<Integer, ExtraFluidStorageBlockTile> COMPARE = IComparable.createParameter();
    public static final TileDataParameter<Integer, ExtraFluidStorageBlockTile> WHITELIST_BLACKLIST = IWhitelistBlacklist.createParameter();
    public static final TileDataParameter<AccessType, ExtraFluidStorageBlockTile> ACCESS_TYPE = IAccessType.createParameter();
    public static final TileDataParameter<Long, ExtraFluidStorageBlockTile> STORED = new TileDataParameter<>(RSSerializers.LONG_SERIALIZER, 0L, t -> t.getNode().getStorage() != null ? (long) t.getNode().getStorage().getStored() : 0);

    private final ExtraFluidStorageType type;

    public ExtraFluidStorageBlockTile(ExtraFluidStorageType type) {
        super(Registration.FLUID_STORAGE_TILE.get(type).get());
        this.type = type;
    }

    public ExtraFluidStorageType getFluidStorageType() {
        return this.type;
    }

    @Override
    public ExtraFluidStorageNetworkNode createNode(World world, BlockPos blockPos) {
        return null;
    }
}
