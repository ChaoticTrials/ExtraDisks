package de.melanx.extradisks;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {
    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SERVER_CONFIG;

    static {
        init(SERVER_BUILDER);
        SERVER_CONFIG = SERVER_BUILDER.build();
    }

    public static ForgeConfigSpec.IntValue tier5usage;
    public static ForgeConfigSpec.IntValue tier6usage;
    public static ForgeConfigSpec.IntValue tier7usage;
    public static ForgeConfigSpec.IntValue tier8usage;
    public static ForgeConfigSpec.IntValue tier9usage;
    public static ForgeConfigSpec.IntValue tier10usage;
    public static ForgeConfigSpec.IntValue tier11usage;
    public static ForgeConfigSpec.IntValue tier12usage;

    public static ForgeConfigSpec.IntValue fluid_tier5usage;
    public static ForgeConfigSpec.IntValue fluid_tier6usage;
    public static ForgeConfigSpec.IntValue fluid_tier7usage;
    public static ForgeConfigSpec.IntValue fluid_tier8usage;
    public static ForgeConfigSpec.IntValue fluid_tier9usage;

    private static void init(ForgeConfigSpec.Builder builder) {
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
        fluid_tier5usage = builder.defineInRange("16384k", 12, 0, Integer.MAX_VALUE);
        fluid_tier6usage = builder.defineInRange("65536k", 14, 0, Integer.MAX_VALUE);
        fluid_tier7usage = builder.defineInRange("262144k", 16, 0, Integer.MAX_VALUE);
        fluid_tier8usage = builder.defineInRange("1048576k", 18, 0, Integer.MAX_VALUE);
        fluid_tier9usage = builder.defineInRange("infinite", 20, 0, Integer.MAX_VALUE);
        builder.pop();
    }
}
