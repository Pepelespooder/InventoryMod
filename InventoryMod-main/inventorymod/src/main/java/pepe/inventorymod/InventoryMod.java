package pepe.inventorymod;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import pepe.inventorymod.config.ModConfig;
import pepe.inventorymod.util.KeyInputHandler;

@Mod(InventoryMod.MODID)
@Mod.EventBusSubscriber(modid = InventoryMod.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class InventoryMod {
    public static final String MODID = "inventorymod";

    public InventoryMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
        ModConfig.register(modEventBus);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // Common setup code
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // Client setup code
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
    }
}
