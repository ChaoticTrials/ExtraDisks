package de.melanx.extradisks.data;

import com.refinedmods.refinedstorage.common.storage.storageblock.StorageBlock;
import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.loottable.ExtraStorageBlockLootFunction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredHolder;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.stream.Collectors;

public class ExtraLootTables extends BlockLootSubProvider {

    public ExtraLootTables(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    public void generate() {
        Registration.BLOCKS.getEntries().stream().map(DeferredHolder::get).forEach(block -> {
            if (block instanceof StorageBlock) {
                this.genBlockItemLootTableWithFunction(block, new ExtraStorageBlockLootFunction.Builder());
            } else {
                this.dropSelf(block);
            }
        });
    }

    @Nonnull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Registration.BLOCKS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toSet());
    }

    private void genBlockItemLootTableWithFunction(Block block, ExtraStorageBlockLootFunction.Builder builder) {
        this.add(block, LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(block)
                                .apply(builder)
                                .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                                        .include(DataComponents.CUSTOM_NAME))
                        )));
    }
}
