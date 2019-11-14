package de.melanx.extradisks.items;

import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageDiskItem;
import de.melanx.extradisks.items.fluid.ExtraFluidStoragePartItem;
import de.melanx.extradisks.items.item.ExtraStorageDiskItem;
import de.melanx.extradisks.items.item.ExtraStoragePartItem;
import net.minecraftforge.registries.ObjectHolder;

public class ExtraItems {

    @ObjectHolder(ExtraDisks.MODID + ":advanced_storage_housing")
    public static final AdvancedStorageHousingItem ADVANCED_STORAGE_HOUSING = null;

    @ObjectHolder(ExtraDisks.MODID + ":256k_storage_part")
    public static final ExtraStoragePartItem TIER_5 = null;
    @ObjectHolder(ExtraDisks.MODID + ":1024k_storage_part")
    public static final ExtraStoragePartItem TIER_6 = null;
    @ObjectHolder(ExtraDisks.MODID + ":4096k_storage_part")
    public static final ExtraStoragePartItem TIER_7 = null;
    @ObjectHolder(ExtraDisks.MODID + ":16384k_storage_part")
    public static final ExtraStoragePartItem TIER_8 = null;

    @ObjectHolder(ExtraDisks.MODID + ":256k_storage_disk")
    public static final ExtraStorageDiskItem TIER_5_DISK = null;
    @ObjectHolder(ExtraDisks.MODID + ":1024k_storage_disk")
    public static final ExtraStorageDiskItem TIER_6_DISK = null;
    @ObjectHolder(ExtraDisks.MODID + ":4096k_storage_disk")
    public static final ExtraStorageDiskItem TIER_7_DISK = null;
    @ObjectHolder(ExtraDisks.MODID + ":16384k_storage_disk")
    public static final ExtraStorageDiskItem TIER_8_DISK = null;

    @ObjectHolder(ExtraDisks.MODID + ":16384k_fluid_storage_part")
    public static final ExtraFluidStoragePartItem TIER_5_FLUID_PART = null;
    @ObjectHolder(ExtraDisks.MODID + ":65536k_fluid_storage_part")
    public static final ExtraFluidStoragePartItem TIER_6_FLUID_PART = null;
    @ObjectHolder(ExtraDisks.MODID + ":262144k_fluid_storage_part")
    public static final ExtraFluidStoragePartItem TIER_7_FLUID_PART = null;
    @ObjectHolder(ExtraDisks.MODID + ":1048576k_fluid_storage_part")
    public static final ExtraFluidStoragePartItem TIER_8_FLUID_PART = null;

    @ObjectHolder(ExtraDisks.MODID + ":16384k_fluid_storage_disk")
    public static final ExtraFluidStorageDiskItem TIER_5_FLUID_DISK = null;
    @ObjectHolder(ExtraDisks.MODID + ":65536k_fluid_storage_disk")
    public static final ExtraFluidStorageDiskItem TIER_6_FLUID_DISK = null;
    @ObjectHolder(ExtraDisks.MODID + ":262144k_fluid_storage_disk")
    public static final ExtraFluidStorageDiskItem TIER_7_FLUID_DISK = null;
    @ObjectHolder(ExtraDisks.MODID + ":1048576k_fluid_storage_disk")
    public static final ExtraFluidStorageDiskItem TIER_8_FLUID_DISK = null;

}
