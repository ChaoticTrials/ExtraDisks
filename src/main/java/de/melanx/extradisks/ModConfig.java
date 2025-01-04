package de.melanx.extradisks;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModConfig {
    private static final ModConfigSpec.Builder CONFIG_BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec CONFIG;

    static {
        init(CONFIG_BUILDER);
        CONFIG = CONFIG_BUILDER.build();
    }

    public static ModConfigSpec.IntValue tier5usage;
    public static ModConfigSpec.IntValue tier6usage;
    public static ModConfigSpec.IntValue tier7usage;
    public static ModConfigSpec.IntValue tier8usage;
    public static ModConfigSpec.IntValue tier9usage;
    public static ModConfigSpec.IntValue tier10usage;
    public static ModConfigSpec.IntValue tier11usage;
    public static ModConfigSpec.IntValue tier12usage;

    public static ModConfigSpec.IntValue fluid_tier5usage;
    public static ModConfigSpec.IntValue fluid_tier6usage;
    public static ModConfigSpec.IntValue fluid_tier7usage;
    public static ModConfigSpec.IntValue fluid_tier8usage;
    public static ModConfigSpec.IntValue fluid_tier9usage;

    public static ModConfigSpec.IntValue chemical_tier5usage;
    public static ModConfigSpec.IntValue chemical_tier6usage;
    public static ModConfigSpec.IntValue chemical_tier7usage;
    public static ModConfigSpec.IntValue chemical_tier8usage;
    public static ModConfigSpec.IntValue chemical_tier9usage;

    private static void init(@SuppressWarnings("SameParameterValue") ModConfigSpec.Builder builder) {
        builder.push("storageblocks");
        builder.push("item").comment("item storage blocks energy usage");
        tier5usage = builder.defineInRange("256k", 12, 0, Integer.MAX_VALUE);
        tier6usage = builder.defineInRange("1024k", 14, 0, Integer.MAX_VALUE);
        tier7usage = builder.defineInRange("4096k", 16, 0, Integer.MAX_VALUE);
        tier8usage = builder.defineInRange("16384k", 18, 0, Integer.MAX_VALUE);
        tier9usage = builder.defineInRange("65536k", 20, 0, Integer.MAX_VALUE);
        tier10usage = builder.defineInRange("262144k", 22, 0, Integer.MAX_VALUE);
        tier11usage = builder.defineInRange("1048576k", 24, 0, Integer.MAX_VALUE);
        tier12usage = builder.defineInRange("infinite", 26, 0, Integer.MAX_VALUE);
        builder.pop();

        builder.push("fluid").comment("fluid storage blocks energy usage");
        fluid_tier5usage = builder.defineInRange("16384b", 12, 0, Integer.MAX_VALUE);
        fluid_tier6usage = builder.defineInRange("65536b", 14, 0, Integer.MAX_VALUE);
        fluid_tier7usage = builder.defineInRange("262144b", 16, 0, Integer.MAX_VALUE);
        fluid_tier8usage = builder.defineInRange("1048576b", 18, 0, Integer.MAX_VALUE);
        fluid_tier9usage = builder.defineInRange("infinite", 20, 0, Integer.MAX_VALUE);
        builder.pop();

        builder.push("chemical").comment("chemical storage blocks energy usage");
        chemical_tier5usage = builder.defineInRange("65536b", 12, 0, Integer.MAX_VALUE);
        chemical_tier6usage = builder.defineInRange("262144b", 14, 0, Integer.MAX_VALUE);
        chemical_tier7usage = builder.defineInRange("1048576b", 16, 0, Integer.MAX_VALUE);
        chemical_tier8usage = builder.defineInRange("8388608b", 18, 0, Integer.MAX_VALUE);
        chemical_tier9usage = builder.defineInRange("infinite", 20, 0, Integer.MAX_VALUE);
        builder.pop();
    }
}
