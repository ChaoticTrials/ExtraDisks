package de.melanx.extradisks.data;

import com.refinedmods.refinedstorage.common.content.Blocks;
import com.refinedmods.refinedstorage.common.content.Items;
import com.refinedmods.refinedstorage.common.misc.ProcessorItem;
import com.refinedmods.refinedstorage.common.storage.FluidStorageVariant;
import com.refinedmods.refinedstorage.common.storage.ItemStorageVariant;
import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.Registration;
import de.melanx.extradisks.content.fluid.ExtraFluidStorageDiskItem;
import de.melanx.extradisks.content.fluid.ExtraFluidStorageVariant;
import de.melanx.extradisks.content.item.ExtraItemStorageDiskItem;
import de.melanx.extradisks.content.item.ExtraItemStorageVariant;
import de.melanx.extradisks.data.recipes.builder.StorageContainerUpgradeRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class Recipes extends RecipeProvider {

    private final HolderLookup.RegistryLookup<Item> items;

    public Recipes(@Nonnull HolderLookup.Provider registries, @Nonnull RecipeOutput output) {
        super(registries, output);
        this.items = registries.lookupOrThrow(Registries.ITEM);
    }

    @Override
    protected void buildRecipes() {
        RecipeOutput mekanismRecipeOutput = this.output.withConditions(new ModLoadedCondition("mekanism"), new ModLoadedCondition("refinedstorage_mekanism_integration"));

        for (ExtraItemStorageVariant variant : ExtraItemStorageVariant.values()) {
            this.registerDiskRecipes(Registration.ITEM_STORAGE_DISK.get(variant).get(), ModTags.Items.PARTS_ITEM.get(variant), this.output);
            this.registerStorageBlockRecipe(ModTags.Items.PARTS_ITEM.get(variant), Registration.ITEM_STORAGE_BLOCK.get(variant).get(), this.output);
        }

        for (ExtraFluidStorageVariant variant : ExtraFluidStorageVariant.values()) {
            this.registerDiskRecipes(Registration.FLUID_STORAGE_DISK.get(variant).get(), ModTags.Items.PARTS_FLUID.get(variant), this.output);
            this.registerStorageBlockRecipe(ModTags.Items.PARTS_FLUID.get(variant), Registration.FLUID_STORAGE_BLOCK.get(variant).get(), this.output);
        }

//        for (ExtraChemicalStorageVariant variant : ExtraChemicalStorageVariant.values()) { todo Mekanism
//            this.registerMekanismDiskRecipes(Registration.CHEMICAL_STORAGE_DISK.get(variant).get(), ModTags.Items.PARTS_CHEMICAL.get(variant), mekanismRecipeOutput);
//            this.registerMekanismStorageBlockRecipe(ModTags.Items.PARTS_CHEMICAL.get(variant), Registration.CHEMICAL_STORAGE_BLOCK.get(variant).get(), mekanismRecipeOutput);
//        }

        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_5).get(), Items.INSTANCE.getItemStoragePart(ItemStorageVariant.SIXTY_FOUR_K), this.output);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_6).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_5), this.output);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_7).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_6), this.output);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_8).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_7), this.output);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_9).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_8), this.output);
        this.registerAdvancedPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_10).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_9), this.output);
        this.registerAdvancedPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_11).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_10), this.output);
        this.registerAdvancedPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_12).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_11), this.output);

        this.registerPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageVariant.TIER_5_FLUID).get(), Items.INSTANCE.getFluidStoragePart(FluidStorageVariant.FOUR_THOUSAND_NINETY_SIX_B), this.output);
        this.registerPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageVariant.TIER_6_FLUID).get(), ModTags.Items.PARTS_FLUID.get(ExtraFluidStorageVariant.TIER_5_FLUID), this.output);
        this.registerPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageVariant.TIER_7_FLUID).get(), ModTags.Items.PARTS_FLUID.get(ExtraFluidStorageVariant.TIER_6_FLUID), this.output);
        this.registerAdvancedPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageVariant.TIER_8_FLUID).get(), ModTags.Items.PARTS_FLUID.get(ExtraFluidStorageVariant.TIER_7_FLUID), this.output);
        this.registerAdvancedPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageVariant.TIER_9_FLUID).get(), ModTags.Items.PARTS_FLUID.get(ExtraFluidStorageVariant.TIER_8_FLUID), this.output);

//        this.registerMekanismPartRecipe(Registration.CHEMICAL_STORAGE_PART.get(ExtraChemicalStorageVariant.TIER_5_CHEMICAL).get(), com.refinedmods.refinedstorage.mekanism.content.Items.getChemicalStoragePart(ChemicalStorageVariant.EIGHT_THOUSAND_NINETY_TWO_B), mekanismRecipeOutput);
//        this.registerMekanismPartRecipe(Registration.CHEMICAL_STORAGE_PART.get(ExtraChemicalStorageVariant.TIER_6_CHEMICAL).get(), ModTags.Items.PARTS_CHEMICAL.get(ExtraChemicalStorageVariant.TIER_5_CHEMICAL), mekanismRecipeOutput);
//        this.registerMekanismPartRecipe(Registration.CHEMICAL_STORAGE_PART.get(ExtraChemicalStorageVariant.TIER_7_CHEMICAL).get(), ModTags.Items.PARTS_CHEMICAL.get(ExtraChemicalStorageVariant.TIER_6_CHEMICAL), mekanismRecipeOutput);
//        this.registerMekanismAdvancedPartRecipe(Registration.CHEMICAL_STORAGE_PART.get(ExtraChemicalStorageVariant.TIER_8_CHEMICAL).get(), ModTags.Items.PARTS_CHEMICAL.get(ExtraChemicalStorageVariant.TIER_7_CHEMICAL), mekanismRecipeOutput);
//        this.registerMekanismAdvancedPartRecipe(Registration.CHEMICAL_STORAGE_PART.get(ExtraChemicalStorageVariant.TIER_9_CHEMICAL).get(), ModTags.Items.PARTS_CHEMICAL.get(ExtraChemicalStorageVariant.TIER_8_CHEMICAL), mekanismRecipeOutput);

        this.registerProcessorRecipe(Registration.WITHERING_PROCESSOR.get(), Registration.RAW_WITHERING_PROCESSOR.get(), Tags.Items.NETHER_STARS, this.output);

        this.registerUpgrades(this.output);

        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, Registration.ADVANCED_STORAGE_HOUSING.get())
                .pattern("GEG")
                .pattern("E E")
                .pattern("IAI")
                .define('G', Tags.Items.GLASS_BLOCKS)
                .define('E', Items.INSTANCE.getQuartzEnrichedIron())
                .define('I', Items.INSTANCE.getProcessor(ProcessorItem.Type.IMPROVED))
                .define('A', Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED))
                .unlockedBy("has_processor", this.has(Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED)))
                .save(this.output);

        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, Registration.ADVANCED_MACHINE_CASING.get())
                .pattern("DCD")
                .pattern("GBG")
                .pattern("DOD")
                .define('D', Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED))
                .define('C', Items.INSTANCE.getConstructionCore())
                .define('G', Items.INSTANCE.getProcessor(ProcessorItem.Type.IMPROVED))
                .define('B', Blocks.INSTANCE.getMachineCasing())
                .define('O', Items.INSTANCE.getDestructionCore())
                .unlockedBy("has_processor", this.has(Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED)))
                .save(this.output);
    }

    private void registerProcessorRecipe(ItemLike result, ItemLike raw, TagKey<Item> ingredient, RecipeOutput recipeOutput) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(raw), RecipeCategory.MISC, CookingBookCategory.MISC, result, 0.5F, 200)
                .unlockedBy("has_raw", this.has(raw))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, raw)
                .requires(Items.INSTANCE.getProcessorBinding())
                .requires(ingredient)
                .requires(Items.INSTANCE.getSilicon())
                .requires(Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_binding", this.has(Items.INSTANCE.getProcessorBinding()))
                .save(recipeOutput);
    }

    private void registerPartRecipe(Item result, Item prevPart, RecipeOutput recipeOutput) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, result)
                .pattern("DED")
                .pattern("PRP")
                .pattern("DPD")
                .define('D', Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED))
                .define('E', Items.INSTANCE.getQuartzEnrichedIron())
                .define('P', prevPart)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_prev_part", this.has(prevPart))
                .save(recipeOutput, ExtraDisks.MODID + ":part/" + BuiltInRegistries.ITEM.getKey(result).getPath());
    }

    private void registerPartRecipe(Item result, TagKey<Item> prevPart, RecipeOutput recipeOutput) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, result)
                .pattern("DED")
                .pattern("PRP")
                .pattern("DPD")
                .define('D', Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED))
                .define('E', Items.INSTANCE.getQuartzEnrichedIron())
                .define('P', prevPart)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_prev_part", this.has(prevPart))
                .save(recipeOutput, ExtraDisks.MODID + ":part/" + BuiltInRegistries.ITEM.getKey(result).getPath());
    }

    private void registerAdvancedPartRecipe(Item result, TagKey<Item> prevPart, RecipeOutput recipeOutput) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, result)
                .pattern("DED")
                .pattern("PRP")
                .pattern("DPD")
                .define('D', Registration.WITHERING_PROCESSOR.get())
                .define('E', Items.INSTANCE.getQuartzEnrichedIron())
                .define('P', prevPart)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_prev_part", this.has(prevPart))
                .save(recipeOutput, ExtraDisks.MODID + ":part/" + BuiltInRegistries.ITEM.getKey(result).getPath());
    }

    private void registerDiskRecipes(Item result, TagKey<Item> part, RecipeOutput recipeOutput) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, result)
                .pattern("GEG")
                .pattern("EPE")
                .pattern("IAI")
                .define('G', Tags.Items.GLASS_BLOCKS)
                .define('E', Items.INSTANCE.getQuartzEnrichedIron())
                .define('P', part)
                .define('I', Items.INSTANCE.getProcessor(ProcessorItem.Type.IMPROVED))
                .define('A', Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED))
                .unlockedBy("has_part", this.has(part))
                .save(recipeOutput, ExtraDisks.MODID + ":disk/shaped/" + BuiltInRegistries.ITEM.getKey(result).getPath());
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, result)
                .requires(Registration.ADVANCED_STORAGE_HOUSING.get())
                .requires(part)
                .unlockedBy("has_part", this.has(part))
                .save(recipeOutput, ExtraDisks.MODID + ":disk/shapeless/" + BuiltInRegistries.ITEM.getKey(result).getPath());
    }

    private void registerStorageBlockRecipe(TagKey<Item> part, ItemLike block, RecipeOutput recipeOutput) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, block)
                .pattern("QPQ")
                .pattern("QCQ")
                .pattern("QRQ")
                .define('Q', Items.INSTANCE.getQuartzEnrichedIron())
                .define('P', part)
                .define('C', Registration.ADVANCED_MACHINE_CASING.get())
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_part", this.has(part))
                .save(recipeOutput, ExtraDisks.MODID + ":blocks/" + BuiltInRegistries.ITEM.getKey(block.asItem()).getPath());
    }

//    private void registerMekanismPartRecipe(Item result, Item prevPart, RecipeOutput recipeOutput) { todo Mekanism
//        //noinspection ConstantConditions
//        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, result)
//                .pattern("DOD")
//                .pattern("PAP")
//                .pattern("DPD")
//                .define('D', Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED))
//                .define('O', ModTags.Items.OSMIUM_INGOTS)
//                .define('P', prevPart)
//                .define('A', MekanismItems.ATOMIC_ALLOY)
//                .unlockedBy("has_prev_part", this.has(prevPart))
//                .save(recipeOutput, ExtraDisks.MODID + ":part/" + BuiltInRegistries.ITEM.getKey(result).getPath());
//    }
//
//    private void registerMekanismPartRecipe(Item result, TagKey<Item> prevPart, RecipeOutput recipeOutput) {
//        //noinspection ConstantConditions
//        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, result)
//                .pattern("DOD")
//                .pattern("PAP")
//                .pattern("DPD")
//                .define('D', Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED))
//                .define('O', ModTags.Items.OSMIUM_INGOTS)
//                .define('P', prevPart)
//                .define('A', MekanismItems.ATOMIC_ALLOY)
//                .unlockedBy("has_prev_part", this.has(prevPart))
//                .save(recipeOutput, ExtraDisks.MODID + ":part/" + BuiltInRegistries.ITEM.getKey(result).getPath());
//    }
//
//    private void registerMekanismAdvancedPartRecipe(Item result, TagKey<Item> prevPart, RecipeOutput recipeOutput) {
//        //noinspection ConstantConditions
//        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, result)
//                .pattern("DOD")
//                .pattern("PAP")
//                .pattern("DPD")
//                .define('D', Registration.WITHERING_PROCESSOR.get())
//                .define('O', ModTags.Items.OSMIUM_INGOTS)
//                .define('P', prevPart)
//                .define('A', MekanismItems.ATOMIC_ALLOY)
//                .unlockedBy("has_prev_part", this.has(prevPart))
//                .save(recipeOutput, ExtraDisks.MODID + ":part/" + BuiltInRegistries.ITEM.getKey(result).getPath());
//    }

    private void registerMekanismDiskRecipes(Item result, TagKey<Item> part, RecipeOutput recipeOutput) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, result)
                .pattern("GRG")
                .pattern("RPR")
                .pattern("OOO")
                .define('G', Tags.Items.GLASS_BLOCKS)
                .define('R', Items.INSTANCE.getQuartzEnrichedIron())
                .define('P', part)
                .define('O', ModTags.Items.OSMIUM_INGOTS)
                .unlockedBy("has_part", this.has(part))
                .save(recipeOutput, ExtraDisks.MODID + ":disk/shaped/" + BuiltInRegistries.ITEM.getKey(result).getPath());
        ShapelessRecipeBuilder.shapeless(this.items, RecipeCategory.MISC, result)
                .requires(Registration.ADVANCED_STORAGE_HOUSING.get())
                .requires(part)
                .unlockedBy("has_part", this.has(part))
                .save(recipeOutput, ExtraDisks.MODID + ":disk/shapeless/" + BuiltInRegistries.ITEM.getKey(result).getPath());
    }

    private void registerMekanismStorageBlockRecipe(TagKey<Item> part, ItemLike block, RecipeOutput recipeOutput) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(this.items, RecipeCategory.MISC, block)
                .pattern("OPO")
                .pattern("OCO")
                .pattern("ORO")
                .define('O', ModTags.Items.OSMIUM_INGOTS)
                .define('P', part)
                .define('C', Registration.ADVANCED_MACHINE_CASING.get())
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_part", this.has(part))
                .save(recipeOutput, ExtraDisks.MODID + ":blocks/" + BuiltInRegistries.ITEM.getKey(block.asItem()).getPath());
    }

    private void registerUpgrades(RecipeOutput recipeOutput) {
        this.registerItemStorageUpgrades(recipeOutput);
        this.registerFluidStorageUpgrades(recipeOutput);
//        this.registerChemicalStorageUpgrades(recipeOutput); todo Mekanism
    }

    private void registerItemStorageUpgrades(RecipeOutput recipeOutput) {
        Set<ItemLike> disks = new HashSet<>();
        for (ItemStorageVariant value : ItemStorageVariant.values()) {
            if (value == ItemStorageVariant.CREATIVE) {
                continue;
            }

            disks.add(Items.INSTANCE.getItemStorageDisk(value).asItem());
        }

        for (ExtraItemStorageVariant value : ExtraItemStorageVariant.values()) {
            DeferredItem<ExtraItemStorageDiskItem> disk = Registration.ITEM_STORAGE_DISK.get(value);
            StorageContainerUpgradeRecipeBuilder.shapeless(disk)
                    .disk(Ingredient.of(disks.stream()))
                    .part(Ingredient.of(Registration.ITEM_STORAGE_PART.get(value)))
                    .save(recipeOutput);

            disks.add(disk);
        }

        Set<ItemLike> storageBlocks = new HashSet<>();
        for (ItemStorageVariant value : ItemStorageVariant.values()) {
            if (value == ItemStorageVariant.CREATIVE) {
                continue;
            }

            storageBlocks.add(Blocks.INSTANCE.getItemStorageBlock(value));
        }

        for (ExtraItemStorageVariant value : ExtraItemStorageVariant.values()) {
            DeferredBlock<Block> storageBlock = Registration.ITEM_STORAGE_BLOCK.get(value);
            StorageContainerUpgradeRecipeBuilder.shapeless(storageBlock)
                    .disk(Ingredient.of(storageBlocks.toArray(new ItemLike[0])))
                    .part(Ingredient.of(Registration.ITEM_STORAGE_PART.get(value)))
                    .save(recipeOutput);

            storageBlocks.add(storageBlock);
        }
    }

    private void registerFluidStorageUpgrades(RecipeOutput recipeOutput) {
        Set<ItemLike> disks = new HashSet<>();
        for (FluidStorageVariant value : FluidStorageVariant.values()) {
            if (value == FluidStorageVariant.CREATIVE) {
                continue;
            }

            disks.add(Items.INSTANCE.getFluidStorageDisk(value));
        }

        for (ExtraFluidStorageVariant value : ExtraFluidStorageVariant.values()) {
            DeferredItem<ExtraFluidStorageDiskItem> disk = Registration.FLUID_STORAGE_DISK.get(value);
            StorageContainerUpgradeRecipeBuilder.shapeless(disk)
                    .disk(Ingredient.of(disks.toArray(new ItemLike[0])))
                    .part(Ingredient.of(Registration.FLUID_STORAGE_PART.get(value)))
                    .save(recipeOutput);

            disks.add(disk);
        }

        Set<ItemLike> storageBlocks = new HashSet<>();
        for (FluidStorageVariant value : FluidStorageVariant.values()) {
            if (value == FluidStorageVariant.CREATIVE) {
                continue;
            }

            storageBlocks.add(Blocks.INSTANCE.getFluidStorageBlock(value));
        }

        for (ExtraFluidStorageVariant value : ExtraFluidStorageVariant.values()) {
            DeferredBlock<Block> storageBlock = Registration.FLUID_STORAGE_BLOCK.get(value);
            StorageContainerUpgradeRecipeBuilder.shapeless(storageBlock)
                    .disk(Ingredient.of(storageBlocks.toArray(new ItemLike[0])))
                    .part(Ingredient.of(Registration.FLUID_STORAGE_PART.get(value)))
                    .save(recipeOutput);

            storageBlocks.add(storageBlock);
        }
    }

//    private void registerChemicalStorageUpgrades(RecipeOutput recipeOutput) { todo Mekanism
//        Set<ItemLike> disks = new HashSet<>();
//        for (ChemicalStorageVariant value : ChemicalStorageVariant.values()) {
//            if (value == ChemicalStorageVariant.CREATIVE) {
//                continue;
//            }
//
//            disks.add(com.refinedmods.refinedstorage.mekanism.content.Items.getChemicalStorageDisk(value));
//        }
//
//        for (ExtraChemicalStorageVariant value : ExtraChemicalStorageVariant.values()) {
//            DeferredItem<ExtraChemicalStorageDiskItem> disk = Registration.CHEMICAL_STORAGE_DISK.get(value);
//            StorageContainerUpgradeRecipeBuilder.shapeless(disk)
//                    .disk(Ingredient.of(disks.toArray(new ItemLike[0])))
//                    .part(Ingredient.of(Registration.CHEMICAL_STORAGE_PART.get(value)))
//                    .save(recipeOutput);
//
//            disks.add(disk);
//        }
//
//        Set<ItemLike> storageBlocks = new HashSet<>();
//        for (ChemicalStorageVariant value : ChemicalStorageVariant.values()) {
//            if (value == ChemicalStorageVariant.CREATIVE) {
//                continue;
//            }
//
//            storageBlocks.add(com.refinedmods.refinedstorage.mekanism.content.Blocks.getChemicalStorageBlock(value));
//        }
//
//        for (ExtraChemicalStorageVariant value : ExtraChemicalStorageVariant.values()) {
//            DeferredBlock<Block> storageBlock = Registration.CHEMICAL_STORAGE_BLOCK.get(value);
//            StorageContainerUpgradeRecipeBuilder.shapeless(storageBlock)
//                    .disk(Ingredient.of(storageBlocks.toArray(new ItemLike[0])))
//                    .part(Ingredient.of(Registration.CHEMICAL_STORAGE_PART.get(value)))
//                    .save(recipeOutput);
//
//            storageBlocks.add(storageBlock);
//        }
//    }

    public static final class Runner extends RecipeProvider.Runner {

        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Nonnull
        @Override
        protected RecipeProvider createRecipeProvider(@Nonnull HolderLookup.Provider registries, @Nonnull RecipeOutput output) {
            return new Recipes(registries, output);
        }

        @Nonnull
        @Override
        public String getName() {
            return "Extra Disks recipes";
        }
    }
}