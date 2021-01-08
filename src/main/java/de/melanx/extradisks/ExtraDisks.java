package de.melanx.extradisks;

import de.melanx.extradisks.blocks.fluid.ExtraFluidStorageBlockScreen;
import de.melanx.extradisks.blocks.item.ExtraItemStorageBlockScreen;
import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageType;
import de.melanx.extradisks.items.item.ExtraItemStorageType;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ExtraDisks.MODID)
public class ExtraDisks {

    public static final String MODID = "extradisks";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ItemGroup ModGroup = new CreativeTab();

    public ExtraDisks() {
        Registration.init();
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        for (ExtraItemStorageType type : ExtraItemStorageType.values()) {
            ScreenManager.registerFactory(Registration.ITEM_STORAGE_CONTAINER.get(type).get(), ExtraItemStorageBlockScreen::new);
        }
        for (ExtraFluidStorageType type : ExtraFluidStorageType.values()) {
            ScreenManager.registerFactory(Registration.FLUID_STORAGE_CONTAINER.get(type).get(), ExtraFluidStorageBlockScreen::new);
        }
    }
}
