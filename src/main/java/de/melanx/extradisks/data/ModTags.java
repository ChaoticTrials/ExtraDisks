package de.melanx.extradisks.data;

import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.Registration;
import de.melanx.extradisks.content.fluid.ExtraFluidStorageVariant;
import de.melanx.extradisks.content.item.ExtraItemStorageVariant;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModTags {

    public static class Blocks {
        public static final TagKey<Block> STORAGE_BLOCKS = tag("storage_blocks");
        public static final TagKey<Block> ITEM_STORAGE_BLOCKS = tag("storage_blocks/items");
        public static final TagKey<Block> FLUID_STORAGE_BLOCKS = tag("storage_blocks/fluids");
        public static final Map<ExtraItemStorageVariant, TagKey<Block>> STORAGE_BLOCKS_ITEM = new HashMap<>();
        public static final Map<ExtraFluidStorageVariant, TagKey<Block>> STORAGE_BLOCKS_FLUID = new HashMap<>();

        static {
            for (ExtraItemStorageVariant variant : ExtraItemStorageVariant.values()) {
                STORAGE_BLOCKS_ITEM.put(variant, tag("storage_blocks/items/" + variant.getName()));
            }
            for (ExtraFluidStorageVariant variant : ExtraFluidStorageVariant.values()) {
                STORAGE_BLOCKS_FLUID.put(variant, tag("storage_blocks/fluids/" + variant.getName()));
            }
        }

        private static TagKey<Block> tag(String name) {
            return net.minecraft.tags.BlockTags.create(ResourceLocation.fromNamespaceAndPath("refinedstorage", name));
        }
    }

    public static class Items {
        public static final TagKey<Item> STORAGE_BLOCKS = tag("storage_blocks");
        public static final TagKey<Item> ITEM_STORAGE_BLOCKS = tag("storage_blocks/items");
        public static final TagKey<Item> FLUID_STORAGE_BLOCKS = tag("storage_blocks/fluids");
        public static final Map<ExtraItemStorageVariant, TagKey<Item>> STORAGE_BLOCKS_ITEM = new HashMap<>();
        public static final Map<ExtraFluidStorageVariant, TagKey<Item>> STORAGE_BLOCKS_FLUID = new HashMap<>();

        public static final TagKey<Item> PARTS = tag("parts");
        public static final TagKey<Item> ITEM_PARTS = tag("parts/items");
        public static final TagKey<Item> FLUID_PARTS = tag("parts/fluids");
        public static final Map<ExtraItemStorageVariant, TagKey<Item>> PARTS_ITEM = new HashMap<>();
        public static final Map<ExtraFluidStorageVariant, TagKey<Item>> PARTS_FLUID = new HashMap<>();

        public static final TagKey<Item> DISKS = tag("disks");
        public static final TagKey<Item> ITEM_DISKS = tag("disks/items");
        public static final TagKey<Item> FLUID_DISKS = tag("disks/fluids");
        public static final Map<ExtraItemStorageVariant, TagKey<Item>> DISKS_ITEM = new HashMap<>();
        public static final Map<ExtraFluidStorageVariant, TagKey<Item>> DISKS_FLUID = new HashMap<>();

        static {
            for (ExtraItemStorageVariant variant : ExtraItemStorageVariant.values()) {
                STORAGE_BLOCKS_ITEM.put(variant, tag("storage_blocks/items/" + variant.getName()));
                PARTS_ITEM.put(variant, tag("parts/items/" + variant.getName()));
                DISKS_ITEM.put(variant, tag("disks/items/" + variant.getName()));
            }
            for (ExtraFluidStorageVariant variant : ExtraFluidStorageVariant.values()) {
                STORAGE_BLOCKS_FLUID.put(variant, tag("storage_blocks/fluids/" + variant.getName()));
                PARTS_FLUID.put(variant, tag("parts/fluids/" + variant.getName()));
                DISKS_FLUID.put(variant, tag("disks/fluids/" + variant.getName()));
            }
        }

        private static TagKey<Item> tag(String name) {
            return net.minecraft.tags.ItemTags.create(ResourceLocation.fromNamespaceAndPath("refinedstorage", name));
        }
    }

    public static class BlockTags extends BlockTagsProvider {
        public BlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper helper) {
            super(output, lookupProvider, ExtraDisks.MODID, helper);
        }

        @Override
        protected void addTags(@Nonnull HolderLookup.Provider provider) {
            TagAppender<Block> itemBlocksBuilder = this.tag(Blocks.ITEM_STORAGE_BLOCKS);
            for (ExtraItemStorageVariant variant : ExtraItemStorageVariant.values()) {
                TagKey<Block> tag = Blocks.STORAGE_BLOCKS_ITEM.get(variant);
                this.tag(tag).add(Registration.ITEM_STORAGE_BLOCK.get(variant).get());
                itemBlocksBuilder.addTag(tag);
            }

            TagAppender<Block> fluidBlocksBuilder = this.tag(Blocks.FLUID_STORAGE_BLOCKS);
            for (ExtraFluidStorageVariant variant : ExtraFluidStorageVariant.values()) {
                TagKey<Block> tag = Blocks.STORAGE_BLOCKS_FLUID.get(variant);
                this.tag(tag).add(Registration.FLUID_STORAGE_BLOCK.get(variant).get());
                fluidBlocksBuilder.addTag(tag);
            }

            //noinspection unchecked
            this.tag(Blocks.STORAGE_BLOCKS).addTags(Blocks.ITEM_STORAGE_BLOCKS, Blocks.FLUID_STORAGE_BLOCKS);
        }
    }

    public static class ItemTags extends ItemTagsProvider {
        public ItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTags, ExistingFileHelper helper) {
            super(output, lookupProvider, blockTags, ExtraDisks.MODID, helper);
        }

        @Override
        protected void addTags(@Nonnull HolderLookup.Provider provider) {
            TagAppender<Item> itemPartsBuilder = this.tag(Items.ITEM_PARTS);
            TagAppender<Item> itemDisksBuilder = this.tag(Items.ITEM_DISKS);
            for (ExtraItemStorageVariant variant : ExtraItemStorageVariant.values()) {
                TagKey<Item> tag = Items.PARTS_ITEM.get(variant);
                this.tag(tag).add(Registration.ITEM_STORAGE_PART.get(variant).get());
                itemPartsBuilder.addTag(tag);

                tag = Items.DISKS_ITEM.get(variant);
                this.tag(tag).add(Registration.ITEM_STORAGE_DISK.get(variant).get());
                itemDisksBuilder.addTag(tag);
            }

            TagAppender<Item> fluidPartsBuilder = this.tag(Items.FLUID_PARTS);
            TagAppender<Item> fluidDisksBuilder = this.tag(Items.FLUID_DISKS);
            for (ExtraFluidStorageVariant variant : ExtraFluidStorageVariant.values()) {
                TagKey<Item> tag = Items.PARTS_FLUID.get(variant);
                this.tag(tag).add(Registration.FLUID_STORAGE_PART.get(variant).get());
                fluidPartsBuilder.addTag(tag);

                tag = Items.DISKS_FLUID.get(variant);
                this.tag(tag).add(Registration.FLUID_STORAGE_DISK.get(variant).get());
                fluidDisksBuilder.addTag(tag);
            }

            //noinspection unchecked
            this.tag(Items.PARTS).addTags(Items.ITEM_PARTS, Items.FLUID_PARTS);
            //noinspection unchecked
            this.tag(Items.DISKS).addTags(Items.ITEM_DISKS, Items.FLUID_DISKS);

            // blocks
            this.copy(Blocks.ITEM_STORAGE_BLOCKS, Items.ITEM_STORAGE_BLOCKS);
            for (ExtraItemStorageVariant variant : ExtraItemStorageVariant.values()) {
                this.copy(Blocks.STORAGE_BLOCKS_ITEM.get(variant), Items.STORAGE_BLOCKS_ITEM.get(variant));
            }

            this.copy(Blocks.FLUID_STORAGE_BLOCKS, Items.FLUID_STORAGE_BLOCKS);
            for (ExtraFluidStorageVariant variant : ExtraFluidStorageVariant.values()) {
                this.copy(Blocks.STORAGE_BLOCKS_FLUID.get(variant), Items.STORAGE_BLOCKS_FLUID.get(variant));
            }

            this.copy(Blocks.STORAGE_BLOCKS, Items.STORAGE_BLOCKS);
        }
    }
}
