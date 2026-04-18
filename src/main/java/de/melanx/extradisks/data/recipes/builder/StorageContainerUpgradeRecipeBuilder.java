package de.melanx.extradisks.data.recipes.builder;

import de.melanx.extradisks.data.recipes.StorageContainerUpgradeRecipe;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StorageContainerUpgradeRecipeBuilder implements RecipeBuilder {

    private final ItemStackTemplate result;
    private Ingredient baseDisk;
    private Ingredient upgradePart;

    public StorageContainerUpgradeRecipeBuilder(ItemStackTemplate result) {
        this.result = result;
    }

    public static StorageContainerUpgradeRecipeBuilder shapeless(ItemLike result) {
        return new StorageContainerUpgradeRecipeBuilder(new ItemStackTemplate(result.asItem()));
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
    public ResourceKey<Recipe<?>> defaultId() {
        Identifier id = this.result.typeHolder().unwrapKey().orElseThrow().identifier().withPrefix("storage_upgrade/").withSuffix("_upgrade");
        return ResourceKey.create(Registries.RECIPE, id);
    }

    @Override
    public void save(@Nonnull RecipeOutput recipeOutput, @Nonnull ResourceKey<Recipe<?>> location) {
        StorageContainerUpgradeRecipe recipe = new StorageContainerUpgradeRecipe(this.baseDisk, this.upgradePart, this.result);
        recipeOutput.accept(location, recipe, null);
    }
}
