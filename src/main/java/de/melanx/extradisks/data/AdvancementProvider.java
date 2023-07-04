package de.melanx.extradisks.data;

import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.items.Registration;
import de.melanx.extradisks.items.fluid.ExtraFluidStorageType;
import de.melanx.extradisks.items.item.ExtraItemStorageType;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AdvancementProvider extends ForgeAdvancementProvider {

    public AdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper, List<AdvancementGenerator> subProviders) {
        super(output, registries, existingFileHelper, List.of(new Generator()));
    }

    private static class Generator implements AdvancementGenerator {

        @Override
        public void generate(@Nonnull HolderLookup.Provider registries, @Nonnull Consumer<Advancement> saver, @Nonnull ExistingFileHelper helper) {
            Advancement.Builder.advancement().display(Registration.ITEM_STORAGE_DISK.get(ExtraItemStorageType.TIER_12).get(), Component.translatable("advancements.extradisks.infinite_storage.title"), Component.translatable("advancements.extradisks.infinite_storage.description"), null, FrameType.CHALLENGE, true, true, true)
                    .addCriterion("has_storage", InventoryChangeTrigger.TriggerInstance.hasItems(
                            ItemPredicate.Builder.item().of(
                                    Registration.ITEM_STORAGE_DISK.get(ExtraItemStorageType.TIER_12).get(),
                                    Registration.FLUID_STORAGE_DISK.get(ExtraFluidStorageType.TIER_9_FLUID).get(),
                                    Registration.ITEM_STORAGE_BLOCK.get(ExtraItemStorageType.TIER_12).get(),
                                    Registration.FLUID_STORAGE_BLOCK.get(ExtraFluidStorageType.TIER_9_FLUID).get()
                            ).build()
                    )).save(saver, new ResourceLocation(ExtraDisks.MODID, "infinite_storage"), helper);
        }
    }
}
