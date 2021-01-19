package de.melanx.extradisks.data;

import com.refinedmods.refinedstorage.RSItems;
import com.refinedmods.refinedstorage.apiimpl.storage.FluidStorageType;
import com.refinedmods.refinedstorage.apiimpl.storage.ItemStorageType;
import com.refinedmods.refinedstorage.item.ProcessorItem;
import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageType;
import de.melanx.extradisks.items.item.ExtraItemStorageType;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        for (ExtraItemStorageType type : ExtraItemStorageType.values()) {
            this.registerDiskRecipes(Registration.ITEM_STORAGE_DISK.get(type).get(), ModTags.Items.PARTS_ITEM.get(type), consumer);
            this.registerStorageBlockRecipe(ModTags.Items.PARTS_ITEM.get(type), Registration.ITEM_STORAGE_BLOCK.get(type).get(), consumer);
        }

        for (ExtraFluidStorageType type : ExtraFluidStorageType.values()) {
            this.registerDiskRecipes(Registration.FLUID_STORAGE_DISK.get(type).get(), ModTags.Items.PARTS_FLUID.get(type), consumer);
            this.registerStorageBlockRecipe(ModTags.Items.PARTS_FLUID.get(type), Registration.FLUID_STORAGE_BLOCK.get(type).get(), consumer);
        }

        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_5).get(), RSItems.ITEM_STORAGE_PARTS.get(ItemStorageType.SIXTY_FOUR_K).get(), consumer);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_6).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageType.TIER_5), consumer);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_7).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageType.TIER_6), consumer);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_8).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageType.TIER_7), consumer);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_9).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageType.TIER_8), consumer);
        this.registerAdvancedPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_10).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageType.TIER_9), consumer);
        this.registerAdvancedPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_11).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageType.TIER_10), consumer);
        this.registerAdvancedPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_12).get(), ModTags.Items.PARTS_ITEM.get(ExtraItemStorageType.TIER_11), consumer);

        this.registerPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageType.TIER_5_FLUID).get(), RSItems.FLUID_STORAGE_PARTS.get(FluidStorageType.FOUR_THOUSAND_NINETY_SIX_K).get(), consumer);
        this.registerPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageType.TIER_6_FLUID).get(), ModTags.Items.PARTS_FLUID.get(ExtraFluidStorageType.TIER_5_FLUID), consumer);
        this.registerPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageType.TIER_7_FLUID).get(), ModTags.Items.PARTS_FLUID.get(ExtraFluidStorageType.TIER_6_FLUID), consumer);
        this.registerAdvancedPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageType.TIER_8_FLUID).get(), ModTags.Items.PARTS_FLUID.get(ExtraFluidStorageType.TIER_7_FLUID), consumer);
        this.registerAdvancedPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageType.TIER_9_FLUID).get(), ModTags.Items.PARTS_FLUID.get(ExtraFluidStorageType.TIER_8_FLUID), consumer);

        this.registerProcessorRecipe(Registration.WITHERING_PROCESSOR.get(), Registration.RAW_WITHERING_PROCESSOR.get(), Ingredient.fromTag(Tags.Items.NETHER_STARS), consumer);

        ShapedRecipeBuilder.shapedRecipe(Registration.ADVANCED_STORAGE_HOUSING.get())
                .patternLine("GEG")
                .patternLine("E E")
                .patternLine("IAI")
                .key('G', Tags.Items.GLASS)
                .key('E', RSItems.QUARTZ_ENRICHED_IRON.get())
                .key('I', RSItems.PROCESSORS.get(ProcessorItem.Type.IMPROVED).get())
                .key('A', RSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get())
                .addCriterion("has_processor", hasItem(RSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(Registration.ADVANCED_MACHINE_CASING.get())
                .patternLine("DCD")
                .patternLine("GBG")
                .patternLine("DOD")
                .key('D', RSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get())
                .key('C', RSItems.CONSTRUCTION_CORE.get())
                .key('G', RSItems.PROCESSORS.get(ProcessorItem.Type.IMPROVED).get())
                .key('B', RSItems.MACHINE_CASING.get())
                .key('O', RSItems.DESTRUCTION_CORE.get())
                .addCriterion("has_processor", hasItem(RSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get()))
                .build(consumer);
    }

    private void registerProcessorRecipe(IItemProvider result, IItemProvider raw, Ingredient ingredient, Consumer<IFinishedRecipe> consumer) {
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(raw), result, 0.5F, 200)
                .addCriterion("has_raw", hasItem(raw))
                .build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(raw)
                .addIngredient(RSItems.PROCESSOR_BINDING.get())
                .addIngredient(ingredient)
                .addIngredient(RSItems.SILICON.get())
                .addIngredient(Tags.Items.DUSTS_REDSTONE)
                .addCriterion("has_binding", hasItem(RSItems.PROCESSOR_BINDING.get()))
                .build(consumer);
    }

    private void registerPartRecipe(Item result, Item prevPart, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(result)
                .patternLine("DED")
                .patternLine("PRP")
                .patternLine("DPD")
                .key('D', RSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get())
                .key('E', RSItems.QUARTZ_ENRICHED_IRON.get())
                .key('P', prevPart)
                .key('R', Tags.Items.DUSTS_REDSTONE)
                .addCriterion("has_prev_part", hasItem(prevPart))
                .build(consumer, new ResourceLocation(ExtraDisks.MODID, "part/" + result.getRegistryName().getPath()));
    }

    private void registerPartRecipe(Item result, ITag.INamedTag<Item> prevPart, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(result)
                .patternLine("DED")
                .patternLine("PRP")
                .patternLine("DPD")
                .key('D', RSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get())
                .key('E', RSItems.QUARTZ_ENRICHED_IRON.get())
                .key('P', prevPart)
                .key('R', Tags.Items.DUSTS_REDSTONE)
                .addCriterion("has_prev_part", hasItem(prevPart))
                .build(consumer, new ResourceLocation(ExtraDisks.MODID, "part/" + result.getRegistryName().getPath()));
    }

    private void registerAdvancedPartRecipe(Item result, ITag.INamedTag<Item> prevPart, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(result)
                .patternLine("DED")
                .patternLine("PRP")
                .patternLine("DPD")
                .key('D', Registration.WITHERING_PROCESSOR.get())
                .key('E', RSItems.QUARTZ_ENRICHED_IRON.get())
                .key('P', prevPart)
                .key('R', Tags.Items.DUSTS_REDSTONE)
                .addCriterion("has_prev_part", hasItem(prevPart))
                .build(consumer, new ResourceLocation(ExtraDisks.MODID, "part/" + result.getRegistryName().getPath()));
    }

    private void registerDiskRecipes(Item result, ITag.INamedTag<Item> part, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(result)
                .patternLine("GEG")
                .patternLine("EPE")
                .patternLine("IAI")
                .key('G', Tags.Items.GLASS)
                .key('E', RSItems.QUARTZ_ENRICHED_IRON.get())
                .key('P', part)
                .key('I', RSItems.PROCESSORS.get(ProcessorItem.Type.IMPROVED).get())
                .key('A', RSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get())
                .addCriterion("has_part", hasItem(part))
                .build(consumer, new ResourceLocation(ExtraDisks.MODID, "disk/shaped/" + result.getRegistryName().getPath()));
        ShapelessRecipeBuilder.shapelessRecipe(result)
                .addIngredient(Registration.ADVANCED_STORAGE_HOUSING.get())
                .addIngredient(part)
                .addCriterion("has_part", hasItem(part))
                .build(consumer, new ResourceLocation(ExtraDisks.MODID, "disk/shapeless/" + result.getRegistryName().getPath()));
    }

    private void registerStorageBlockRecipe(ITag.INamedTag<Item> part, IItemProvider block, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(block)
                .patternLine("QPQ")
                .patternLine("QCQ")
                .patternLine("QRQ")
                .key('Q', RSItems.QUARTZ_ENRICHED_IRON.get())
                .key('P', part)
                .key('C', Registration.ADVANCED_MACHINE_CASING.get())
                .key('R', Tags.Items.DUSTS_REDSTONE)
                .addCriterion("has_part", hasItem(part))
                .build(consumer, new ResourceLocation(ExtraDisks.MODID, "blocks/" + block.asItem().getRegistryName().getPath()));
    }
}