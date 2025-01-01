package de.melanx.extradisks.data;

import com.refinedmods.refinedstorage.common.content.Blocks;
import com.refinedmods.refinedstorage.common.content.Items;
import com.refinedmods.refinedstorage.common.misc.ProcessorItem;
import com.refinedmods.refinedstorage.common.storage.ItemStorageVariant;
import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.Registration;
import de.melanx.extradisks.content.fluid.ExtraFluidStorageVariant;
import de.melanx.extradisks.content.item.ExtraItemStorageVariant;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class Recipes extends RecipeProvider {

    public Recipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@Nonnull RecipeOutput recipeOutput, @Nonnull HolderLookup.Provider holderLookup) {
        for (ExtraItemStorageVariant variant : ExtraItemStorageVariant.values()) {
            this.registerDiskRecipes(Registration.ITEM_STORAGE_DISK.get(variant).get(), ModTags.Items.PARTS_ITEM.get(variant), recipeOutput);
            this.registerStorageBlockRecipe(ModTags.Items.PARTS_ITEM.get(variant), Registration.ITEM_STORAGE_BLOCK.get(variant).get(), recipeOutput);
        }

        for (ExtraFluidStorageVariant variant : ExtraFluidStorageVariant.values()) {
            this.registerDiskRecipes(Registration.FLUID_STORAGE_DISK.get(variant).get(), ModTags.Items.PARTS_FLUID.get(variant), recipeOutput);
            this.registerStorageBlockRecipe(ModTags.Items.PARTS_FLUID.get(variant), Registration.FLUID_STORAGE_BLOCK.get(variant).get(), recipeOutput);
        }

        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_5).get(), Items.INSTANCE.getItemStoragePart(ItemStorageVariant.SIXTY_FOUR_K), recipeOutput);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_6).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_5), recipeOutput);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_7).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_6), recipeOutput);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_8).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_7), recipeOutput);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_9).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_8), recipeOutput);
        this.registerAdvancedPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_10).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_9), recipeOutput);
        this.registerAdvancedPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_11).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_10), recipeOutput);
        this.registerAdvancedPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageVariant.TIER_12).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageVariant.TIER_11), recipeOutput);

        this.registerPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageVariant.TIER_5_FLUID).get(), Items.INSTANCE.getItemStoragePart(ItemStorageVariant.SIXTY_FOUR_K), recipeOutput);
        this.registerPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageVariant.TIER_6_FLUID).get(), ModTags.Items.PARTS_FLUID.get(ExtraFluidStorageVariant.TIER_5_FLUID), recipeOutput);
        this.registerPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageVariant.TIER_7_FLUID).get(), ModTags.Items.PARTS_FLUID.get(ExtraFluidStorageVariant.TIER_6_FLUID), recipeOutput);
        this.registerAdvancedPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageVariant.TIER_8_FLUID).get(), ModTags.Items.PARTS_FLUID.get(ExtraFluidStorageVariant.TIER_7_FLUID), recipeOutput);
        this.registerAdvancedPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageVariant.TIER_9_FLUID).get(), ModTags.Items.PARTS_FLUID.get(ExtraFluidStorageVariant.TIER_8_FLUID), recipeOutput);

        this.registerProcessorRecipe(Registration.WITHERING_PROCESSOR.get(), Registration.RAW_WITHERING_PROCESSOR.get(), Ingredient.of(Tags.Items.NETHER_STARS), recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.ADVANCED_STORAGE_HOUSING.get())
                .pattern("GEG")
                .pattern("E E")
                .pattern("IAI")
                .define('G', Tags.Items.GLASS_BLOCKS)
                .define('E', Items.INSTANCE.getQuartzEnrichedIron())
                .define('I', Items.INSTANCE.getProcessor(ProcessorItem.Type.IMPROVED))
                .define('A', Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED))
                .unlockedBy("has_processor", has(Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED)))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.ADVANCED_MACHINE_CASING.get())
                .pattern("DCD")
                .pattern("GBG")
                .pattern("DOD")
                .define('D', Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED))
                .define('C', Items.INSTANCE.getConstructionCore())
                .define('G', Items.INSTANCE.getProcessor(ProcessorItem.Type.IMPROVED))
                .define('B', Blocks.INSTANCE.getMachineCasing())
                .define('O', Items.INSTANCE.getDestructionCore())
                .unlockedBy("has_processor", has(Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED)))
                .save(recipeOutput);
    }

    private void registerProcessorRecipe(ItemLike result, ItemLike raw, Ingredient ingredient, RecipeOutput recipeOutput) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(raw), RecipeCategory.MISC, result, 0.5F, 200)
                .unlockedBy("has_raw", has(raw))
                .save(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, raw)
                .requires(Items.INSTANCE.getProcessorBinding())
                .requires(ingredient)
                .requires(Items.INSTANCE.INSTANCE.getSilicon())
                .requires(Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_binding", has(Items.INSTANCE.getProcessorBinding()))
                .save(recipeOutput);
    }

    private void registerPartRecipe(Item result, Item prevPart, RecipeOutput recipeOutput) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .pattern("DED")
                .pattern("PRP")
                .pattern("DPD")
                .define('D', Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED))
                .define('E', Items.INSTANCE.getQuartzEnrichedIron())
                .define('P', prevPart)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_prev_part", has(prevPart))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "part/" + BuiltInRegistries.ITEM.getKey(result).getPath()));
    }

    private void registerPartRecipe(Item result, TagKey<Item> prevPart, RecipeOutput recipeOutput) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .pattern("DED")
                .pattern("PRP")
                .pattern("DPD")
                .define('D', Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED))
                .define('E', Items.INSTANCE.getQuartzEnrichedIron())
                .define('P', prevPart)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_prev_part", has(prevPart))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "part/" + BuiltInRegistries.ITEM.getKey(result).getPath()));
    }

    private void registerAdvancedPartRecipe(Item result, TagKey<Item> prevPart, RecipeOutput recipeOutput) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .pattern("DED")
                .pattern("PRP")
                .pattern("DPD")
                .define('D', Registration.WITHERING_PROCESSOR.get())
                .define('E', Items.INSTANCE.getQuartzEnrichedIron())
                .define('P', prevPart)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_prev_part", has(prevPart))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "part/" + BuiltInRegistries.ITEM.getKey(result).getPath()));
    }

    private void registerDiskRecipes(Item result, TagKey<Item> part, RecipeOutput recipeOutput) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .pattern("GEG")
                .pattern("EPE")
                .pattern("IAI")
                .define('G', Tags.Items.GLASS_BLOCKS)
                .define('E', Items.INSTANCE.getQuartzEnrichedIron())
                .define('P', part)
                .define('I', Items.INSTANCE.getProcessor(ProcessorItem.Type.IMPROVED))
                .define('A', Items.INSTANCE.getProcessor(ProcessorItem.Type.ADVANCED))
                .unlockedBy("has_part", has(part))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "disk/shaped/" + BuiltInRegistries.ITEM.getKey(result).getPath()));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
                .requires(Registration.ADVANCED_STORAGE_HOUSING.get())
                .requires(part)
                .unlockedBy("has_part", has(part))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "disk/shapeless/" + BuiltInRegistries.ITEM.getKey(result).getPath()));
    }

    private void registerStorageBlockRecipe(TagKey<Item> part, ItemLike block, RecipeOutput recipeOutput) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, block)
                .pattern("QPQ")
                .pattern("QCQ")
                .pattern("QRQ")
                .define('Q', Items.INSTANCE.getQuartzEnrichedIron())
                .define('P', part)
                .define('C', Registration.ADVANCED_MACHINE_CASING.get())
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_part", has(part))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "blocks/" + BuiltInRegistries.ITEM.getKey(block.asItem()).getPath()));
    }
}
