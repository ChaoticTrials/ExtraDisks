package de.melanx.extradisks.data;

import com.refinedmods.refinedstorage.RSItems;
import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.items.ExtraItems;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider {
    public Recipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        registerDiskRecipes(ExtraItems.TIER_5_DISK.get(), ExtraItems.TIER_5_PART.get(), consumer);
        registerDiskRecipes(ExtraItems.TIER_6_DISK.get(), ExtraItems.TIER_6_PART.get(), consumer);
        registerDiskRecipes(ExtraItems.TIER_7_DISK.get(), ExtraItems.TIER_7_PART.get(), consumer);
        registerDiskRecipes(ExtraItems.TIER_8_DISK.get(), ExtraItems.TIER_8_PART.get(), consumer);
        registerDiskRecipes(ExtraItems.TIER_9_DISK.get(), ExtraItems.TIER_9_PART.get(), consumer);
        registerDiskRecipes(ExtraItems.TIER_10_DISK.get(), ExtraItems.TIER_10_PART.get(), consumer);
        registerDiskRecipes(ExtraItems.TIER_11_DISK.get(), ExtraItems.TIER_11_PART.get(), consumer);
        registerDiskRecipes(ExtraItems.TIER_12_DISK.get(), ExtraItems.TIER_12_PART.get(), consumer);

        registerDiskRecipes(ExtraItems.TIER_5_FLUID_DISK.get(), ExtraItems.TIER_5_FLUID_PART.get(), consumer);
        registerDiskRecipes(ExtraItems.TIER_6_FLUID_DISK.get(), ExtraItems.TIER_6_FLUID_PART.get(), consumer);
        registerDiskRecipes(ExtraItems.TIER_7_FLUID_DISK.get(), ExtraItems.TIER_7_FLUID_PART.get(), consumer);
        registerDiskRecipes(ExtraItems.TIER_8_FLUID_DISK.get(), ExtraItems.TIER_8_FLUID_PART.get(), consumer);
        registerDiskRecipes(ExtraItems.TIER_9_FLUID_DISK.get(), ExtraItems.TIER_9_FLUID_PART.get(), consumer);

        registerPartRecipe(ExtraItems.TIER_5_PART.get(), RSItems.SIXTY_FOUR_K_STORAGE_PART, consumer);
        registerPartRecipe(ExtraItems.TIER_6_PART.get(), ExtraItems.TIER_5_PART.get(), consumer);
        registerPartRecipe(ExtraItems.TIER_7_PART.get(), ExtraItems.TIER_6_PART.get(), consumer);
        registerPartRecipe(ExtraItems.TIER_8_PART.get(), ExtraItems.TIER_7_PART.get(), consumer);
        registerPartRecipe(ExtraItems.TIER_9_PART.get(), ExtraItems.TIER_8_PART.get(), consumer);
        registerPartRecipe(ExtraItems.TIER_10_PART.get(), ExtraItems.TIER_9_PART.get(), consumer);
        registerPartRecipe(ExtraItems.TIER_11_PART.get(), ExtraItems.TIER_10_PART.get(), consumer);
        registerPartRecipe(ExtraItems.TIER_12_PART.get(), ExtraItems.TIER_11_PART.get(), consumer);

        registerPartRecipe(ExtraItems.TIER_5_FLUID_DISK.get(), RSItems.FOUR_THOUSAND_NINETY_SIX_K_FLUID_STORAGE_PART, consumer);
        registerPartRecipe(ExtraItems.TIER_6_FLUID_PART.get(), ExtraItems.TIER_5_FLUID_PART.get(), consumer);
        registerPartRecipe(ExtraItems.TIER_7_FLUID_PART.get(), ExtraItems.TIER_6_FLUID_PART.get(), consumer);
        registerPartRecipe(ExtraItems.TIER_8_FLUID_PART.get(), ExtraItems.TIER_7_FLUID_PART.get(), consumer);
        registerPartRecipe(ExtraItems.TIER_9_FLUID_PART.get(), ExtraItems.TIER_8_FLUID_PART.get(), consumer);

        ShapedRecipeBuilder.shapedRecipe(ExtraItems.ADVANCED_STORAGE_HOUSING.get())
                .patternLine("GEG")
                .patternLine("E E")
                .patternLine("IAI")
                .key('G', Tags.Items.GLASS)
                .key('E', RSItems.QUARTZ_ENRICHED_IRON)
                .key('I', RSItems.IMPROVED_PROCESSOR)
                .key('A', RSItems.ADVANCED_PROCESSOR)
                .addCriterion("has_processor", hasItem(RSItems.ADVANCED_PROCESSOR))
                .build(consumer);
    }

    private void registerPartRecipe(Item result, Item prevPart, Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(result)
                .patternLine("DED")
                .patternLine("PRP")
                .patternLine("DPD")
                .key('D', RSItems.ADVANCED_PROCESSOR)
                .key('E', RSItems.QUARTZ_ENRICHED_IRON)
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
                .key('E', RSItems.QUARTZ_ENRICHED_IRON)
                .key('P', part)
                .key('I', RSItems.IMPROVED_PROCESSOR)
                .key('A', RSItems.ADVANCED_PROCESSOR)
                .addCriterion("has_part", hasItem(part))
                .build(consumer, new ResourceLocation(ExtraDisks.MODID, "disk/shaped/" + result.getRegistryName().getPath()));
        ShapelessRecipeBuilder.shapelessRecipe(result)
                .addIngredient(ExtraItems.ADVANCED_STORAGE_HOUSING.get())
                .addIngredient(part)
                .addCriterion("has_part", hasItem(part))
                .build(consumer, new ResourceLocation(ExtraDisks.MODID, "disk/shapeless/" + result.getRegistryName().getPath()));
    }
}
