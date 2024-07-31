package pepe.inventorymod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import pepe.inventorymod.InventoryMod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = InventoryMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KeyInputHandler {

    private static final KeyMapping SAVE_INVENTORY_KEY = new KeyMapping("key.inventorymod.save_inventory", GLFW.GLFW_KEY_F7, "key.categories.inventorymod");
    private static final KeyMapping SAVE_JEI_PAGE_KEY = new KeyMapping("key.inventorymod.save_jei_page", GLFW.GLFW_KEY_KP_MULTIPLY, "key.categories.inventorymod");
    private static final KeyMapping RAYTRACE_BLOCK_KEY = new KeyMapping("key.inventorymod.raytrace_block", GLFW.GLFW_KEY_F8, "key.categories.inventorymod");

    private static final long DELAY = 2000; // 2 seconds in milliseconds
    private static final Map<KeyMapping, Long> lastKeyPressTime = new HashMap<>();

    public KeyInputHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(SAVE_INVENTORY_KEY);
        event.register(SAVE_JEI_PAGE_KEY);
        event.register(RAYTRACE_BLOCK_KEY);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.screen == null) {
                processKey(SAVE_INVENTORY_KEY, () -> InventoryUtils.saveInventory(minecraft.player));
                processKey(SAVE_JEI_PAGE_KEY, InventoryUtils::saveJEIPage);
                processKey(RAYTRACE_BLOCK_KEY, () -> InventoryUtils.outputBlockStates(minecraft));
            }
        }
    }

    @SubscribeEvent
    public void onKeyPressed(ScreenEvent.KeyPressed event) {
        processKey(SAVE_INVENTORY_KEY, () -> InventoryUtils.saveInventory(Minecraft.getInstance().player), event.getKeyCode(), event.getScanCode());
        processKey(SAVE_JEI_PAGE_KEY, InventoryUtils::saveJEIPage, event.getKeyCode(), event.getScanCode());
        processKey(RAYTRACE_BLOCK_KEY, () -> InventoryUtils.outputBlockStates(Minecraft.getInstance()), event.getKeyCode(), event.getScanCode());
    }

    private void processKey(KeyMapping key, Runnable action) {
        long currentTime = System.currentTimeMillis();
        long lastTime = lastKeyPressTime.getOrDefault(key, 0L);
        if (currentTime - lastTime >= DELAY) {
            action.run();
            lastKeyPressTime.put(key, currentTime);
        }
    }

    private void processKey(KeyMapping key, Runnable action, int keyCode, int scanCode) {
        if (key.isActiveAndMatches(InputConstants.getKey(keyCode, scanCode))) {
            processKey(key, action);
        }
    }
}
