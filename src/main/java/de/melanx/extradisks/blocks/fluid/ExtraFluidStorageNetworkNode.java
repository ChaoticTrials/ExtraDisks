package de.melanx.extradisks.blocks.fluid;

import com.refinedmods.refinedstorage.api.storage.IStorage;
import com.refinedmods.refinedstorage.api.storage.disk.IStorageDisk;
import com.refinedmods.refinedstorage.apiimpl.API;
import com.refinedmods.refinedstorage.apiimpl.network.node.storage.FluidStorageNetworkNode;
import com.refinedmods.refinedstorage.apiimpl.network.node.storage.FluidStorageWrapperStorageDisk;
import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.ModConfig;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
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

    public ExtraFluidStorageNetworkNode(Level level, BlockPos pos, ExtraFluidStorageType type) {
        super(level, pos, null);
        this.type = type;
    }

    public static ResourceLocation getId(ExtraFluidStorageType type) {
        return switch (type) {
            case TIER_5_FLUID -> BLOCK_FLUID_16384K_ID;
            case TIER_6_FLUID -> BLOCK_FLUID_65536K_ID;
            case TIER_7_FLUID -> BLOCK_FLUID_262144K_ID;
            case TIER_8_FLUID -> BLOCK_FLUID_1048576K_ID;
            case TIER_9_FLUID -> BLOCK_FLUID_INFINITE_ID;
        };
    }

    @Override
    public ResourceLocation getId() {
        return getId(this.type);
    }

    @Override
    public int getEnergyUsage() {
        return switch (this.type) {
            case TIER_5_FLUID -> ModConfig.fluid_tier5usage.get();
            case TIER_6_FLUID -> ModConfig.fluid_tier6usage.get();
            case TIER_7_FLUID -> ModConfig.fluid_tier7usage.get();
            case TIER_8_FLUID -> ModConfig.fluid_tier8usage.get();
            case TIER_9_FLUID -> ModConfig.fluid_tier9usage.get();
        };
    }

    @Override
    public void addFluidStorages(List<IStorage<FluidStack>> storages) {
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
            disk = API.instance().createDefaultFluidDisk((ServerLevel) this.level, this.type.getCapacity(), owner);
            API.instance().getStorageDiskManager((ServerLevel) this.level).set(this.getStorageId(), disk);
            API.instance().getStorageDiskManager((ServerLevel) this.level).markForSaving();
        }

        //noinspection unchecked
        this.storage = new FluidStorageWrapperStorageDisk(this, disk);
    }

    @Override
    public IStorageDisk<FluidStack> getStorage() {
        return this.storage;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("block." + ExtraDisks.MODID + "." + this.type.getName() + "_fluid_storage_block");
    }

    @Override
    public long getStored() {
        return ExtraFluidStorageBlockEntity.STORED.getValue();
    }

    @Override
    public long getCapacity() {
        return this.type.getCapacity();
    }
}
