package de.melanx.extradisks.loottable;

import de.melanx.extradisks.ExtraDisks;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class ExtraLootFunctions {

    public static LootItemFunctionType STORAGE_BLOCK;

    public static void register() {
        STORAGE_BLOCK = Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(ExtraDisks.MODID, "storage_block"), new LootItemFunctionType(new ExtraStorageBlockLootFunction.Serializer()));
    }
}
