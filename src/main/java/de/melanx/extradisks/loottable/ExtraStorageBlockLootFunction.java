package de.melanx.extradisks.loottable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import de.melanx.extradisks.blocks.fluid.ExtraFluidStorageBlockEntity;
import de.melanx.extradisks.blocks.fluid.ExtraFluidStorageNetworkNode;
import de.melanx.extradisks.blocks.item.ExtraItemStorageBlockEntity;
import de.melanx.extradisks.blocks.item.ExtraItemStorageNetworkNode;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import javax.annotation.Nonnull;

public class ExtraStorageBlockLootFunction extends LootItemConditionalFunction {
    protected ExtraStorageBlockLootFunction(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Nonnull
    @Override
    protected ItemStack run(@Nonnull ItemStack stack, @Nonnull LootContext context) {
        BlockEntity tile = context.getParamOrNull(LootContextParams.BLOCK_ENTITY);
        if (tile instanceof ExtraItemStorageBlockEntity) {
            ExtraItemStorageNetworkNode removedNode = ((ExtraItemStorageBlockEntity) tile).getRemovedNode();
            if (removedNode == null) {
                removedNode = ((ExtraItemStorageBlockEntity) tile).getNode();
            }
            stack.getOrCreateTag().putUUID(ExtraItemStorageNetworkNode.NBT_ID, removedNode.getStorageId());
        } else if (tile instanceof ExtraFluidStorageBlockEntity) {
            ExtraFluidStorageNetworkNode removedNode = ((ExtraFluidStorageBlockEntity) tile).getRemovedNode();
            if (removedNode == null) {
                removedNode = ((ExtraFluidStorageBlockEntity) tile).getNode();
            }
            stack.getOrCreateTag().putUUID(ExtraFluidStorageNetworkNode.NBT_ID, removedNode.getStorageId());
        }
        return stack;
    }

    @Nonnull
    public LootItemFunctionType getType() {
        return ExtraLootFunctions.STORAGE_BLOCK;
    }

    public static LootItemConditionalFunction.Builder<?> builder() {
        return simpleBuilder(ExtraStorageBlockLootFunction::new);
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<ExtraStorageBlockLootFunction> {
        public Serializer() {
        }

        @Nonnull
        public ExtraStorageBlockLootFunction deserialize(@Nonnull JsonObject object, @Nonnull JsonDeserializationContext deserializationContext, @Nonnull LootItemCondition[] conditions) {
            return new ExtraStorageBlockLootFunction(conditions);
        }
    }
}
