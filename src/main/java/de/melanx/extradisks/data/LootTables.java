package de.melanx.extradisks.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import de.melanx.extradisks.blocks.fluid.ExtraFluidStorageBlock;
import de.melanx.extradisks.blocks.item.ExtraItemStorageBlock;
import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.loottable.ExtraStorageBlockLootFunction;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LootTables extends LootTableProvider {
    public LootTables(DataGenerator generator) {
        super(generator);
    }

    @Nonnull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(Pair.of(BlockTables::new, LootParameterSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationTracker validationtracker) {
        map.forEach((name, table) -> LootTableManager.validateLootTable(validationtracker, name, table));
    }

    private static class BlockTables extends BlockLootTables {
        @Override
        protected void addTables() {
            Registration.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
                if (block instanceof ExtraItemStorageBlock || block instanceof ExtraFluidStorageBlock) {
                    this.genBlockItemLootTableWithFunction(block, ExtraStorageBlockLootFunction.builder());
                } else {
                    this.registerDropSelfLootTable(block);
                }
            });
        }

        @Nonnull
        @Override
        protected Iterable<Block> getKnownBlocks() {
             return Registration.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
        }

        private void genBlockItemLootTableWithFunction(Block block, ILootFunction.IBuilder builder) {
            this.registerLootTable(block, LootTable.builder().addLootPool(
                    LootPool.builder()
                            .rolls(ConstantRange.of(1))
                            .addEntry(ItemLootEntry.builder(block)
                                    .acceptFunction(builder))));
        }
    }
}
