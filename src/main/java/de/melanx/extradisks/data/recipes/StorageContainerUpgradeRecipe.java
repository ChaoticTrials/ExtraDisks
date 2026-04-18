package de.melanx.extradisks.data.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.refinedmods.refinedstorage.common.storage.UpgradeableStorageContainer;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;

import javax.annotation.Nonnull;
import java.util.List;

public class StorageContainerUpgradeRecipe extends ShapelessRecipe {

    public static final MapCodec<StorageContainerUpgradeRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Ingredient.CODEC.fieldOf("base_disk").forGetter(recipe -> recipe.baseDisk),
                    Ingredient.CODEC.fieldOf("storage_part").forGetter(recipe -> recipe.part),
                    ItemStackTemplate.MAP_CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
            )
            .apply(instance, StorageContainerUpgradeRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, StorageContainerUpgradeRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, recipe -> recipe.baseDisk,
            Ingredient.CONTENTS_STREAM_CODEC, recipe -> recipe.part,
            ItemStackTemplate.STREAM_CODEC, recipe -> recipe.result,
            StorageContainerUpgradeRecipe::new
    );

    public static final RecipeSerializer<StorageContainerUpgradeRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);
    private final Ingredient baseDisk;
    private final Ingredient part;

    public StorageContainerUpgradeRecipe(Ingredient baseDisk, Ingredient part, ItemStackTemplate result) {
        super(new Recipe.CommonInfo(false), RecipeBuilder.createCraftingBookInfo(RecipeCategory.MISC, null), result, List.of(baseDisk, part));
        this.baseDisk = baseDisk;
        this.part = part;
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull CraftingInput input) {
        for (int i = 0; i < input.size(); ++i) {
            ItemStack fromDisk = input.getItem(i);
            if (fromDisk.getItem() instanceof UpgradeableStorageContainer from) {
                final ItemStack toDisk = this.result.create();
                from.transferTo(fromDisk, toDisk);

                return toDisk;
            }
        }

        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remainingItems = NonNullList.withSize(input.size(), ItemStack.EMPTY);
        for (int i = 0; i < input.size(); i++) {
            ItemStack fromDisk = input.getItem(i);
            if (fromDisk.getItem() instanceof UpgradeableStorageContainer from) {
                Item storagePart = from.getVariant().getStoragePart();
                if (storagePart != null) {
                    remainingItems.set(i, new ItemStack(storagePart));
                }
            }
        }

        return remainingItems;
    }

    @Nonnull
    @Override
    public RecipeSerializer<ShapelessRecipe> getSerializer() {
        //noinspection rawtypes
        return (RecipeSerializer) StorageContainerUpgradeRecipe.SERIALIZER;
    }
}
