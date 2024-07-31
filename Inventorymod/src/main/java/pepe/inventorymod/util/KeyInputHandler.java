package pepe.inventorymod.util;

import com.mojang.blaze3d.platform.InputConstants;
import me.shedaniel.rei.api.client.REIRuntime;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    private static final Logger LOGGER = LogManager.getLogger(KeyInputHandler.class);
    private static KeyMapping saveInventoryKey;
    private static KeyMapping saveReiPageKey;
    private static KeyMapping saveBlockStatesKey;
    private static final long WAIT_TIME_MS = 5000; // 5 seconds
    private static long lastInventorySaveTime = 0;
    private static long lastReiPageSaveTime = 0;
    private static long lastBlockStatesSaveTime = 0;

    public static void registerKeyBindings() {
        saveInventoryKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.inventorymod.save_inventory",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_F7,
                "key.categories.inventorymod"
        ));
        saveReiPageKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.inventorymod.save_rei_page",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_MULTIPLY,
                "key.categories.inventorymod"
        ));
        saveBlockStatesKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.inventorymod.save_block_states",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_F8,
                "key.categories.inventorymod"
        ));
    }

    public static void initialize() {
        registerKeyBindings();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                handleKeyInput(client.player);
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(KeyInputHandler::handleGuiKeyInput);
    }

    private static void handleGuiKeyInput(Minecraft client) {
        if (client.screen != null && client.player != null) {
            long windowHandle = client.getWindow().getWindow();
            if (InputConstants.isKeyDown(windowHandle, saveInventoryKey.getDefaultKey().getValue())) {
                handleKeyPress(saveInventoryKey.getDefaultKey().getValue(), 0);
            }
            if (InputConstants.isKeyDown(windowHandle, saveReiPageKey.getDefaultKey().getValue())) {
                handleKeyPress(saveReiPageKey.getDefaultKey().getValue(), 0);
            }
            if (InputConstants.isKeyDown(windowHandle, saveBlockStatesKey.getDefaultKey().getValue())) {
                handleKeyPress(saveBlockStatesKey.getDefaultKey().getValue(), 0);
            }
        }
    }

    public static void handleKeyInput(Player player) {
        long currentTime = System.currentTimeMillis();
        if (saveInventoryKey.isDown() && (currentTime - lastInventorySaveTime >= WAIT_TIME_MS)) {
            lastInventorySaveTime = currentTime;
            LOGGER.info("SAVE_INVENTORY_KEY pressed");
            InventoryUtils.saveInventory(player);
        }
        if (saveReiPageKey.isDown() && (currentTime - lastReiPageSaveTime >= WAIT_TIME_MS)) {
            if (REIRuntime.getInstance() == null || !REIRuntime.getInstance().isOverlayVisible()) {
                LOGGER.warn("REI runtime is not available or the overlay is not visible, cannot save REI page");
                return;
            }
            lastReiPageSaveTime = currentTime;
            LOGGER.info("SAVE_REI_PAGE_KEY pressed");
            InventoryUtils.saveREIPage();
        }
        if (saveBlockStatesKey.isDown() && (currentTime - lastBlockStatesSaveTime >= WAIT_TIME_MS)) {
            lastBlockStatesSaveTime = currentTime;
            LOGGER.info("SAVE_BLOCK_STATES_KEY pressed");
            InventoryUtils.saveBlockAndItemStates(player);
        }
    }

    private static void handleKeyPress(int keyCode, int scanCode) {
        long currentTime = System.currentTimeMillis();
        Minecraft client = Minecraft.getInstance();
        if (keyCode == saveInventoryKey.getDefaultKey().getValue() && (currentTime - lastInventorySaveTime >= WAIT_TIME_MS)) {
            lastInventorySaveTime = currentTime;
            LOGGER.info("SAVE_INVENTORY_KEY pressed");
            InventoryUtils.saveInventory(client.player);
        }
        if (keyCode == saveReiPageKey.getDefaultKey().getValue() && (currentTime - lastReiPageSaveTime >= WAIT_TIME_MS)) {
            if (REIRuntime.getInstance() == null || !REIRuntime.getInstance().isOverlayVisible()) {
                LOGGER.warn("REI runtime is not available or the overlay is not visible, cannot save REI page");
                return;
            }
            lastReiPageSaveTime = currentTime;
            LOGGER.info("SAVE_REI_PAGE_KEY pressed");
            InventoryUtils.saveREIPage();
        }
        if (keyCode == saveBlockStatesKey.getDefaultKey().getValue() && (currentTime - lastBlockStatesSaveTime >= WAIT_TIME_MS)) {
            lastBlockStatesSaveTime = currentTime;
            LOGGER.info("SAVE_BLOCK_STATES_KEY pressed");
            InventoryUtils.saveBlockAndItemStates(client.player);
        }
    }
}
