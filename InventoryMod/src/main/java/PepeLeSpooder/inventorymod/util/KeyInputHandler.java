package PepeLeSpooder.inventorymod.util;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import PepeLeSpooder.inventorymod.InventoryMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EventBusSubscriber(modid = InventoryMod.MODID, bus = Bus.FORGE, value = Dist.CLIENT)
public class KeyInputHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (ModKeyBindings.saveInventoryKey.isDown()) {
            LOGGER.info("Save Inventory Key Pressed");
            InventoryUtils.saveInventory(Minecraft.getInstance().player);
        } else if (ModKeyBindings.saveJeiPageKey.isDown()) {
            LOGGER.info("Save JEI Page Key Pressed");
            InventoryUtils.saveJeiPage();
        }
    }
}
