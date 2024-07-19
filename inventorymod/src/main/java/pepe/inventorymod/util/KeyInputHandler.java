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
import pepe.inventorymod.config.ModConfig;

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
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.screen == null) {
                if (SAVE_INVENTORY_KEY.isDown()) {
                    InventoryUtils.saveInventory(minecraft.player);
                }
                if (SAVE_JEI_PAGE_KEY.isDown()) {
                    InventoryUtils.saveJEIPage();
                }
            }
        }
    }

    @SubscribeEvent
    public void onKeyPressed(ScreenEvent.KeyPressed event) {
        if (SAVE_INVENTORY_KEY.isActiveAndMatches(InputConstants.getKey(event.getKeyCode(), event.getScanCode()))) {
            InventoryUtils.saveInventory(Minecraft.getInstance().player);
        }
        if (SAVE_JEI_PAGE_KEY.isActiveAndMatches(InputConstants.getKey(event.getKeyCode(), event.getScanCode()))) {
            InventoryUtils.saveJEIPage();
        }
    }
}
