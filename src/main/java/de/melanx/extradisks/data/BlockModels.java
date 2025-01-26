package de.melanx.extradisks.data;

import de.melanx.extradisks.ExtraDisks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockModels extends BlockModelProvider {

    public BlockModels(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ExtraDisks.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ModelFile.UncheckedModelFile modelFile = new ModelFile.UncheckedModelFile("refinedstorage:block/disk/disk");
        this.getBuilder("block/disk/item_disk").parent(modelFile).texture("base", "block/disk/item_disk");
        this.getBuilder("block/disk/fluid_disk").parent(modelFile).texture("base", "block/disk/fluid_disk");
        this.getBuilder("block/disk/chemical_disk").parent(modelFile).texture("base", "block/disk/chemical_disk");
    }
}
