package pepe.inventorymod.util;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import pepe.inventorymod.InventoryMod;

@Mod.EventBusSubscriber(modid = InventoryMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeyInputHandler {

    private static final KeyMapping SAVE_INVENTORY_KEY = new KeyMapping("key.inventorymod.save_inventory", GLFW.GLFW_KEY_F7, "key.categories.inventorymod");
    private static final KeyMapping SAVE_JEI_PAGE_KEY = new KeyMapping("key.inventorymod.save_jei_page", GLFW.GLFW_KEY_KP_MULTIPLY, "key.categories.inventorymod");

    public KeyInputHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(SAVE_INVENTORY_KEY);
        event.register(SAVE_JEI_PAGE_KEY);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        if (SAVE_INVENTORY_KEY.isDown() && Minecraft.getInstance().screen == null) {
            InventoryUtils.saveInventory(Minecraft.getInstance().player);
        }
        if (SAVE_JEI_PAGE_KEY.isDown() && Minecraft.getInstance().screen == null) {
            InventoryUtils.saveJEIPage();
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (Minecraft.getInstance().screen != null) {
                if (SAVE_INVENTORY_KEY.isDown()) {
                    InventoryUtils.saveInventory(Minecraft.getInstance().player);
                }
                if (SAVE_JEI_PAGE_KEY.isDown()) {
                    InventoryUtils.saveJEIPage();
                }
            }
        }
    }
}
