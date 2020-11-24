package de.melanx.extradisks.data;

import com.refinedmods.refinedstorage.RSItems;
import com.refinedmods.refinedstorage.apiimpl.storage.FluidStorageType;
import com.refinedmods.refinedstorage.apiimpl.storage.ItemStorageType;
import com.refinedmods.refinedstorage.item.ProcessorItem;
import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.items.ExtraItems;
import net.minecraft.data.*;
import net.minecraft.item.Item;
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

        registerPartRecipe(ExtraItems.TIER_5_PART.get(), RSItems.ITEM_STORAGE_PARTS.get(ItemStorageType.SIXTY_FOUR_K).get(), consumer);
        registerPartRecipe(ExtraItems.TIER_6_PART.get(), ExtraItems.TIER_5_PART.get(), consumer);
        registerPartRecipe(ExtraItems.TIER_7_PART.get(), ExtraItems.TIER_6_PART.get(), consumer);
        registerPartRecipe(ExtraItems.TIER_8_PART.get(), ExtraItems.TIER_7_PART.get(), consumer);
        registerPartRecipe(ExtraItems.TIER_9_PART.get(), ExtraItems.TIER_8_PART.get(), consumer);
        registerPartRecipe(ExtraItems.TIER_10_PART.get(), ExtraItems.TIER_9_PART.get(), consumer);
        registerPartRecipe(ExtraItems.TIER_11_PART.get(), ExtraItems.TIER_10_PART.get(), consumer);
        registerPartRecipe(ExtraItems.TIER_12_PART.get(), ExtraItems.TIER_11_PART.get(), consumer);

        registerPartRecipe(ExtraItems.TIER_5_FLUID_PART.get(), RSItems.FLUID_STORAGE_PARTS.get(FluidStorageType.FOUR_THOUSAND_NINETY_SIX_K).get(), consumer);
        registerPartRecipe(ExtraItems.TIER_6_FLUID_PART.get(), ExtraItems.TIER_5_FLUID_PART.get(), consumer);
        registerPartRecipe(ExtraItems.TIER_7_FLUID_PART.get(), ExtraItems.TIER_6_FLUID_PART.get(), consumer);
        registerPartRecipe(ExtraItems.TIER_8_FLUID_PART.get(), ExtraItems.TIER_7_FLUID_PART.get(), consumer);
        registerPartRecipe(ExtraItems.TIER_9_FLUID_PART.get(), ExtraItems.TIER_8_FLUID_PART.get(), consumer);

        ShapedRecipeBuilder.shapedRecipe(ExtraItems.ADVANCED_STORAGE_HOUSING.get())
                .patternLine("GEG")
                .patternLine("E E")
                .patternLine("IAI")
                .key('G', Tags.Items.GLASS)
                .key('E', RSItems.QUARTZ_ENRICHED_IRON.get())
                .key('I', RSItems.PROCESSORS.get(ProcessorItem.Type.IMPROVED).get())
                .key('A', RSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get())
                .addCriterion("has_processor", hasItem(RSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get()))
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
                .addIngredient(ExtraItems.ADVANCED_STORAGE_HOUSING.get())
                .addIngredient(part)
                .addCriterion("has_part", hasItem(part))
                .build(consumer, new ResourceLocation(ExtraDisks.MODID, "disk/shapeless/" + result.getRegistryName().getPath()));
    }
}
