package de.melanx.extradisks;

import de.melanx.extradisks.items.fluid.ExtraFluidStorageDiskItem;
import de.melanx.extradisks.items.fluid.ExtraFluidStoragePartItem;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageType;
import de.melanx.extradisks.items.item.ExtraItemStorageType;
import de.melanx.extradisks.items.item.ExtraStorageDiskItem;
import de.melanx.extradisks.items.AdvancedStorageHousingItem;
import de.melanx.extradisks.items.item.ExtraStoragePartItem;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ExtraDisks.MODID)
public class ExtraDisks {

    public static final String MODID = "extradisks";
    private static final Logger LOGGER = LogManager.getLogger();
    public static final ItemGroup ModGroup = new CreativeTab();

    public ExtraDisks() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
            event.getRegistry().register(new AdvancedStorageHousingItem());

            for (ExtraItemStorageType type : ExtraItemStorageType.values()) {
                event.getRegistry().register(new ExtraStoragePartItem(type));
                event.getRegistry().register(new ExtraStorageDiskItem(type));
            }

            for (ExtraFluidStorageType type: ExtraFluidStorageType.values()) {
                event.getRegistry().register(new ExtraFluidStoragePartItem(type));
                event.getRegistry().register(new ExtraFluidStorageDiskItem(type));
            }
        }
    }
}
