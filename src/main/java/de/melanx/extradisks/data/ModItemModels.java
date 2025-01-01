package de.melanx.extradisks.data;

import de.melanx.extradisks.ExtraDisks;
import de.melanx.extradisks.Registration;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModItemModels extends ItemModelProvider {

    public ModItemModels(PackOutput output, ExistingFileHelper helper) {
        super(output, ExtraDisks.MODID, helper);
    }

    @Override
    protected void registerModels() {
        Registration.ITEMS.getEntries().forEach(holder -> {
            if (holder.get() instanceof BlockItem) {
                this.generateBlockItemModel(holder);
            } else {
                this.generateItem(holder);
            }
        });
    }

    private void generateItem(DeferredHolder<Item, ? extends Item> holder) {
        String path = holder.getId().getPath();
        this.getBuilder(path).parent(this.getExistingFile(this.mcLoc("item/handheld")))
                .texture("layer0", "item/" + path);
    }

    private void generateBlockItemModel(DeferredHolder<Item, ? extends Item> holder) {
        String path = holder.getId().getPath();
        this.getBuilder(path).parent(new ModelFile.UncheckedModelFile(this.modLoc("block/" + path)));
    }
}
