package de.melanx.extradisks.items;

import com.refinedmods.refinedstorage.util.BlockUtils;
import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.blocks.fluid.ExtraFluidStorageBlock;
import de.melanx.extradisks.blocks.fluid.ExtraFluidStorageBlockContainerMenu;
import de.melanx.extradisks.blocks.fluid.ExtraFluidStorageBlockEntity;
import de.melanx.extradisks.blocks.item.ExtraItemStorageBlock;
import de.melanx.extradisks.blocks.item.ExtraItemStorageBlockContainerMenu;
import de.melanx.extradisks.blocks.item.ExtraItemStorageBlockEntity;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageDiskItem;
import de.melanx.extradisks.items.fluid.ExtraFluidStoragePartItem;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageType;
import de.melanx.extradisks.items.item.ExtraItemStorageType;
import de.melanx.extradisks.items.item.ExtraStorageDiskItem;
import de.melanx.extradisks.items.item.ExtraStoragePartItem;
import de.melanx.extradisks.items.storageblocks.ExtraFluidStorageBlockItem;
import de.melanx.extradisks.items.storageblocks.ExtraItemStorageBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class Registration {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExtraDisks.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExtraDisks.MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ExtraDisks.MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ExtraDisks.MODID);
    private static final Item.Properties ITEM_PROPS = new Item.Properties().tab(ExtraDisks.ModCategory);

    // item storage blocks
    public static final Map<ExtraItemStorageType, RegistryObject<ExtraItemStorageBlock>> ITEM_STORAGE_BLOCK = new HashMap<>();
    public static final Map<ExtraItemStorageType, RegistryObject<Item>> ITEM_STORAGE = new HashMap<>();
    public static final Map<ExtraItemStorageType, RegistryObject<BlockEntityType<ExtraItemStorageBlockEntity>>> ITEM_STORAGE_TILE = new HashMap<>();
    public static final Map<ExtraItemStorageType, RegistryObject<MenuType<ExtraItemStorageBlockContainerMenu>>> ITEM_STORAGE_CONTAINER = new HashMap<>();

    // fluid storage blocks
    public static final Map<ExtraFluidStorageType, RegistryObject<ExtraFluidStorageBlock>> FLUID_STORAGE_BLOCK = new HashMap<>();
    public static final Map<ExtraFluidStorageType, RegistryObject<Item>> FLUID_STORAGE = new HashMap<>();
    public static final Map<ExtraFluidStorageType, RegistryObject<BlockEntityType<ExtraFluidStorageBlockEntity>>> FLUID_STORAGE_TILE = new HashMap<>();
    public static final Map<ExtraFluidStorageType, RegistryObject<MenuType<ExtraFluidStorageBlockContainerMenu>>> FLUID_STORAGE_CONTAINER = new HashMap<>();

    // item storage disks/parts
    public static final Map<ExtraItemStorageType, RegistryObject<ExtraStoragePartItem>> ITEM_STORAGE_PART = new HashMap<>();
    public static final Map<ExtraFluidStorageType, RegistryObject<ExtraFluidStoragePartItem>> FLUID_STORAGE_PART = new HashMap<>();
    public static final Map<ExtraItemStorageType, RegistryObject<ExtraStorageDiskItem>> ITEM_STORAGE_DISK = new HashMap<>();
    public static final Map<ExtraFluidStorageType, RegistryObject<ExtraFluidStorageDiskItem>> FLUID_STORAGE_DISK = new HashMap<>();

    public static final RegistryObject<Block> ADVANCED_MACHINE_CASING_BLOCK = BLOCKS.register("advanced_machine_casing", () -> new Block(BlockUtils.DEFAULT_ROCK_PROPERTIES));
    public static final RegistryObject<Item> ADVANCED_MACHINE_CASING = ITEMS.register("advanced_machine_casing", () -> new BlockItem(ADVANCED_MACHINE_CASING_BLOCK.get(), ITEM_PROPS));
    public static final RegistryObject<Item> ADVANCED_STORAGE_HOUSING = ITEMS.register("advanced_storage_housing", () -> new Item(ITEM_PROPS));
    public static final RegistryObject<Item> RAW_WITHERING_PROCESSOR = ITEMS.register("raw_withering_processor", () -> new Item(ITEM_PROPS));
    public static final RegistryObject<Item> WITHERING_PROCESSOR = ITEMS.register("withering_processor", () -> new Item(ITEM_PROPS));

    public static void init() {
        for (ExtraItemStorageType type : ExtraItemStorageType.values()) {
            String name = type.getName() + "_storage_block";
            ITEM_STORAGE_BLOCK.put(type, BLOCKS.register(name, () -> new ExtraItemStorageBlock(type)));
            ITEM_STORAGE.put(type, ITEMS.register(name, () -> new ExtraItemStorageBlockItem(ITEM_STORAGE_BLOCK.get(type).get(), ITEM_PROPS)));
            ITEM_STORAGE_TILE.put(type, TILES.register(name, () -> BlockEntityType.Builder.of((pos, state) -> new ExtraItemStorageBlockEntity(type, pos, state), ITEM_STORAGE_BLOCK.get(type).get()).build(null)));
            ITEM_STORAGE_CONTAINER.put(type, CONTAINERS.register(name, () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                BlockEntity tile = inv.player.getCommandSenderWorld().getBlockEntity(pos);
                if (!(tile instanceof ExtraItemStorageBlockEntity)) {
                    ExtraDisks.LOGGER.error("Wrong tile type.");
                    return null;
                }
                return new ExtraItemStorageBlockContainerMenu(windowId, inv.player, (ExtraItemStorageBlockEntity) tile);
            })));

            ITEM_STORAGE_PART.put(type, ITEMS.register(type.getName() + "_storage_part", ExtraStoragePartItem::new));
            ITEM_STORAGE_DISK.put(type, ITEMS.register(type.getName() + "_storage_disk", () -> new ExtraStorageDiskItem(type)));
        }

        for (ExtraFluidStorageType type : ExtraFluidStorageType.values()) {
            String name = type.getName() + "_fluid_storage_block";
            FLUID_STORAGE_BLOCK.put(type, BLOCKS.register(name, () -> new ExtraFluidStorageBlock(type)));
            FLUID_STORAGE.put(type, ITEMS.register(name, () -> new ExtraFluidStorageBlockItem(FLUID_STORAGE_BLOCK.get(type).get(), ITEM_PROPS)));
            FLUID_STORAGE_TILE.put(type, TILES.register(name, () -> BlockEntityType.Builder.of((pos, state) -> new ExtraFluidStorageBlockEntity(type, pos, state), FLUID_STORAGE_BLOCK.get(type).get()).build(null)));
            FLUID_STORAGE_CONTAINER.put(type, CONTAINERS.register(name, () -> IForgeMenuType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                BlockEntity tile = inv.player.getCommandSenderWorld().getBlockEntity(pos);
                if (!(tile instanceof ExtraFluidStorageBlockEntity)) {
                    ExtraDisks.LOGGER.error("Wrong tile type.");
                    return null;
                }
                return new ExtraFluidStorageBlockContainerMenu(windowId, inv.player, (ExtraFluidStorageBlockEntity) tile);
            })));

            FLUID_STORAGE_PART.put(type, ITEMS.register(type.getName() + "_fluid_storage_part", ExtraFluidStoragePartItem::new));
            FLUID_STORAGE_DISK.put(type, ITEMS.register(type.getName() + "_fluid_storage_disk", () -> new ExtraFluidStorageDiskItem(type)));
        }

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ExtraDisks.LOGGER.info(BLOCKS.getEntries().size() + " blocks registered.");
        ITEMS.register(bus);
        ExtraDisks.LOGGER.info(ITEMS.getEntries().size() - BLOCKS.getEntries().size() + " items registered.");
        TILES.register(bus);
        ExtraDisks.LOGGER.info(TILES.getEntries().size() + " tiles registered.");
        CONTAINERS.register(bus);
        ExtraDisks.LOGGER.info(CONTAINERS.getEntries().size() + " containers registered.");
    }
}
