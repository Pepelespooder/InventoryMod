package PepeLeSpooder.inventorymod.util;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {

    public static KeyBinding saveInventoryKey;
    public static KeyBinding saveJeiPageKey;

    public static void register() {
        saveInventoryKey = new KeyBinding("key.inventorymod.save_inventory", GLFW.GLFW_KEY_F7, "key.categories.inventorymod");
        saveJeiPageKey = new KeyBinding("key.inventorymod.save_jei_page", GLFW.GLFW_KEY_KP_MULTIPLY, "key.categories.inventorymod");

        ClientRegistry.registerKeyBinding(saveInventoryKey);
        ClientRegistry.registerKeyBinding(saveJeiPageKey);
    }
}
