package de.melanx.extradisks.loottable;

import com.mojang.serialization.MapCodec;
import com.refinedmods.refinedstorage.common.content.DirectRegistryCallback;
import de.melanx.extradisks.ExtraDisks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

public class ExtraLootFunctions {

    public static void register() {
        DirectRegistryCallback<MapCodec<? extends LootItemFunction>> registryCallback = new DirectRegistryCallback<>(BuiltInRegistries.LOOT_FUNCTION_TYPE);
        registryCallback.register(Identifier.fromNamespaceAndPath(ExtraDisks.MODID, "storage_block"), () -> ExtraStorageBlockLootFunction.FUNCTION_CODEC);
    }
}
