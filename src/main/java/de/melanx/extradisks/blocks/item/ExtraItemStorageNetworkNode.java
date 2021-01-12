package de.melanx.extradisks.blocks.item;

import com.refinedmods.refinedstorage.api.storage.IStorage;
import com.refinedmods.refinedstorage.api.storage.disk.IStorageDisk;
import com.refinedmods.refinedstorage.apiimpl.API;
import com.refinedmods.refinedstorage.apiimpl.network.node.storage.ItemStorageWrapperStorageDisk;
import com.refinedmods.refinedstorage.apiimpl.network.node.storage.StorageNetworkNode;
import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.ServerConfig;
import de.melanx.extradisks.items.item.ExtraItemStorageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;

public class ExtraItemStorageNetworkNode extends StorageNetworkNode {
    public static final ResourceLocation BLOCK_256K_ID = new ResourceLocation(ExtraDisks.MODID, "256k_storage_block");
    public static final ResourceLocation BLOCK_1024K_ID = new ResourceLocation(ExtraDisks.MODID, "1024k_storage_block");
    public static final ResourceLocation BLOCK_4096K_ID = new ResourceLocation(ExtraDisks.MODID, "4096k_storage_block");
    public static final ResourceLocation BLOCK_16384K_ID = new ResourceLocation(ExtraDisks.MODID, "16384k_storage_block");
    public static final ResourceLocation BLOCK_65536K_ID = new ResourceLocation(ExtraDisks.MODID, "65536k_storage_block");
    public static final ResourceLocation BLOCK_262144K_ID = new ResourceLocation(ExtraDisks.MODID, "262144k_storage_block");
    public static final ResourceLocation BLOCK_1048576K_ID = new ResourceLocation(ExtraDisks.MODID, "1048576k_storage_block");
    public static final ResourceLocation BLOCK_INFINITE_ID = new ResourceLocation(ExtraDisks.MODID, "infinite_storage_block");

    private final ExtraItemStorageType type;
    private IStorageDisk<ItemStack> storage;

    public ExtraItemStorageNetworkNode(World world, BlockPos pos, ExtraItemStorageType type) {
        super(world, pos, null);
        this.type = type;
    }

    public static ResourceLocation getId(ExtraItemStorageType type) {
        switch (type) {
            case TIER_5:
                return BLOCK_256K_ID;
            case TIER_6:
                return BLOCK_1024K_ID;
            case TIER_7:
                return BLOCK_4096K_ID;
            case TIER_8:
                return BLOCK_16384K_ID;
            case TIER_9:
                return BLOCK_65536K_ID;
            case TIER_10:
                return BLOCK_262144K_ID;
            case TIER_11:
                return BLOCK_1048576K_ID;
            case TIER_12:
                return BLOCK_INFINITE_ID;
            default:
                throw new IllegalArgumentException("Unknown storage type " + type);
        }
    }

    @Override
    public ResourceLocation getId() {
        return getId(this.type);
    }

    @Override
    public int getEnergyUsage() {
        switch (this.type) {
            case TIER_5:
                return ServerConfig.tier5usage.get();
            case TIER_6:
                return ServerConfig.tier6usage.get();
            case TIER_7:
                return ServerConfig.tier7usage.get();
            case TIER_8:
                return ServerConfig.tier8usage.get();
            case TIER_9:
                return ServerConfig.tier9usage.get();
            case TIER_10:
                return ServerConfig.tier10usage.get();
            case TIER_11:
                return ServerConfig.tier11usage.get();
            case TIER_12:
                return ServerConfig.tier12usage.get();
            default:
                return 0;
        }
    }

    @Override
    public void addItemStorages(List<IStorage<ItemStack>> storages) {
        if (this.storage == null) {
            this.loadStorage(null);
        }

        storages.add(this.storage);
    }

    @Override
    public void loadStorage(@Nullable PlayerEntity owner) {
        //noinspection rawtypes
        IStorageDisk disk = API.instance().getStorageDiskManager((ServerWorld) this.world).get(this.getStorageId());

        if (disk == null) {
            disk = API.instance().createDefaultItemDisk((ServerWorld) this.world, this.type.getCapacity(), owner);
            API.instance().getStorageDiskManager((ServerWorld) this.world).set(this.getStorageId(), disk);
            API.instance().getStorageDiskManager((ServerWorld) this.world).markForSaving();
        }
        this.storage = new ItemStorageWrapperStorageDisk(this, disk);
    }

    @Override
    public IStorageDisk<ItemStack> getStorage() {
        return this.storage;
    }

    @Override
    public ITextComponent getTitle() {
        return new TranslationTextComponent("block." + ExtraDisks.MODID + "." + this.type.getName() + "_storage_block");
    }

    @Override
    public long getStored() {
        return ExtraItemStorageBlockTile.STORED.getValue();
    }

    @Override
    public long getCapacity() {
        return this.type.getCapacity();
    }
}
