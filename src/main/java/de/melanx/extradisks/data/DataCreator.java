package de.melanx.extradisks.data;

import de.melanx.extradisks.ExtraDisks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExtraDisks.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataCreator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        ModTags.BlockTags blockTagsProvider = new ModTags.BlockTags(generator, helper);
        boolean server = event.includeServer();
        generator.addProvider(server, blockTagsProvider);
        generator.addProvider(server, new ModTags.ItemTags(generator, helper, blockTagsProvider));
        generator.addProvider(server, new Recipes(generator));
        generator.addProvider(server, new ExtraLootTables(generator));

        boolean client = event.includeClient();
        generator.addProvider(client, new ModItemModels(generator, helper));
        generator.addProvider(client, new BlockStates(generator, helper));
    }

}
