package de.melanx.extradisks;

import com.refinedmods.refinedstorage.api.network.node.INetworkNode;
import com.refinedmods.refinedstorage.apiimpl.API;
import com.refinedmods.refinedstorage.apiimpl.network.node.NetworkNode;
import com.refinedmods.refinedstorage.blockentity.data.BlockEntitySynchronizationManager;
import de.melanx.extradisks.blocks.fluid.ExtraFluidStorageBlockScreen;
import de.melanx.extradisks.blocks.fluid.ExtraFluidStorageNetworkNode;
import de.melanx.extradisks.blocks.item.ExtraItemStorageBlockScreen;
import de.melanx.extradisks.blocks.item.ExtraItemStorageNetworkNode;
import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageType;
import de.melanx.extradisks.items.item.ExtraItemStorageType;
import de.melanx.extradisks.loottable.ExtraLootFunctions;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@Mod(ExtraDisks.MODID)
public class ExtraDisks {

    public static final String MODID = "extradisks";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final CreativeModeTab ModCategory = new CreativeModeTab(MODID) {

        @Nonnull
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registration.ITEM_STORAGE_DISK.get(ExtraItemStorageType.TIER_8).get());
        }
    };

    public ExtraDisks() {
        Registration.init();
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, ModConfig.CONFIG);
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        for (ExtraItemStorageType type : ExtraItemStorageType.values()) {
            MenuScreens.register(Registration.ITEM_STORAGE_CONTAINER.get(type).get(), ExtraItemStorageBlockScreen::new);
        }

        for (ExtraFluidStorageType type : ExtraFluidStorageType.values()) {
            MenuScreens.register(Registration.FLUID_STORAGE_CONTAINER.get(type).get(), ExtraFluidStorageBlockScreen::new);
        }
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        ExtraLootFunctions.register();

        for (ExtraItemStorageType type : ExtraItemStorageType.values()) {
            API.instance().getNetworkNodeRegistry().add(new ResourceLocation(MODID, type.getName() + "_storage_block"), (tag, world, pos) -> readAndReturn(tag, new ExtraItemStorageNetworkNode(world, pos, type)));
            //noinspection ConstantConditions
            Registration.ITEM_STORAGE_TILE.get(type).get().create(BlockPos.ZERO, null).getDataManager().getParameters().forEach(BlockEntitySynchronizationManager::registerParameter);
        }

        for (ExtraFluidStorageType type : ExtraFluidStorageType.values()) {
            API.instance().getNetworkNodeRegistry().add(new ResourceLocation(MODID, type.getName() + "_fluid_storage_block"), (tag, world, pos) -> readAndReturn(tag, new ExtraFluidStorageNetworkNode(world, pos, type)));
            //noinspection ConstantConditions
            Registration.FLUID_STORAGE_TILE.get(type).get().create(BlockPos.ZERO, null).getDataManager().getParameters().forEach(BlockEntitySynchronizationManager::registerParameter);
        }
    }

    private static INetworkNode readAndReturn(CompoundTag tag, NetworkNode node) {
        node.read(tag);
        return node;
    }
}
