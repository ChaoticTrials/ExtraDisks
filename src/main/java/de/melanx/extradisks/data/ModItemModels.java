package de.melanx.extradisks.data;

import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.items.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModels extends ItemModelProvider {
    public ModItemModels(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, ExtraDisks.MODID, helper);
    }

    @Override
    protected void registerModels() {
        Registration.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(item -> {
            if (item instanceof BlockItem) {
                this.generateBlockItemModel(item);
            } else {
                this.generateItem(item);
            }
        });
    }

    private void generateItem(Item item) {
        //noinspection ConstantConditions
        String path = item.getRegistryName().getPath();
        this.getBuilder(path).parent(this.getExistingFile(this.mcLoc("item/handheld")))
                .texture("layer0", "item/" + path);
    }

    private void generateBlockItemModel(Item item) {
        //noinspection ConstantConditions
        String path = item.getRegistryName().getPath();
        this.getBuilder(path).parent(new ModelFile.UncheckedModelFile(this.modLoc("block/" + path)));
    }
}
