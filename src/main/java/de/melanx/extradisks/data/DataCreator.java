package de.melanx.extradisks.data;

import de.melanx.extradisks.ExtraDisks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ExtraDisks.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
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
        generator.addProvider(server, new Recipes(output));
        generator.addProvider(server, new AdvancementProvider(output, lookupProvider, helper, List.of()));
        generator.addProvider(server, new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ExtraLootTables::new, LootContextParamSets.BLOCK)
        )));

        boolean client = event.includeClient();
        generator.addProvider(client, new ModItemModels(output, helper));
        generator.addProvider(client, new BlockStates(output, helper));
    }

}
