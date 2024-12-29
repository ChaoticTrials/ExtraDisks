package de.melanx.extradisks.loottable;

import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.api.storage.StorageBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import javax.annotation.Nonnull;
import java.util.List;

public class ExtraStorageBlockLootFunction extends LootItemConditionalFunction {

    public ExtraStorageBlockLootFunction() {
        super(List.of());
    }

    @Nonnull
    @Override
    protected ItemStack run(@Nonnull ItemStack stack, @Nonnull LootContext context) {
        BlockEntity blockEntity = context.getParam(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof StorageBlockEntity transferable) {
            RefinedStorageApi.INSTANCE.getStorageContainerItemHelper().transferFromBlockEntity(stack, transferable);
        }

        return stack;
    }

    @Nonnull
    public LootItemFunctionType<ExtraStorageBlockLootFunction> getType() {
        return ExtraLootFunctions.STORAGE_BLOCK;
    }

    public static class Builder extends LootItemConditionalFunction.Builder<ExtraStorageBlockLootFunction.Builder> {

        @Nonnull
        @Override
        protected ExtraStorageBlockLootFunction.Builder getThis() {
            return this;
        }

        @Nonnull
        @Override
        public LootItemFunction build() {
            return new ExtraStorageBlockLootFunction();
        }
    }
}
