package pepe.inventorymod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pepe.inventorymod.util.KeyInputHandler;

public class InventoryModClient implements ClientModInitializer {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Inventory Mod Client");
        KeyInputHandler.registerKeyBindings();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && mc.screen == null) {
                KeyInputHandler.handleKeyInput(mc.player);
            }
        });
    }
}
