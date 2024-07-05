package pepe.inventorymod.util;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;
import pepe.inventorymod.InventoryMod;

@JeiPlugin
public class MyJeiPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(InventoryMod.MODID, "default");
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        InventoryUtils.setJeiRuntime(jeiRuntime);
    }
}
