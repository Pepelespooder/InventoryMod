package PepeLeSpooder.inventorymod.util;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.api.runtime.IIngredientListOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import PepeLeSpooder.inventorymod.InventoryMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class InventoryUtils {

    private static final Logger LOGGER = LogManager.getLogger();
    private static IJeiRuntime jeiRuntime;

    public static void setJeiRuntime(IJeiRuntime runtime) {
        jeiRuntime = runtime;
        LOGGER.info("JEI Runtime has been set.");
    }

    public static void saveInventory(Player player) {
        if (player == null) return;

        File configDir = new File(FMLPaths.CONFIGDIR.get().toFile(), InventoryMod.MODID);
        if (!configDir.exists()) {
            configDir.mkdirs();
        }

        File inventoryFile = new File(configDir, "inventory.txt");
        try (FileWriter writer = new FileWriter(inventoryFile)) {
            for (ItemStack stack : player.getInventory().items) {
                if (!stack.isEmpty()) {
                    ResourceLocation itemKey = ForgeRegistries.ITEMS.getKey(stack.getItem());
                    String modid = itemKey.getNamespace();
                    String itemName = itemKey.getPath();
                    writer.write(modid + ":" + itemName + "\n");
                }
            }
            LOGGER.info("Inventory saved successfully.");
        } catch (IOException e) {
            LOGGER.error("Error saving inventory: ", e);
        }
    }

    public static void saveJEIPage() {
        if (jeiRuntime == null) {
            LOGGER.error("JEI Runtime is not available.");
            return;
        }

        IIngredientListOverlay ingredientListOverlay = jeiRuntime.getIngredientListOverlay();
        if (ingredientListOverlay == null) {
            LOGGER.error("Ingredient List Overlay is not available.");
            return;
        }

        File configDir = new File(FMLPaths.CONFIGDIR.get().toFile(), InventoryMod.MODID);
        File jeiFile = new File(configDir, "jei_page.txt");
        try (FileWriter writer = new FileWriter(jeiFile)) {
            List<ItemStack> visibleStacks = ingredientListOverlay.getVisibleIngredients(VanillaTypes.ITEM_STACK);
            for (ItemStack stack : visibleStacks) {
                if (!stack.isEmpty()) {
                    ResourceLocation itemKey = ForgeRegistries.ITEMS.getKey(stack.getItem());
                    String modid = itemKey.getNamespace();
                    String itemName = itemKey.getPath();
                    writer.write(modid + ":" + itemName + "\n");
                }
            }
            LOGGER.info("JEI page saved successfully.");
        } catch (IOException e) {
            LOGGER.error("Error saving JEI page: ", e);
        }
    }
}
