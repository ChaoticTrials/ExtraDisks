package de.melanx.extradisks.loottable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import de.melanx.extradisks.blocks.fluid.ExtraFluidStorageBlockTile;
import de.melanx.extradisks.blocks.fluid.ExtraFluidStorageNetworkNode;
import de.melanx.extradisks.blocks.item.ExtraItemStorageBlockTile;
import de.melanx.extradisks.blocks.item.ExtraItemStorageNetworkNode;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;

public class ExtraStorageBlockLootFunction extends LootFunction {
    protected ExtraStorageBlockLootFunction(ILootCondition[] conditions) {
        super(conditions);
    }

    @Nonnull
    @Override
    protected ItemStack doApply(@Nonnull ItemStack stack, @Nonnull LootContext context) {
        TileEntity tile = context.get(LootParameters.BLOCK_ENTITY);
        if (tile instanceof ExtraItemStorageBlockTile) {
            ExtraItemStorageNetworkNode removedNode = ((ExtraItemStorageBlockTile) tile).getRemovedNode();
            if (removedNode == null) {
                ((ExtraItemStorageBlockTile) tile).getNode();
            }
            stack.getOrCreateTag().putUniqueId(ExtraItemStorageNetworkNode.NBT_ID, removedNode.getStorageId());
        } else if (tile instanceof ExtraFluidStorageBlockTile) {
            ExtraFluidStorageNetworkNode removedNode = ((ExtraFluidStorageBlockTile) tile).getRemovedNode();
            if (removedNode == null) {
                ((ExtraFluidStorageBlockTile) tile).getNode();
            }
            stack.getOrCreateTag().putUniqueId(ExtraFluidStorageNetworkNode.NBT_ID, removedNode.getStorageId());
        }
        return stack;
    }

    @Nonnull
    public LootFunctionType getFunctionType() {
        return ExtraLootFunctions.STORAGE_BLOCK;
    }

    public static LootFunction.Builder<?> builder() {
        return builder(ExtraStorageBlockLootFunction::new);
    }

    public static class Serializer extends LootFunction.Serializer<ExtraStorageBlockLootFunction> {
        public Serializer() {
        }

        @Nonnull
        public ExtraStorageBlockLootFunction deserialize(@Nonnull JsonObject object, @Nonnull JsonDeserializationContext deserializationContext, @Nonnull ILootCondition[] conditions) {
            return new ExtraStorageBlockLootFunction(conditions);
        }
    }
}
