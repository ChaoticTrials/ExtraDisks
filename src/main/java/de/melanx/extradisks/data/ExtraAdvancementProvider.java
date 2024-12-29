package de.melanx.extradisks.data;

import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageVariant;
import de.melanx.extradisks.items.item.ExtraItemStorageVariant;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ExtraAdvancementProvider extends AdvancementProvider {

    public ExtraAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new Generator()));
    }

    private static class Generator implements AdvancementGenerator {

        @Override
        public void generate(@Nonnull HolderLookup.Provider registries, @Nonnull Consumer<AdvancementHolder> saver, @Nonnull ExistingFileHelper helper) {
            Advancement.Builder.advancement().display(Registration.ITEM_STORAGE_DISK.get(ExtraItemStorageVariant.TIER_12).get(), Component.translatable("advancements.extradisks.infinite_storage.title"), Component.translatable("advancements.extradisks.infinite_storage.description"), null, AdvancementType.CHALLENGE, true, true, true)
                    .addCriterion("has_storage", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(
                                    Registration.ITEM_STORAGE_DISK.get(ExtraItemStorageVariant.TIER_12).get(),
                                    Registration.FLUID_STORAGE_DISK.get(ExtraFluidStorageVariant.TIER_9_FLUID).get(),
                                    Registration.ITEM_STORAGE_BLOCK.get(ExtraItemStorageVariant.TIER_12).get(),
                                    Registration.FLUID_STORAGE_BLOCK.get(ExtraFluidStorageVariant.TIER_9_FLUID).get()
                            ).build()
                    )).save(saver, ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "infinite_storage"), helper);
        }
    }
}
