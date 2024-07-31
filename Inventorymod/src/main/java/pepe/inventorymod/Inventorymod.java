package pepe.inventorymod;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pepe.inventorymod.config.ModConfig;
import pepe.inventorymod.util.KeyInputHandler;

public class Inventorymod implements ModInitializer, ClientModInitializer {
    public static final String MODID = "inventorymod";
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Inventory Mod");
        try {
            AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        } catch (Exception e) {
            LOGGER.error("Failed to register ModConfig: " + e.getMessage(), e);
        }
    }

    @Override
    public void onInitializeClient() {
        try {
            KeyInputHandler.initialize();
        } catch (Exception e) {
            LOGGER.error("Failed to register key bindings: " + e.getMessage(), e);
        }
    }
}
