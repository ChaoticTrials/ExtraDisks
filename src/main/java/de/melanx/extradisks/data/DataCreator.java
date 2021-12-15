package de.melanx.extradisks.data;

import de.melanx.extradisks.ExtraDisks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ExtraDisks.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataCreator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        if (event.includeServer()) {
            ModTags.BlockTags blockTagsProvider = new ModTags.BlockTags(generator, helper);
            generator.addProvider(blockTagsProvider);
            generator.addProvider(new ModTags.ItemTags(generator, helper, blockTagsProvider));
            generator.addProvider(new Recipes(generator));
            generator.addProvider(new ExtraLootTables(generator));
        }
        if (event.includeClient()) {
            generator.addProvider(new ModItemModels(generator, helper));
            generator.addProvider(new BlockStates(generator, helper));
        }
    }

}
