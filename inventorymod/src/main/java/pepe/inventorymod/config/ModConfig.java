package pepe.inventorymod.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import pepe.inventorymod.InventoryMod;

@Mod.EventBusSubscriber(modid = InventoryMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModConfig {

    public static final String CATEGORY_KEYS = "keys";

    public static ForgeConfigSpec.IntValue SAVE_INVENTORY_KEY;
    public static ForgeConfigSpec.IntValue SAVE_JEI_PAGE_KEY;

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CONFIG;

    static {
        BUILDER.push(CATEGORY_KEYS);
        SAVE_INVENTORY_KEY = BUILDER.comment("Key for saving inventory")
                .defineInRange("saveInventoryKey", 293, 0, 256); // Default to F7
        SAVE_JEI_PAGE_KEY = BUILDER.comment("Key for saving JEI page")
                .defineInRange("saveJeiPageKey", 334, 0, 256); // Default to Keypad *
        BUILDER.pop();

        CONFIG = BUILDER.build();
    }
}
