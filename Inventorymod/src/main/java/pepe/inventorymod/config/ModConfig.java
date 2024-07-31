package pepe.inventorymod.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "inventorymod")
public class ModConfig implements ConfigData {
    public boolean enableFeature = true;
    public String inventoryRegex = ".*"; // Default regex for inventory
    public String reiPageRegex = ".*";    // Default regex for REI page
}
