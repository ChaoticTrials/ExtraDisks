package de.melanx.extradisks.data;

import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.items.ExtraItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.fml.RegistryObject;

public class ModItemModels extends ItemModelProvider {
    public ModItemModels(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, ExtraDisks.MODID, helper);
    }

    @Override
    protected void registerModels() {
        for (RegistryObject<Item> item : ExtraItems.ITEMS.getEntries())
            generateItem(item.get());
    }

    private void generateItem(Item item) {
        String path = item.getRegistryName().getPath();
        getBuilder(path).parent(getExistingFile(mcLoc("item/handheld")))
                .texture("layer0", "item/" + path);
    }
}
