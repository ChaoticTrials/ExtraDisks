package de.melanx.extradisks.data;

import de.melanx.extradisks.blocks.fluid.ExtraFluidStorageBlock;
import de.melanx.extradisks.blocks.item.ExtraItemStorageBlock;
import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.loottable.ExtraStorageBlockLootFunction;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.Collections;

public class ExtraLootTables extends BlockLootSubProvider {

    public ExtraLootTables() {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        Registration.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
            if (block instanceof ExtraItemStorageBlock || block instanceof ExtraFluidStorageBlock) {
                this.genBlockItemLootTableWithFunction(block, ExtraStorageBlockLootFunction.builder());
            } else {
                this.dropSelf(block);
            }
        });
    }

    @Nonnull
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Registration.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    private void genBlockItemLootTableWithFunction(Block block, LootItemFunction.Builder builder) {
        this.add(block, LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(block)
                                .apply(builder))));
    }
}
