package de.melanx.extradisks.data;

import com.refinedmods.refinedstorage.RSItems;
import com.refinedmods.refinedstorage.apiimpl.storage.FluidStorageType;
import com.refinedmods.refinedstorage.apiimpl.storage.ItemStorageType;
import com.refinedmods.refinedstorage.item.ProcessorItem;
import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageType;
import de.melanx.extradisks.items.item.ExtraItemStorageType;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class Recipes extends RecipeProvider {

    public Recipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
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

        this.registerProcessorRecipe(Registration.WITHERING_PROCESSOR.get(), Registration.RAW_WITHERING_PROCESSOR.get(), Ingredient.of(Tags.Items.NETHER_STARS), consumer);

        ShapedRecipeBuilder.shaped(Registration.ADVANCED_STORAGE_HOUSING.get())
                .pattern("GEG")
                .pattern("E E")
                .pattern("IAI")
                .define('G', Tags.Items.GLASS)
                .define('E', RSItems.QUARTZ_ENRICHED_IRON.get())
                .define('I', RSItems.PROCESSORS.get(ProcessorItem.Type.IMPROVED).get())
                .define('A', RSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get())
                .unlockedBy("has_processor", has(RSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(Registration.ADVANCED_MACHINE_CASING.get())
                .pattern("DCD")
                .pattern("GBG")
                .pattern("DOD")
                .define('D', RSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get())
                .define('C', RSItems.CONSTRUCTION_CORE.get())
                .define('G', RSItems.PROCESSORS.get(ProcessorItem.Type.IMPROVED).get())
                .define('B', RSItems.MACHINE_CASING.get())
                .define('O', RSItems.DESTRUCTION_CORE.get())
                .unlockedBy("has_processor", has(RSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get()))
                .save(consumer);
    }

    private void registerProcessorRecipe(ItemLike result, ItemLike raw, Ingredient ingredient, Consumer<FinishedRecipe> consumer) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(raw), result, 0.5F, 200)
                .unlockedBy("has_raw", has(raw))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(raw)
                .requires(RSItems.PROCESSOR_BINDING.get())
                .requires(ingredient)
                .requires(RSItems.SILICON.get())
                .requires(Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_binding", has(RSItems.PROCESSOR_BINDING.get()))
                .save(consumer);
    }

    private void registerPartRecipe(Item result, Item prevPart, Consumer<FinishedRecipe> consumer) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(result)
                .pattern("DED")
                .pattern("PRP")
                .pattern("DPD")
                .define('D', RSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get())
                .define('E', RSItems.QUARTZ_ENRICHED_IRON.get())
                .define('P', prevPart)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_prev_part", has(prevPart))
                .save(consumer, new ResourceLocation(ExtraDisks.MODID, "part/" + ForgeRegistries.ITEMS.getKey(result).getPath()));
    }

    private void registerPartRecipe(Item result, TagKey<Item> prevPart, Consumer<FinishedRecipe> consumer) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(result)
                .pattern("DED")
                .pattern("PRP")
                .pattern("DPD")
                .define('D', RSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get())
                .define('E', RSItems.QUARTZ_ENRICHED_IRON.get())
                .define('P', prevPart)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_prev_part", has(prevPart))
                .save(consumer, new ResourceLocation(ExtraDisks.MODID, "part/" + ForgeRegistries.ITEMS.getKey(result).getPath()));
    }

    private void registerAdvancedPartRecipe(Item result, TagKey<Item> prevPart, Consumer<FinishedRecipe> consumer) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(result)
                .pattern("DED")
                .pattern("PRP")
                .pattern("DPD")
                .define('D', Registration.WITHERING_PROCESSOR.get())
                .define('E', RSItems.QUARTZ_ENRICHED_IRON.get())
                .define('P', prevPart)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_prev_part", has(prevPart))
                .save(consumer, new ResourceLocation(ExtraDisks.MODID, "part/" + ForgeRegistries.ITEMS.getKey(result).getPath()));
    }

    private void registerDiskRecipes(Item result, TagKey<Item> part, Consumer<FinishedRecipe> consumer) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(result)
                .pattern("GEG")
                .pattern("EPE")
                .pattern("IAI")
                .define('G', Tags.Items.GLASS)
                .define('E', RSItems.QUARTZ_ENRICHED_IRON.get())
                .define('P', part)
                .define('I', RSItems.PROCESSORS.get(ProcessorItem.Type.IMPROVED).get())
                .define('A', RSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get())
                .unlockedBy("has_part", has(part))
                .save(consumer, new ResourceLocation(ExtraDisks.MODID, "disk/shaped/" + ForgeRegistries.ITEMS.getKey(result).getPath()));
        ShapelessRecipeBuilder.shapeless(result)
                .requires(Registration.ADVANCED_STORAGE_HOUSING.get())
                .requires(part)
                .unlockedBy("has_part", has(part))
                .save(consumer, new ResourceLocation(ExtraDisks.MODID, "disk/shapeless/" + ForgeRegistries.ITEMS.getKey(result).getPath()));
    }

    private void registerStorageBlockRecipe(TagKey<Item> part, ItemLike block, Consumer<FinishedRecipe> consumer) {
        //noinspection ConstantConditions
        ShapedRecipeBuilder.shaped(block)
                .pattern("QPQ")
                .pattern("QCQ")
                .pattern("QRQ")
                .define('Q', RSItems.QUARTZ_ENRICHED_IRON.get())
                .define('P', part)
                .define('C', Registration.ADVANCED_MACHINE_CASING.get())
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .unlockedBy("has_part", has(part))
                .save(consumer, new ResourceLocation(ExtraDisks.MODID, "blocks/" + ForgeRegistries.ITEMS.getKey(block.asItem()).getPath()));
    }
}
