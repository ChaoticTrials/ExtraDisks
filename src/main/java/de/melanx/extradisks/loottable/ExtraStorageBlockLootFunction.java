package de.melanx.extradisks.loottable;

import com.mojang.serialization.MapCodec;
import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.api.storage.StorageBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import javax.annotation.Nonnull;

public class ExtraStorageBlockLootFunction implements LootItemFunction {

    public static final MapCodec<? extends LootItemFunction> FUNCTION_CODEC = MapCodec.unit(ExtraStorageBlockLootFunction::new);

    @Nonnull
    @Override
    public MapCodec<? extends LootItemFunction> codec() {
        return FUNCTION_CODEC;
    }

    @Override
    public ItemStack apply(ItemStack stack, LootContext context) {
        BlockEntity blockEntity = context.getParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof StorageBlockEntity transferable) {
            RefinedStorageApi.INSTANCE.getStorageContainerItemHelper().transferFromBlockEntity(stack, transferable);
        }

        return stack;
    }
}
