package PepeLeSpooder.inventorymod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import PepeLeSpooder.inventorymod.util.ModKeyBindings;
import PepeLeSpooder.inventorymod.util.KeyInputHandler;

@Mod(InventoryMod.MODID)
public class InventoryMod {
    public static final String MODID = "inventorymod";

    public InventoryMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Common setup code
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        ModKeyBindings.register();
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
    }
}
