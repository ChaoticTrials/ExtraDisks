package de.melanx.extradisks.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.refinedmods.refinedstorage.loottable.CrafterLootFunction;
import com.refinedmods.refinedstorage.loottable.StorageBlockLootFunction;
import de.melanx.extradisks.blocks.fluid.ExtraFluidStorageBlock;
import de.melanx.extradisks.blocks.item.ExtraItemStorageBlock;
import de.melanx.extradisks.items.Registration;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((name, table) -> LootTableManager.validateLootTable(validationtracker, name, table));
    }

    private static class BlockTables extends BlockLootTables {
        @Override
        protected void addTables() {
            Registration.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
                if (block instanceof ExtraItemStorageBlock || block instanceof ExtraFluidStorageBlock) {
                    // TODO wait for RS to implement StorageBlockLootFunction.builder()
                } else {
                    this.registerDropSelfLootTable(block);
                }
            });
        }

        @Nonnull
        @Override
        protected Iterable<Block> getKnownBlocks() {
             return Registration.BLOCKS.getEntries().stream().map(RegistryObject::get)
                     .filter(block -> !(block instanceof ExtraItemStorageBlock) && !(block instanceof ExtraFluidStorageBlock))::iterator;
        }

        // taken from Refined Storage
        // unused at the moment
//        private void genBlockItemLootTableWithFunction(Block block, ILootFunction.IBuilder builder) {
//            this.registerLootTable(block, LootTable.builder().addLootPool(
//                    LootPool.builder()
//                            .rolls(ConstantRange.of(1))
//                            .addEntry(ItemLootEntry.builder(block)
//                                    .acceptFunction(builder))
//                            .acceptCondition(SurvivesExplosion.builder())));
//        }
    }
}
