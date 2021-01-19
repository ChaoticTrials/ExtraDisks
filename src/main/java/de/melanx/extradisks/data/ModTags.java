package de.melanx.extradisks.data;

import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageType;
import de.melanx.extradisks.items.item.ExtraItemStorageType;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;

import java.util.HashMap;
import java.util.Map;

public class ModTags {

    public static class Blocks {
        public static final ITag.INamedTag<Block> STORAGE_BLOCKS = tag("storage_blocks");
        public static final ITag.INamedTag<Block> ITEM_STORAGE_BLOCKS = tag("storage_blocks/items");
        public static final ITag.INamedTag<Block> FLUID_STORAGE_BLOCKS = tag("storage_blocks/fluids");
        public static final Map<ExtraItemStorageType, ITag.INamedTag<Block>> STORAGE_BLOCKS_ITEM = new HashMap<>();
        public static final Map<ExtraFluidStorageType, ITag.INamedTag<Block>> STORAGE_BLOCKS_FLUID = new HashMap<>();

        static {
            for (ExtraItemStorageType type : ExtraItemStorageType.values()) {
                STORAGE_BLOCKS_ITEM.put(type, tag("storage_blocks/items/" + type.getName()));
            }
            for (ExtraFluidStorageType type : ExtraFluidStorageType.values()) {
                STORAGE_BLOCKS_FLUID.put(type, tag("storage_blocks/fluids/" + type.getName()));
            }
        }

        private static ITag.INamedTag<Block> tag(String name) {
            return net.minecraft.tags.BlockTags.makeWrapperTag("refinedstorage:" + name);
        }
    }

    public static class Items {
        public static final ITag.INamedTag<Item> STORAGE_BLOCKS = tag("storage_blocks");
        public static final ITag.INamedTag<Item> ITEM_STORAGE_BLOCKS = tag("storage_blocks/items");
        public static final ITag.INamedTag<Item> FLUID_STORAGE_BLOCKS = tag("storage_blocks/fluids");
        public static final Map<ExtraItemStorageType, ITag.INamedTag<Item>> STORAGE_BLOCKS_ITEM = new HashMap<>();
        public static final Map<ExtraFluidStorageType, ITag.INamedTag<Item>> STORAGE_BLOCKS_FLUID = new HashMap<>();

        public static final ITag.INamedTag<Item> PARTS = tag("parts");
        public static final ITag.INamedTag<Item> ITEM_PARTS = tag("parts/items");
        public static final ITag.INamedTag<Item> FLUID_PARTS = tag("parts/fluids");
        public static final Map<ExtraItemStorageType, ITag.INamedTag<Item>> PARTS_ITEM = new HashMap<>();
        public static final Map<ExtraFluidStorageType, ITag.INamedTag<Item>> PARTS_FLUID = new HashMap<>();

        public static final ITag.INamedTag<Item> DISKS = tag("disks");
        public static final ITag.INamedTag<Item> ITEM_DISKS = tag("disks/items");
        public static final ITag.INamedTag<Item> FLUID_DISKS = tag("disks/fluids");
        public static final Map<ExtraItemStorageType, ITag.INamedTag<Item>> DISKS_ITEM = new HashMap<>();
        public static final Map<ExtraFluidStorageType, ITag.INamedTag<Item>> DISKS_FLUID = new HashMap<>();

        static {
            for (ExtraItemStorageType type : ExtraItemStorageType.values()) {
                STORAGE_BLOCKS_ITEM.put(type, tag("storage_blocks/items/" + type.getName()));
                PARTS_ITEM.put(type, tag("parts/items/" + type.getName()));
                DISKS_ITEM.put(type, tag("disks/items/" + type.getName()));
            }
            for (ExtraFluidStorageType type : ExtraFluidStorageType.values()) {
                STORAGE_BLOCKS_FLUID.put(type, tag("storage_blocks/fluids/" + type.getName()));
                PARTS_FLUID.put(type, tag("parts/fluids/" + type.getName()));
                DISKS_FLUID.put(type, tag("disks/fluids/" + type.getName()));
            }
        }

        private static ITag.INamedTag<Item> tag(String name) {
            return net.minecraft.tags.ItemTags.makeWrapperTag("refinedstorage:" + name);
        }
    }

    public static class BlockTags extends BlockTagsProvider {
        public BlockTags(DataGenerator generator) {
            super(generator, ExtraDisks.MODID, null);
        }

        @Override
        protected void registerTags() {
            Builder<Block> itemBlocksBuilder = this.getOrCreateBuilder(Blocks.ITEM_STORAGE_BLOCKS);
            for (ExtraItemStorageType type : ExtraItemStorageType.values()) {
                ITag.INamedTag<Block> tag = Blocks.STORAGE_BLOCKS_ITEM.get(type);
                this.getOrCreateBuilder(tag).add(Registration.ITEM_STORAGE_BLOCK.get(type).get());
                itemBlocksBuilder.addTag(tag);
            }

            Builder<Block> fluidBlocksBuilder = this.getOrCreateBuilder(Blocks.FLUID_STORAGE_BLOCKS);
            for (ExtraFluidStorageType type : ExtraFluidStorageType.values()) {
                ITag.INamedTag<Block> tag = Blocks.STORAGE_BLOCKS_FLUID.get(type);
                this.getOrCreateBuilder(tag).add(Registration.FLUID_STORAGE_BLOCK.get(type).get());
                fluidBlocksBuilder.addTag(tag);
            }

            //noinspection unchecked
            this.getOrCreateBuilder(Blocks.STORAGE_BLOCKS).addTags(Blocks.ITEM_STORAGE_BLOCKS, Blocks.FLUID_STORAGE_BLOCKS);
        }
    }

    public static class ItemTags extends ItemTagsProvider {
        public ItemTags(DataGenerator generator, BlockTagsProvider provider) {
            super(generator, provider, ExtraDisks.MODID, null);
        }

        @Override
        protected void registerTags() {
            Builder<Item> itemPartsBuilder = this.getOrCreateBuilder(Items.ITEM_PARTS);
            Builder<Item> itemDisksBuilder = this.getOrCreateBuilder(Items.ITEM_DISKS);
            for (ExtraItemStorageType type : ExtraItemStorageType.values()) {
                ITag.INamedTag<Item> tag = Items.PARTS_ITEM.get(type);
                this.getOrCreateBuilder(tag).add(Registration.ITEM_STORAGE_PART.get(type).get());
                itemPartsBuilder.addTag(tag);

                tag = Items.DISKS_ITEM.get(type);
                this.getOrCreateBuilder(tag).add(Registration.ITEM_STORAGE_DISK.get(type).get());
                itemDisksBuilder.addTag(tag);
            }

            Builder<Item> fluidPartsBuilder = this.getOrCreateBuilder(Items.FLUID_PARTS);
            Builder<Item> fluidDisksBuilder = this.getOrCreateBuilder(Items.FLUID_DISKS);
            for (ExtraFluidStorageType type : ExtraFluidStorageType.values()) {
                ITag.INamedTag<Item> tag = Items.PARTS_FLUID.get(type);
                this.getOrCreateBuilder(tag).add(Registration.FLUID_STORAGE_PART.get(type).get());
                fluidPartsBuilder.addTag(tag);

                tag = Items.DISKS_FLUID.get(type);
                this.getOrCreateBuilder(tag).add(Registration.FLUID_STORAGE_DISK.get(type).get());
                fluidDisksBuilder.addTag(tag);
            }

            //noinspection unchecked
            this.getOrCreateBuilder(Items.PARTS).addTags(Items.ITEM_PARTS, Items.FLUID_PARTS);
            //noinspection unchecked
            this.getOrCreateBuilder(Items.DISKS).addTags(Items.ITEM_DISKS, Items.FLUID_DISKS);

            // blocks
            this.copy(Blocks.ITEM_STORAGE_BLOCKS, Items.ITEM_STORAGE_BLOCKS);
            for (ExtraItemStorageType type : ExtraItemStorageType.values()) {
                this.copy(Blocks.STORAGE_BLOCKS_ITEM.get(type), Items.STORAGE_BLOCKS_ITEM.get(type));
            }

            this.copy(Blocks.FLUID_STORAGE_BLOCKS, Items.FLUID_STORAGE_BLOCKS);
            for (ExtraFluidStorageType type : ExtraFluidStorageType.values()) {
                this.copy(Blocks.STORAGE_BLOCKS_FLUID.get(type), Items.STORAGE_BLOCKS_FLUID.get(type));
            }

            this.copy(Blocks.STORAGE_BLOCKS, Items.STORAGE_BLOCKS);
        }
    }
}
