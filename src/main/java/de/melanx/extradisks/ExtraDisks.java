package de.melanx.extradisks;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ExtraDisks.MODID)
public final class ExtraDisks {

    public static final String MODID = "extradisks";
    public static final Logger LOGGER = LoggerFactory.getLogger(ExtraDisks.class);

    public ExtraDisks(IEventBus modBus, ModContainer container) {
        Registration.init(modBus);
        modBus.addListener(Registration::registerExtras);
        container.registerConfig(ModConfig.Type.SERVER, de.melanx.extradisks.ModConfig.CONFIG);
    }
}
