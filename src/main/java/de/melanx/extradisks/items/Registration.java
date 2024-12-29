package de.melanx.extradisks.items;

import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.api.support.network.AbstractNetworkNodeContainerBlockEntity;
import com.refinedmods.refinedstorage.common.content.*;
import com.refinedmods.refinedstorage.common.storage.storageblock.StorageBlock;
import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.blocks.fluid.ExtraFluidStorageBlockProvider;
import de.melanx.extradisks.blocks.item.ExtraItemStorageBlockProvider;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageDiskItem;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageVariant;
import de.melanx.extradisks.items.item.ExtraItemStorageDiskItem;
import de.melanx.extradisks.items.item.ExtraItemStorageVariant;
import de.melanx.extradisks.items.storageblocks.ExtraFluidStorageBlockItem;
import de.melanx.extradisks.items.storageblocks.ExtraItemStorageBlockItem;
import de.melanx.extradisks.loottable.ExtraLootFunctions;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.*;

import javax.annotation.Nonnull;
import java.util.*;

public class Registration {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ExtraDisks.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ExtraDisks.MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ExtraDisks.MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, ExtraDisks.MODID);
    public static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTIONS = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, ExtraDisks.MODID);
    private static final Item.Properties ITEM_PROPS = new Item.Properties();

    // item storage blocks
    public static final Map<ExtraItemStorageVariant, DeferredBlock<Block>> ITEM_STORAGE_BLOCK = new HashMap<>();
    public static final Map<ExtraItemStorageVariant, DeferredItem<Item>> ITEM_STORAGE = new HashMap<>();
    public static final Map<ExtraItemStorageVariant, DeferredHolder<BlockEntityType<?>, BlockEntityType<AbstractNetworkNodeContainerBlockEntity<?>>>> ITEM_STORAGE_TILE = new HashMap<>();
    public static final Map<ExtraItemStorageVariant, DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>>> ITEM_STORAGE_CONTAINER = new HashMap<>();

    // fluid storage blocks
    public static final Map<ExtraFluidStorageVariant, DeferredBlock<Block>> FLUID_STORAGE_BLOCK = new HashMap<>();
    public static final Map<ExtraFluidStorageVariant, DeferredItem<Item>> FLUID_STORAGE = new HashMap<>();
    public static final Map<ExtraFluidStorageVariant, DeferredHolder<BlockEntityType<?>, BlockEntityType<AbstractNetworkNodeContainerBlockEntity<?>>>> FLUID_STORAGE_TILE = new HashMap<>();
    public static final Map<ExtraFluidStorageVariant, DeferredHolder<MenuType<?>, MenuType<AbstractContainerMenu>>> FLUID_STORAGE_CONTAINER = new HashMap<>();

    // item storage disks/parts
    public static final Map<ExtraItemStorageVariant, DeferredItem<Item>> ITEM_STORAGE_PART = new HashMap<>();
    public static final Map<ExtraFluidStorageVariant, DeferredItem<Item>> FLUID_STORAGE_PART = new HashMap<>();
    public static final Map<ExtraItemStorageVariant, DeferredItem<ExtraItemStorageDiskItem>> ITEM_STORAGE_DISK = new HashMap<>();
    public static final Map<ExtraFluidStorageVariant, DeferredItem<ExtraFluidStorageDiskItem>> FLUID_STORAGE_DISK = new HashMap<>();

    public static final DeferredBlock<Block> ADVANCED_MACHINE_CASING_BLOCK = BLOCKS.registerSimpleBlock("advanced_machine_casing", BlockConstants.PROPERTIES);
    public static final DeferredItem<BlockItem> ADVANCED_MACHINE_CASING = ITEMS.registerSimpleBlockItem("advanced_machine_casing", ADVANCED_MACHINE_CASING_BLOCK, ITEM_PROPS);
    public static final DeferredItem<Item> ADVANCED_STORAGE_HOUSING = ITEMS.registerSimpleItem("advanced_storage_housing", ITEM_PROPS);
    public static final DeferredItem<Item> RAW_WITHERING_PROCESSOR = ITEMS.registerSimpleItem("raw_withering_processor", ITEM_PROPS);
    public static final DeferredItem<Item> WITHERING_PROCESSOR = ITEMS.registerSimpleItem("withering_processor", ITEM_PROPS);

    public static void registerExtras(RegisterEvent event) {
        event.register(Registries.CREATIVE_MODE_TAB, helper -> {
            helper.register(ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "general"), CreativeModeTab.builder()
                    .title(Component.literal("Extra Disks"))
                    .icon(() -> new ItemStack(Registration.ITEM_STORAGE_DISK.get(ExtraItemStorageVariant.TIER_8).get()))
                    .displayItems((enabledFlags, output) -> {
                        output.accept(ADVANCED_MACHINE_CASING.get());
                        output.accept(ADVANCED_STORAGE_HOUSING.get());
                        output.accept(RAW_WITHERING_PROCESSOR.get());
                        output.accept(WITHERING_PROCESSOR.get());

                        // item storage
                        for (ExtraItemStorageVariant variant : ExtraItemStorageVariant.values()) {
                            DeferredItem<ExtraItemStorageDiskItem> item = ITEM_STORAGE_DISK.get(variant);
                            output.accept(item.get());
                        }
                        for (ExtraItemStorageVariant variant : ExtraItemStorageVariant.values()) {
                            DeferredItem<Item> item = ITEM_STORAGE.get(variant);
                            output.accept(item.get());
                        }
                        for (ExtraItemStorageVariant variant : ExtraItemStorageVariant.values()) {
                            DeferredItem<Item> item = ITEM_STORAGE_PART.get(variant);
                            output.accept(item.get());
                        }

                        // fluid storage
                        for (ExtraFluidStorageVariant variant : ExtraFluidStorageVariant.values()) {
                            DeferredItem<ExtraFluidStorageDiskItem> item = FLUID_STORAGE_DISK.get(variant);
                            output.accept(item.get());
                        }
                        for (ExtraFluidStorageVariant variant : ExtraFluidStorageVariant.values()) {
                            DeferredItem<Item> item = FLUID_STORAGE.get(variant);
                            output.accept(item.get());
                        }
                        for (ExtraFluidStorageVariant variant : ExtraFluidStorageVariant.values()) {
                            DeferredItem<Item> item = FLUID_STORAGE_PART.get(variant);
                            output.accept(item.get());
                        }
                    })
                    .build());
        });

        event.register(Registries.LOOT_FUNCTION_TYPE, helper -> ExtraLootFunctions.register());
    }

    public static void init(IEventBus modBus) {
        BlockEntityTypeFactory blockEntityTypeFactory = new BlockEntityTypeFactory() {
            @Nonnull
            @Override
            public <T extends BlockEntity> BlockEntityType<T> create(@Nonnull BlockEntityProvider<T> blockEntityProvider, @Nonnull Block... allowedBlocks) {
                Objects.requireNonNull(blockEntityProvider);
                return new BlockEntityType<>(blockEntityProvider::create, new HashSet<>(Arrays.asList(allowedBlocks)), null);
            }
        };

        ExtendedMenuTypeFactory extendedMenuTypeFactory = new ExtendedMenuTypeFactory() {
            @Nonnull
            @Override
            public <T extends AbstractContainerMenu, D> MenuType<T> create(@Nonnull MenuSupplier<T, D> menuSupplier, @Nonnull StreamCodec<RegistryFriendlyByteBuf, D> streamCodec) {
                return IMenuTypeExtension.create((syncId, inventory, buffer) -> {
                    D data = streamCodec.decode(buffer);
                    return menuSupplier.create(syncId, inventory, data);
                });
            }
        };

        for (ExtraItemStorageVariant variant : ExtraItemStorageVariant.values()) {
            String name = variant.getName() + "_storage_block";
            ITEM_STORAGE_BLOCK.put(variant, BLOCKS.register(name, () -> new StorageBlock<>(BlockConstants.PROPERTIES, new ExtraItemStorageBlockProvider(variant))));
            ITEM_STORAGE.put(variant, ITEMS.register(name, () -> new ExtraItemStorageBlockItem(ITEM_STORAGE_BLOCK.get(variant).get(), variant)));
            ITEM_STORAGE_TILE.put(variant, TILES.register(name, () -> blockEntityTypeFactory.create((pos, state) -> RefinedStorageApi.INSTANCE.createStorageBlockEntity(pos, state, new ExtraItemStorageBlockProvider(variant)), ITEM_STORAGE_BLOCK.get(variant).get())));
            ITEM_STORAGE_CONTAINER.put(variant, CONTAINERS.register(name, () -> extendedMenuTypeFactory.create((syncId, playerInventory, data) -> RefinedStorageApi.INSTANCE.createStorageBlockContainerMenu(syncId, playerInventory.player, data, RefinedStorageApi.INSTANCE.getItemResourceFactory(), Menus.INSTANCE.getItemStorage()), RefinedStorageApi.INSTANCE.getStorageBlockDataStreamCodec())));

            ITEM_STORAGE_PART.put(variant, ITEMS.register(variant.getName() + "_storage_part", () -> new Item(new Item.Properties())));
            ITEM_STORAGE_DISK.put(variant, ITEMS.register(variant.getName() + "_storage_disk", () -> new ExtraItemStorageDiskItem(variant)));
        }

        for (ExtraFluidStorageVariant variant : ExtraFluidStorageVariant.values()) {
            String name = variant.getName() + "_fluid_storage_block";
            FLUID_STORAGE_BLOCK.put(variant, BLOCKS.register(name, () -> new StorageBlock<>(BlockConstants.PROPERTIES, new ExtraFluidStorageBlockProvider(variant))));
            FLUID_STORAGE.put(variant, ITEMS.register(name, () -> new ExtraFluidStorageBlockItem(FLUID_STORAGE_BLOCK.get(variant).get(), variant)));
            FLUID_STORAGE_TILE.put(variant, TILES.register(name, () -> blockEntityTypeFactory.create((pos, state) -> RefinedStorageApi.INSTANCE.createStorageBlockEntity(pos, state, new ExtraFluidStorageBlockProvider(variant)), FLUID_STORAGE_BLOCK.get(variant).get())));
            FLUID_STORAGE_CONTAINER.put(variant, CONTAINERS.register(name, () -> extendedMenuTypeFactory.create((syncId, playerInventory, data) -> RefinedStorageApi.INSTANCE.createStorageBlockContainerMenu(syncId, playerInventory.player, data, RefinedStorageApi.INSTANCE.getFluidResourceFactory(), Menus.INSTANCE.getFluidStorage()), RefinedStorageApi.INSTANCE.getStorageBlockDataStreamCodec())));

            FLUID_STORAGE_PART.put(variant, ITEMS.register(variant.getName() + "_fluid_storage_part", () -> new Item(new Item.Properties())));
            FLUID_STORAGE_DISK.put(variant, ITEMS.register(variant.getName() + "_fluid_storage_disk", () -> new ExtraFluidStorageDiskItem(variant)));
        }

        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        TILES.register(modBus);
        CONTAINERS.register(modBus);
    }
}
