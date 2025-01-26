package de.melanx.extradisks;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ExtraDisks.MODID)
public final class ExtraDisks {

    public static final String MODID = "extradisks";
    public static final Logger LOGGER = LoggerFactory.getLogger(ExtraDisks.class);

    public ExtraDisks(IEventBus modBus) {
        Registration.init(modBus);
        modBus.addListener(Registration::registerExtras);
    }
}
