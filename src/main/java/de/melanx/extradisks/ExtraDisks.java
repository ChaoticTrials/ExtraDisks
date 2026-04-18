package de.melanx.extradisks;

import com.refinedmods.refinedstorage.neoforge.api.RefinedStorageNeoForgeApi;
import de.melanx.extradisks.content.fluid.ExtraFluidStorageVariant;
import de.melanx.extradisks.content.item.ExtraItemStorageVariant;
import de.melanx.extradisks.data.recipes.StorageContainerUpgradeRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ExtraDisks.MODID)
public final class ExtraDisks {

    public static final String MODID = "extradisks";
    public static final Logger LOGGER = LoggerFactory.getLogger(ExtraDisks.class);

    public ExtraDisks(IEventBus modBus, ModContainer container) {
        Registration.init(modBus);
        modBus.addListener(Registration::registerExtras);
        modBus.addListener(ExtraDisks::registerCapabilities);
        container.registerConfig(ModConfig.Type.SERVER, de.melanx.extradisks.ModConfig.CONFIG);

        DeferredRegister<RecipeSerializer<?>> recipeSerializerRegistry = DeferredRegister.create(
                BuiltInRegistries.RECIPE_SERIALIZER,
                ExtraDisks.MODID
        );

        ExtraDisks.registerRecipeSerializers(recipeSerializerRegistry);
        recipeSerializerRegistry.register(modBus);
    }

    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        for (ExtraItemStorageVariant variant : ExtraItemStorageVariant.values()) {
            event.registerBlockEntity(RefinedStorageNeoForgeApi.INSTANCE.getNetworkNodeContainerProviderCapability(),
                    Registration.ITEM_STORAGE_TILE.get(variant).get(), (be, side) -> be.getContainerProvider());
        }

        for (ExtraFluidStorageVariant variant : ExtraFluidStorageVariant.values()) {
            event.registerBlockEntity(RefinedStorageNeoForgeApi.INSTANCE.getNetworkNodeContainerProviderCapability(),
                    Registration.FLUID_STORAGE_TILE.get(variant).get(), (be, side) -> be.getContainerProvider());
        }
    }

    private static void registerRecipeSerializers(DeferredRegister<RecipeSerializer<?>> registry) {
        registry.register(
                "item_storage_disk_upgrade",
                () -> new RecipeSerializer<>(
                        StorageContainerUpgradeRecipe.CODEC,
                        StorageContainerUpgradeRecipe.STREAM_CODEC
                )
        );
//        registry.register(
//                "item_storage_block_upgrade",
//                () -> new StorageContainerUpgradeRecipeSerializer<>(
//                        ExtraItemStorageVariant.values(),
//                        to -> new StorageContainerUpgradeRecipe<>(
//                                ExtraItemStorageVariant.values(), to, Registration.ITEM_STORAGE_BLOCK::get
//                        )
//                )
//        );
//
//        registry.register(
//                "fluid_storage_disk_upgrade",
//                () -> new StorageContainerUpgradeRecipeSerializer<>(
//                        ExtraFluidStorageVariant.values(),
//                        to -> new StorageContainerUpgradeRecipe<>(
//                                ExtraFluidStorageVariant.values(), to, Registration.FLUID_STORAGE_DISK::get
//                        )
//                )
//        );
//        registry.register(
//                "fluid_storage_block_upgrade",
//                () -> new StorageContainerUpgradeRecipeSerializer<>(
//                        ExtraFluidStorageVariant.values(),
//                        to -> new StorageContainerUpgradeRecipe<>(
//                                ExtraFluidStorageVariant.values(), to, Registration.FLUID_STORAGE_BLOCK::get
//                        )
//                )
//        );
//
//        registry.register(
//                "chemical_storage_disk_upgrade",
//                () -> new StorageContainerUpgradeRecipeSerializer<>(
//                        ExtraChemicalStorageVariant.values(),
//                        to -> new StorageContainerUpgradeRecipe<>(
//                                ExtraChemicalStorageVariant.values(), to, Registration.CHEMICAL_STORAGE_DISK::get
//                        )
//                )
//        );
//        registry.register(
//                "chemical_storage_block_upgrade",
//                () -> new StorageContainerUpgradeRecipeSerializer<>(
//                        ExtraChemicalStorageVariant.values(),
//                        to -> new StorageContainerUpgradeRecipe<>(
//                                ExtraChemicalStorageVariant.values(), to, Registration.CHEMICAL_STORAGE_BLOCK::get
//                        )
//                )
//        );
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(ExtraDisks.MODID, path);
    }
}
