package de.melanx.extradisks.data;

import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.Registration;
import de.melanx.extradisks.content.fluid.ExtraFluidStorageVariant;
import de.melanx.extradisks.content.item.ExtraItemStorageVariant;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ExtraAdvancementProvider extends AdvancementProvider {

    public ExtraAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new Generator()));
    }

    private static class Generator implements AdvancementSubProvider {

        @Override
        public void generate(@Nonnull HolderLookup.Provider registries, @Nonnull Consumer<AdvancementHolder> saver) {
            Advancement.Builder.advancement().display(Registration.ITEM_STORAGE_DISK.get(ExtraItemStorageVariant.TIER_12).get(), Component.translatable("advancements.extradisks.infinite_storage.title"), Component.translatable("advancements.extradisks.infinite_storage.description"), Identifier.fromNamespaceAndPath(ExtraDisks.MODID, "textures/gui/advancements.png"), AdvancementType.CHALLENGE, true, true, true)
                    .addCriterion("has_storage", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(
                                    registries.lookupOrThrow(Registries.ITEM),
                                    Registration.ITEM_STORAGE_DISK.get(ExtraItemStorageVariant.TIER_12).get(),
                                    Registration.FLUID_STORAGE_DISK.get(ExtraFluidStorageVariant.TIER_9_FLUID).get(),
                                    Registration.ITEM_STORAGE_BLOCK.get(ExtraItemStorageVariant.TIER_12).get(),
                                    Registration.FLUID_STORAGE_BLOCK.get(ExtraFluidStorageVariant.TIER_9_FLUID).get()
                            ).build()
                    )).save(saver, Identifier.fromNamespaceAndPath(ExtraDisks.MODID, "infinite_storage"));
        }
    }
}
