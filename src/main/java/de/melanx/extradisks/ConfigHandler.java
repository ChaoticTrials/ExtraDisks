package de.melanx.extradisks;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class ConfigHandler {

    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SERVER_CONFIG;

    static {
        init(SERVER_BUILDER);
        SERVER_CONFIG = SERVER_BUILDER.build();
    }

    public static ForgeConfigSpec.BooleanValue retro;
    public static ForgeConfigSpec.BooleanValue moreDisks;

    public static void init(ForgeConfigSpec.Builder builder) {
        builder.push("general");
        retro = builder.comment("If set to true, the less op Disk values by Reborn Storage will be used for fluid disks.")
                .define("retro", false);
        moreDisks = builder.comment("If set to true, 4 extra tiers for item storage will be added. Automatically turns retro mode off.")
                .define("higher_disks", false);
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        ExtraDisks.LOGGER.debug("Loading config file {}", path);

        final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();

        configData.load();

        spec.setConfig(configData);
    }

}
