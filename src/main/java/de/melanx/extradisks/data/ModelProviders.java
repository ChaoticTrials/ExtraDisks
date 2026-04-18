package de.melanx.extradisks.data;

import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.Registration;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;

import javax.annotation.Nonnull;

public class ModelProviders extends ModelProvider {

    public ModelProviders(PackOutput output) {
        super(output, ExtraDisks.MODID);
    }

    @Override
    protected void registerModels(@Nonnull BlockModelGenerators blockModels, @Nonnull ItemModelGenerators itemModels) {
        Registration.ITEMS.getEntries().forEach(holder -> {
            if (holder.get() instanceof BlockItem blockItem) {
                Identifier blockModel = ModelTemplates.CUBE_ALL.create(
                        this.modLocation("block/" + holder.getId().getPath()),
                        TextureMapping.cube(blockItem.getBlock()),
                        blockModels.modelOutput
                );
                blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(blockItem.getBlock(), BlockModelGenerators.plainVariant(blockModel)));
                itemModels.itemModelOutput.accept(holder.get(), ItemModelUtils.plainModel(blockModel));
            } else {
                itemModels.generateFlatItem(holder.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
            }
        });
    }
}
