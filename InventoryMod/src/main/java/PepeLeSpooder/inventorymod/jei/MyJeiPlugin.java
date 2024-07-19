package PepeLeSpooder.inventorymod.jei;

import PepeLeSpooder.inventorymod.InventoryMod;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.util.ResourceLocation;
import PepeLeSpooder.inventorymod.util.InventoryUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@JeiPlugin
public class MyJeiPlugin implements IModPlugin {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(InventoryMod.MODID, "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        // Register your custom recipes or handlers here
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        InventoryUtils.setJeiRuntime(jeiRuntime);
        LOGGER.info("JEI Runtime is now available.");
    }
}
