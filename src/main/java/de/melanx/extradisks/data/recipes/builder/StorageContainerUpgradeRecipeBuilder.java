package de.melanx.extradisks.data.recipes.builder;

import de.melanx.extradisks.data.recipes.StorageContainerUpgradeRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StorageContainerUpgradeRecipeBuilder implements RecipeBuilder {

    private final Item result;
    private final ItemStack resultStack;
    private Ingredient baseDisk;
    private Ingredient upgradePart;

    public StorageContainerUpgradeRecipeBuilder(ItemStack result) {
        this.result = result.getItem();
        this.resultStack = result;
    }

    public static StorageContainerUpgradeRecipeBuilder shapeless(ItemLike result) {
        return new StorageContainerUpgradeRecipeBuilder(result.asItem().getDefaultInstance());
    }

    public StorageContainerUpgradeRecipeBuilder disk(Ingredient ingredient) {
        this.baseDisk = ingredient;

        return this;
    }

    public StorageContainerUpgradeRecipeBuilder part(Ingredient ingredient) {
        this.upgradePart = ingredient;

        return this;
    }

    @Nonnull
    @Override
    public RecipeBuilder unlockedBy(@Nonnull String name, @Nonnull Criterion<?> criterion) {
        return this;
    }

    @Nonnull
    @Override
    public StorageContainerUpgradeRecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Nonnull
    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public void save(@Nonnull RecipeOutput recipeOutput) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(this.getResult().asItem()).withPrefix("storage_upgrade/").withSuffix("_upgrade");
        this.save(recipeOutput, location);
    }

    @Override
    public void save(RecipeOutput recipeOutput, @Nonnull ResourceLocation id) {
        StorageContainerUpgradeRecipe recipe = new StorageContainerUpgradeRecipe(this.baseDisk, this.upgradePart, this.resultStack);
        recipeOutput.accept(id, recipe, null);
    }
}
