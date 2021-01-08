package de.melanx.extradisks.blocks.item;

import com.refinedmods.refinedstorage.api.storage.AccessType;
import com.refinedmods.refinedstorage.tile.NetworkNodeTile;
import com.refinedmods.refinedstorage.tile.config.IAccessType;
import com.refinedmods.refinedstorage.tile.config.IComparable;
import com.refinedmods.refinedstorage.tile.config.IPrioritizable;
import com.refinedmods.refinedstorage.tile.config.IWhitelistBlacklist;
import com.refinedmods.refinedstorage.tile.data.RSSerializers;
import com.refinedmods.refinedstorage.tile.data.TileDataParameter;
import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.items.item.ExtraItemStorageType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExtraItemStorageBlockTile extends NetworkNodeTile<ExtraItemStorageNetworkNode> {
    public static final TileDataParameter<Integer, ExtraItemStorageBlockTile> PRIORITY = IPrioritizable.createParameter();
    public static final TileDataParameter<Integer, ExtraItemStorageBlockTile> COMPARE = IComparable.createParameter();
    public static final TileDataParameter<Integer, ExtraItemStorageBlockTile> WHITELIST_BLACKLIST = IWhitelistBlacklist.createParameter();
    public static final TileDataParameter<AccessType, ExtraItemStorageBlockTile> ACCESS_TYPE = IAccessType.createParameter();
    public static final TileDataParameter<Long, ExtraItemStorageBlockTile> STORED = new TileDataParameter<>(RSSerializers.LONG_SERIALIZER, 0L, t -> t.getNode().getStorage() != null ? (long) t.getNode().getStorage().getStored() : 0);

    private final ExtraItemStorageType type;

    public ExtraItemStorageBlockTile(ExtraItemStorageType type) {
        super(Registration.ITEM_STORAGE_TILE.get(type).get());
        this.type = type;

        this.dataManager.addWatchedParameter(PRIORITY);
        this.dataManager.addWatchedParameter(COMPARE);
        this.dataManager.addWatchedParameter(WHITELIST_BLACKLIST);
        this.dataManager.addWatchedParameter(ACCESS_TYPE);
        this.dataManager.addWatchedParameter(STORED);
    }

    public ExtraItemStorageType getItemStorageType() {
        return this.type;
    }

    @Override
    public ExtraItemStorageNetworkNode createNode(World world, BlockPos pos) {
        return new ExtraItemStorageNetworkNode(world, pos, this.type);
    }
}
