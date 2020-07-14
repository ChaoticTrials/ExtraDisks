package de.melanx.extradisks.items;

import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageDiskItem;
import de.melanx.extradisks.items.fluid.ExtraFluidStoragePartItem;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageType;
import de.melanx.extradisks.items.item.ExtraItemStorageType;
import de.melanx.extradisks.items.item.ExtraStorageDiskItem;
import de.melanx.extradisks.items.item.ExtraStoragePartItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtraItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExtraDisks.MODID);

    public static final RegistryObject<Item> ADVANCED_STORAGE_HOUSING = ITEMS.register("advanced_storage_housing", AdvancedStorageHousingItem::new);

    private static final String POSTFIX_ITEM_DISKS = "_storage_disk";
    public static final RegistryObject<Item> TIER_5_DISK = ITEMS.register(ExtraItemStorageType.TIER_5.getName() + POSTFIX_ITEM_DISKS, () -> new ExtraStorageDiskItem(ExtraItemStorageType.TIER_5));
    public static final RegistryObject<Item> TIER_6_DISK = ITEMS.register(ExtraItemStorageType.TIER_6.getName() + POSTFIX_ITEM_DISKS, () -> new ExtraStorageDiskItem(ExtraItemStorageType.TIER_6));
    public static final RegistryObject<Item> TIER_7_DISK = ITEMS.register(ExtraItemStorageType.TIER_7.getName() + POSTFIX_ITEM_DISKS, () -> new ExtraStorageDiskItem(ExtraItemStorageType.TIER_7));
    public static final RegistryObject<Item> TIER_8_DISK = ITEMS.register(ExtraItemStorageType.TIER_8.getName() + POSTFIX_ITEM_DISKS, () -> new ExtraStorageDiskItem(ExtraItemStorageType.TIER_8));
    public static final RegistryObject<Item> TIER_9_DISK = ITEMS.register(ExtraItemStorageType.TIER_9.getName() + POSTFIX_ITEM_DISKS, () -> new ExtraStorageDiskItem(ExtraItemStorageType.TIER_9));
    public static final RegistryObject<Item> TIER_10_DISK = ITEMS.register(ExtraItemStorageType.TIER_10.getName() + POSTFIX_ITEM_DISKS, () -> new ExtraStorageDiskItem(ExtraItemStorageType.TIER_10));
    public static final RegistryObject<Item> TIER_11_DISK = ITEMS.register(ExtraItemStorageType.TIER_11.getName() + POSTFIX_ITEM_DISKS, () -> new ExtraStorageDiskItem(ExtraItemStorageType.TIER_11));
    public static final RegistryObject<Item> TIER_12_DISK = ITEMS.register(ExtraItemStorageType.TIER_12.getName() + POSTFIX_ITEM_DISKS, () -> new ExtraStorageDiskItem(ExtraItemStorageType.TIER_12));

    private static final String POSTFIX_FLUID_DISKS = "_fluid_storage_disk";
    public static final RegistryObject<Item> TIER_5_FLUID_DISK = ITEMS.register(ExtraFluidStorageType.TIER_5_FLUID.getName() + POSTFIX_FLUID_DISKS, () -> new ExtraFluidStorageDiskItem(ExtraFluidStorageType.TIER_5_FLUID));
    public static final RegistryObject<Item> TIER_6_FLUID_DISK = ITEMS.register(ExtraFluidStorageType.TIER_6_FLUID.getName() + POSTFIX_FLUID_DISKS, () -> new ExtraFluidStorageDiskItem(ExtraFluidStorageType.TIER_6_FLUID));
    public static final RegistryObject<Item> TIER_7_FLUID_DISK = ITEMS.register(ExtraFluidStorageType.TIER_7_FLUID.getName() + POSTFIX_FLUID_DISKS, () -> new ExtraFluidStorageDiskItem(ExtraFluidStorageType.TIER_7_FLUID));
    public static final RegistryObject<Item> TIER_8_FLUID_DISK = ITEMS.register(ExtraFluidStorageType.TIER_8_FLUID.getName() + POSTFIX_FLUID_DISKS, () -> new ExtraFluidStorageDiskItem(ExtraFluidStorageType.TIER_8_FLUID));
    public static final RegistryObject<Item> TIER_9_FLUID_DISK = ITEMS.register(ExtraFluidStorageType.TIER_9_FLUID.getName() + POSTFIX_FLUID_DISKS, () -> new ExtraFluidStorageDiskItem(ExtraFluidStorageType.TIER_9_FLUID));

    private static final String POSTFIX_ITEM_PARTS = "_storage_part";
    public static final RegistryObject<Item> TIER_5_PART = ITEMS.register(ExtraItemStorageType.TIER_5.getName() + POSTFIX_ITEM_PARTS, ExtraStoragePartItem::new);
    public static final RegistryObject<Item> TIER_6_PART = ITEMS.register(ExtraItemStorageType.TIER_6.getName() + POSTFIX_ITEM_PARTS, ExtraStoragePartItem::new);
    public static final RegistryObject<Item> TIER_7_PART = ITEMS.register(ExtraItemStorageType.TIER_7.getName() + POSTFIX_ITEM_PARTS, ExtraStoragePartItem::new);
    public static final RegistryObject<Item> TIER_8_PART = ITEMS.register(ExtraItemStorageType.TIER_8.getName() + POSTFIX_ITEM_PARTS, ExtraStoragePartItem::new);
    public static final RegistryObject<Item> TIER_9_PART = ITEMS.register(ExtraItemStorageType.TIER_9.getName() + POSTFIX_ITEM_PARTS, ExtraStoragePartItem::new);
    public static final RegistryObject<Item> TIER_10_PART = ITEMS.register(ExtraItemStorageType.TIER_10.getName() + POSTFIX_ITEM_PARTS, ExtraStoragePartItem::new);
    public static final RegistryObject<Item> TIER_11_PART = ITEMS.register(ExtraItemStorageType.TIER_11.getName() + POSTFIX_ITEM_PARTS, ExtraStoragePartItem::new);
    public static final RegistryObject<Item> TIER_12_PART = ITEMS.register(ExtraItemStorageType.TIER_12.getName() + POSTFIX_ITEM_PARTS, ExtraStoragePartItem::new);

    private static final String POSTFIX_FLUID_PARTS = "_fluid_storage_part";
    public static final RegistryObject<Item> TIER_5_FLUID_PART = ITEMS.register(ExtraFluidStorageType.TIER_5_FLUID.getName() + POSTFIX_FLUID_PARTS, ExtraFluidStoragePartItem::new);
    public static final RegistryObject<Item> TIER_6_FLUID_PART = ITEMS.register(ExtraFluidStorageType.TIER_6_FLUID.getName() + POSTFIX_FLUID_PARTS, ExtraFluidStoragePartItem::new);
    public static final RegistryObject<Item> TIER_7_FLUID_PART = ITEMS.register(ExtraFluidStorageType.TIER_7_FLUID.getName() + POSTFIX_FLUID_PARTS, ExtraFluidStoragePartItem::new);
    public static final RegistryObject<Item> TIER_8_FLUID_PART = ITEMS.register(ExtraFluidStorageType.TIER_8_FLUID.getName() + POSTFIX_FLUID_PARTS, ExtraFluidStoragePartItem::new);
    public static final RegistryObject<Item> TIER_9_FLUID_PART = ITEMS.register(ExtraFluidStorageType.TIER_9_FLUID.getName() + POSTFIX_FLUID_PARTS, ExtraFluidStoragePartItem::new);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ExtraDisks.LOGGER.info("Items registered.");
    }
}
