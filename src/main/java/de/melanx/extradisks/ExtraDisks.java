package de.melanx.extradisks;

import de.melanx.extradisks.items.ExtraItems;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ExtraDisks.MODID)
public class ExtraDisks {

    public static final String MODID = "extradisks";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ItemGroup ModGroup = new CreativeTab();

    public ExtraDisks() {
        ExtraItems.init();
        MinecraftForge.EVENT_BUS.register(this);
    }
}
