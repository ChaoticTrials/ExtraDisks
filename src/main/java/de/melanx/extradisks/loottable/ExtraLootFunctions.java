package de.melanx.extradisks.loottable;

import de.melanx.extradisks.ExtraDisks;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ExtraLootFunctions {
    public static LootFunctionType STORAGE_BLOCK;

    public static void register() {
        STORAGE_BLOCK = Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(ExtraDisks.MODID, "storage_block"), new LootFunctionType(new ExtraStorageBlockLootFunction.Serializer()));
    }
}
