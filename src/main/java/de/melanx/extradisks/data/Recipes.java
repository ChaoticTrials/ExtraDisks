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
import net.minecraft.item.crafting.ShapelessRecipe;
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
            this.registerDiskRecipes(Registration.ITEM_STORAGE_DISK.get(type).get(), Registration.ITEM_STORAGE_PART.get(type).get(), consumer);
            this.registerStorageBlockRecipe(Registration.ITEM_STORAGE_PART.get(type).get(), Registration.ITEM_STORAGE_BLOCK.get(type).get(), consumer);
        }

        for (ExtraFluidStorageType type : ExtraFluidStorageType.values()) {
            this.registerDiskRecipes(Registration.FLUID_STORAGE_DISK.get(type).get(), Registration.FLUID_STORAGE_PART.get(type).get(), consumer);
            this.registerStorageBlockRecipe(Registration.FLUID_STORAGE_PART.get(type).get(), Registration.FLUID_STORAGE_BLOCK.get(type).get(), consumer);
        }

        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_5).get(), RSItems.ITEM_STORAGE_PARTS.get(ItemStorageType.SIXTY_FOUR_K).get(), consumer);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_6).get(), Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_5).get(), consumer);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_7).get(), Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_6).get(), consumer);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_8).get(), Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_7).get(), consumer);
        this.registerPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_9).get(), Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_8).get(), consumer);
        this.registerAdvancedPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_10).get(), Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_9).get(), consumer);
        this.registerAdvancedPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_11).get(), Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_10).get(), consumer);
        this.registerAdvancedPartRecipe(Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_12).get(), Registration.ITEM_STORAGE_PART.get(ExtraItemStorageType.TIER_11).get(), consumer);

        this.registerPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageType.TIER_5_FLUID).get(), RSItems.FLUID_STORAGE_PARTS.get(FluidStorageType.FOUR_THOUSAND_NINETY_SIX_K).get(), consumer);
        this.registerPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageType.TIER_6_FLUID).get(), Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageType.TIER_5_FLUID).get(), consumer);
        this.registerPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageType.TIER_7_FLUID).get(), Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageType.TIER_6_FLUID).get(), consumer);
        this.registerAdvancedPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageType.TIER_8_FLUID).get(), Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageType.TIER_7_FLUID).get(), consumer);
        this.registerAdvancedPartRecipe(Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageType.TIER_9_FLUID).get(), Registration.FLUID_STORAGE_PART.get(ExtraFluidStorageType.TIER_8_FLUID).get(), consumer);

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

    private void registerAdvancedPartRecipe(Item result, Item prevPart, Consumer<IFinishedRecipe> consumer) {
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

    private void registerDiskRecipes(Item result, Item part, Consumer<IFinishedRecipe> consumer) {
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

    private void registerStorageBlockRecipe(IItemProvider part, IItemProvider block, Consumer<IFinishedRecipe> consumer) {
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
