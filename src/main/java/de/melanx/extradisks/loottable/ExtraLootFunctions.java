package de.melanx.extradisks.loottable;

import com.mojang.serialization.MapCodec;
import de.melanx.extradisks.ExtraDisks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class ExtraLootFunctions {

    public static LootItemFunctionType<ExtraStorageBlockLootFunction> STORAGE_BLOCK;

    public static void register() {
        STORAGE_BLOCK = Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE, ResourceLocation.fromNamespaceAndPath(ExtraDisks.MODID, "storage_block"), new LootItemFunctionType<>(MapCodec.unit(new ExtraStorageBlockLootFunction())));
    }
}
