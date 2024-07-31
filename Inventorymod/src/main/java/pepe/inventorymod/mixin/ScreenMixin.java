package pepe.inventorymod.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pepe.inventorymod.util.KeyInputHandler;

@Mixin(Screen.class)
public class ScreenMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();
        if (client.player != null && client.screen != null) {
            long windowHandle = client.getWindow().getWindow();
            if (GLFW.glfwGetKey(windowHandle, GLFW.GLFW_KEY_F7) == GLFW.GLFW_PRESS) {
                KeyInputHandler.handleKeyPress(GLFW.GLFW_KEY_F7, client.player);
            }
            if (GLFW.glfwGetKey(windowHandle, GLFW.GLFW_KEY_KP_MULTIPLY) == GLFW.GLFW_PRESS) {
                KeyInputHandler.handleKeyPress(GLFW.GLFW_KEY_KP_MULTIPLY, client.player);
            }
            if (GLFW.glfwGetKey(windowHandle, GLFW.GLFW_KEY_F8) == GLFW.GLFW_PRESS) {
                KeyInputHandler.handleKeyPress(GLFW.GLFW_KEY_F8, client.player);
            }
        }
    }
}
