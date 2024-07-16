package PepeLeSpooder.inventorymod.util;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import PepeLeSpooder.inventorymod.InventoryMod;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = InventoryMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KeyInputHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final KeyMapping saveInventoryKey = new KeyMapping("key.save_inventory", 256, "category.inventorymod");
    public static final KeyMapping saveJEIKey = new KeyMapping("key.save_jei", 334, "category.inventorymod");

    public static void registerKeys() {
        ClientRegistry.registerKeyBinding(saveInventoryKey);
        ClientRegistry.registerKeyBinding(saveJEIKey);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            handleKeyInput();
        }
    }

    private static void handleKeyInput() {
        if (saveInventoryKey.consumeClick()) {
            // Code to save inventory
            InventoryUtils.saveInventory(Minecraft.getInstance().player);
        }
        if (saveJEIKey.consumeClick()) {
            // Code to save JEI page
            InventoryUtils.saveJEIPage();
        }
    }
}
