package de.melanx.extradisks.data;

import de.melanx.extradisks.ExtraDisks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ExtraDisks.MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataCreator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        ModTags.BlockTags blockTagsProvider = new ModTags.BlockTags(output, lookupProvider, helper);
        boolean server = event.includeServer();
        generator.addProvider(server, blockTagsProvider);
        generator.addProvider(server, new ModTags.ItemTags(output, lookupProvider, blockTagsProvider.contentsGetter(), helper));
        generator.addProvider(server, new Recipes(output, lookupProvider));
        generator.addProvider(server, new ExtraAdvancementProvider(output, lookupProvider, helper));
        generator.addProvider(server, new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ExtraLootTables::new, LootContextParamSets.BLOCK)
        ), lookupProvider));

        boolean client = event.includeClient();
        generator.addProvider(client, new ModItemModels(output, helper));
        generator.addProvider(client, new BlockStates(output, helper));
        generator.addProvider(client, new BlockModels(output, helper));
    }
}
