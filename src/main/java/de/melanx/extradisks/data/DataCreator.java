package de.melanx.extradisks.data;

import de.melanx.extradisks.ExtraDisks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ExtraDisks.MODID)
public class DataCreator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ModTags.BlockTags blockTagsProvider = new ModTags.BlockTags(output, lookupProvider);
        event.addProvider(blockTagsProvider);
        event.addProvider(new ModTags.ItemTags(output, lookupProvider, blockTagsProvider.contentsGetter()));
        event.addProvider(new Recipes.Runner(output, lookupProvider));
        event.addProvider(new ExtraAdvancementProvider(output, lookupProvider));
        event.addProvider(new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ExtraLootTables::new, LootContextParamSets.BLOCK)
        ), lookupProvider));

        event.addProvider(new ModelProviders(output));
    }
}
