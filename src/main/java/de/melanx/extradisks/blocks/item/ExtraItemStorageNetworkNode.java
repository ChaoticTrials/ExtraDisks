package de.melanx.extradisks.blocks.item;

import com.refinedmods.refinedstorage.api.storage.IStorage;
import com.refinedmods.refinedstorage.api.storage.disk.IStorageDisk;
import com.refinedmods.refinedstorage.apiimpl.API;
import com.refinedmods.refinedstorage.apiimpl.network.node.storage.ItemStorageWrapperStorageDisk;
import com.refinedmods.refinedstorage.apiimpl.network.node.storage.StorageNetworkNode;
import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.ModConfig;
import de.melanx.extradisks.items.item.ExtraItemStorageType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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

    public ExtraItemStorageNetworkNode(Level level, BlockPos pos, ExtraItemStorageType type) {
        super(level, pos, null);
        this.type = type;
    }

    public static ResourceLocation getId(ExtraItemStorageType type) {
        return switch (type) {
            case TIER_5 -> BLOCK_256K_ID;
            case TIER_6 -> BLOCK_1024K_ID;
            case TIER_7 -> BLOCK_4096K_ID;
            case TIER_8 -> BLOCK_16384K_ID;
            case TIER_9 -> BLOCK_65536K_ID;
            case TIER_10 -> BLOCK_262144K_ID;
            case TIER_11 -> BLOCK_1048576K_ID;
            case TIER_12 -> BLOCK_INFINITE_ID;
        };
    }

    @Override
    public ResourceLocation getId() {
        return getId(this.type);
    }

    @Override
    public int getEnergyUsage() {
        return switch (this.type) {
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

    @Override
    public void addItemStorages(List<IStorage<ItemStack>> storages) {
        if (this.storage == null) {
            this.loadStorage(null);
        }

        storages.add(this.storage);
    }

    @Override
    public void loadStorage(@Nullable Player owner) {
        //noinspection rawtypes
        IStorageDisk disk = API.instance().getStorageDiskManager((ServerLevel) this.level).get(this.getStorageId());

        if (disk == null) {
            disk = API.instance().createDefaultItemDisk((ServerLevel) this.level, this.type.getCapacity(), owner);
            API.instance().getStorageDiskManager((ServerLevel) this.level).set(this.getStorageId(), disk);
            API.instance().getStorageDiskManager((ServerLevel) this.level).markForSaving();
        }
        //noinspection unchecked
        this.storage = new ItemStorageWrapperStorageDisk(this, disk);
    }

    @Override
    public IStorageDisk<ItemStack> getStorage() {
        return this.storage;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block." + ExtraDisks.MODID + "." + this.type.getName() + "_storage_block");
    }

    @Override
    public long getStored() {
        return ExtraItemStorageBlockEntity.STORED.getValue();
    }

    @Override
    public long getCapacity() {
        return this.type.getCapacity();
    }
}
