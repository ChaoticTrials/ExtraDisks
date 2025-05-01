package de.melanx.extradisks.data.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.refinedmods.refinedstorage.common.storage.UpgradeableStorageContainer;
import de.melanx.extradisks.Registration;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

import javax.annotation.Nonnull;

public class StorageContainerUpgradeRecipe extends ShapelessRecipe {

    private final Ingredient baseDisk;
    private final Ingredient part;

    public StorageContainerUpgradeRecipe(Ingredient baseDisk, Ingredient part, ItemStack result) {
        super("", CraftingBookCategory.MISC, result, NonNullList.of(Ingredient.EMPTY, baseDisk, part));
        this.baseDisk = baseDisk;
        this.part = part;
    }

    @Nonnull
    @Override
    public ItemStack assemble(CraftingInput input, @Nonnull HolderLookup.Provider provider) {
        for (int i = 0; i < input.size(); ++i) {
            final ItemStack fromDisk = input.getItem(i);
            if (fromDisk.getItem() instanceof UpgradeableStorageContainer from) {
                final ItemStack toDisk = this.getResultItem(provider).copy();
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
            final ItemStack fromDisk = input.getItem(i);
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
    public RecipeSerializer<?> getSerializer() {
        return Registration.UPGRADE_RECIPE.get();
    }

    public static class Serializer implements RecipeSerializer<StorageContainerUpgradeRecipe> {

        private static final MapCodec<StorageContainerUpgradeRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                        Ingredient.CODEC_NONEMPTY.fieldOf("base_disk").forGetter(recipe -> recipe.baseDisk),
                        Ingredient.CODEC_NONEMPTY.fieldOf("storage_part").forGetter(recipe -> recipe.part),
                        ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
                )
                .apply(instance, StorageContainerUpgradeRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, StorageContainerUpgradeRecipe> STREAM_CODEC = StreamCodec.of(
                (buffer, recipe) -> {
                    Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.baseDisk);
                    Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.part);
                    ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
                }, buffer -> {
                    return new StorageContainerUpgradeRecipe(
                            Ingredient.CONTENTS_STREAM_CODEC.decode(buffer),
                            Ingredient.CONTENTS_STREAM_CODEC.decode(buffer),
                            ItemStack.STREAM_CODEC.decode(buffer)
                    );
                }
        );

        @Nonnull
        @Override
        public MapCodec<StorageContainerUpgradeRecipe> codec() {
            return CODEC;
        }

        @Nonnull
        @Override
        public StreamCodec<RegistryFriendlyByteBuf, StorageContainerUpgradeRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
