package de.melanx.extradisks;

import com.refinedmods.refinedstorage.api.network.node.INetworkNode;
import com.refinedmods.refinedstorage.apiimpl.API;
import com.refinedmods.refinedstorage.apiimpl.network.node.NetworkNode;
import com.refinedmods.refinedstorage.tile.data.TileDataManager;
import de.melanx.extradisks.blocks.fluid.ExtraFluidStorageBlockScreen;
import de.melanx.extradisks.blocks.fluid.ExtraFluidStorageNetworkNode;
import de.melanx.extradisks.blocks.item.ExtraItemStorageBlockScreen;
import de.melanx.extradisks.blocks.item.ExtraItemStorageNetworkNode;
import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageType;
import de.melanx.extradisks.items.item.ExtraItemStorageType;
import de.melanx.extradisks.loottable.ExtraLootFunctions;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SERVER_CONFIG);
        MinecraftForge.EVENT_BUS.register(this);
        ExtraLootFunctions.register();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        for (ExtraItemStorageType type : ExtraItemStorageType.values()) {
            ScreenManager.registerFactory(Registration.ITEM_STORAGE_CONTAINER.get(type).get(), ExtraItemStorageBlockScreen::new);
        }
        for (ExtraFluidStorageType type : ExtraFluidStorageType.values()) {
            ScreenManager.registerFactory(Registration.FLUID_STORAGE_CONTAINER.get(type).get(), ExtraFluidStorageBlockScreen::new);
        }
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        for (ExtraItemStorageType type : ExtraItemStorageType.values()) {
            API.instance().getNetworkNodeRegistry().add(new ResourceLocation(MODID, type.getName() + "_storage_block"), (tag, world, pos) -> readAndReturn(tag, new ExtraItemStorageNetworkNode(world, pos, type)));
            Registration.ITEM_STORAGE_TILE.get(type).get().create().getDataManager().getParameters().forEach(TileDataManager::registerParameter);
        }
        for (ExtraFluidStorageType type : ExtraFluidStorageType.values()) {
            API.instance().getNetworkNodeRegistry().add(new ResourceLocation(MODID, type.getName() + "_fluid_storage_block"), (tag, world, pos) -> readAndReturn(tag, new ExtraFluidStorageNetworkNode(world, pos, type)));
            Registration.FLUID_STORAGE_TILE.get(type).get().create().getDataManager().getParameters().forEach(TileDataManager::registerParameter);
        }
    }

    private static INetworkNode readAndReturn(CompoundNBT tag, NetworkNode node) {
        node.read(tag);
        return node;
    }
}
