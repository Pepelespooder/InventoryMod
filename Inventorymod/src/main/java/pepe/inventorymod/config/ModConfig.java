package pepe.inventorymod.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "inventorymod")
public class ModConfig implements ConfigData {
    public boolean enableFeature = true;
}
