package de.melanx.extradisks.blocks.fluid;

import com.refinedmods.refinedstorage.api.storage.IStorage;
import com.refinedmods.refinedstorage.api.storage.disk.IStorageDisk;
import com.refinedmods.refinedstorage.apiimpl.API;
import com.refinedmods.refinedstorage.apiimpl.network.node.storage.FluidStorageNetworkNode;
import com.refinedmods.refinedstorage.apiimpl.network.node.storage.FluidStorageWrapperStorageDisk;
import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.ServerConfig;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

public class ExtraFluidStorageNetworkNode extends FluidStorageNetworkNode {
    public static final ResourceLocation BLOCK_FLUID_16384K_ID = new ResourceLocation(ExtraDisks.MODID, "16384k_fluid_storage_block");
    public static final ResourceLocation BLOCK_FLUID_65536K_ID = new ResourceLocation(ExtraDisks.MODID, "65536k_fluid_storage_block");
    public static final ResourceLocation BLOCK_FLUID_262144K_ID = new ResourceLocation(ExtraDisks.MODID, "262144k_fluid_storage_block");
    public static final ResourceLocation BLOCK_FLUID_1048576K_ID = new ResourceLocation(ExtraDisks.MODID, "1048576k_fluid_storage_block");
    public static final ResourceLocation BLOCK_FLUID_INFINITE_ID = new ResourceLocation(ExtraDisks.MODID, "infinite_fluid_storage_block");

    private final ExtraFluidStorageType type;
    private IStorageDisk<FluidStack> storage;

    public ExtraFluidStorageNetworkNode(World world, BlockPos pos, ExtraFluidStorageType type) {
        super(world, pos, null);
        this.type = type;
    }

    public static ResourceLocation getId(ExtraFluidStorageType type) {
        switch (type) {
            case TIER_5_FLUID:
                return BLOCK_FLUID_16384K_ID;
            case TIER_6_FLUID:
                return BLOCK_FLUID_65536K_ID;
            case TIER_7_FLUID:
                return BLOCK_FLUID_262144K_ID;
            case TIER_8_FLUID:
                return BLOCK_FLUID_1048576K_ID;
            case TIER_9_FLUID:
                return BLOCK_FLUID_INFINITE_ID;
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
            case TIER_5_FLUID:
                return ServerConfig.fluid_tier5usage.get();
            case TIER_6_FLUID:
                return ServerConfig.fluid_tier6usage.get();
            case TIER_7_FLUID:
                return ServerConfig.fluid_tier7usage.get();
            case TIER_8_FLUID:
                return ServerConfig.fluid_tier8usage.get();
            case TIER_9_FLUID:
                return ServerConfig.fluid_tier9usage.get();
            default:
                return 0;
        }
    }

    @Override
    public void addFluidStorages(List<IStorage<FluidStack>> storages) {
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
            disk = API.instance().createDefaultFluidDisk((ServerWorld) this.world, this.type.getCapacity(), owner);
            API.instance().getStorageDiskManager((ServerWorld) this.world).set(this.getStorageId(), disk);
            API.instance().getStorageDiskManager((ServerWorld) this.world).markForSaving();
        }

        this.storage = new FluidStorageWrapperStorageDisk(this, disk);
    }

    @Override
    public IStorageDisk<FluidStack> getStorage() {
        return this.storage;
    }

    @Override
    public ITextComponent getTitle() {
        return new TranslationTextComponent("block." + ExtraDisks.MODID + "." + this.type.getName() + "_fluid_storage_block");
    }

    @Override
    public long getStored() {
        return ExtraFluidStorageBlockTile.STORED.getValue();
    }

    @Override
    public long getCapacity() {
        return this.type.getCapacity();
    }
}
