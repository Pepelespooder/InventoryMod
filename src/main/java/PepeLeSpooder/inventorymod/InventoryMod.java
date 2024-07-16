package PepeLeSpooder.inventorymod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import PepeLeSpooder.inventorymod.util.KeyInputHandler;

@Mod(InventoryMod.MODID)
public class InventoryMod {
    public static final String MODID = "inventorymod";

    public InventoryMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);

        KeyInputHandler.registerKeys();
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Common setup code
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // Client-specific setup, e.g., key bindings
    }
}
