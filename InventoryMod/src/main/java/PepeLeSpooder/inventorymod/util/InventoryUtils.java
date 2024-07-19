package PepeLeSpooder.inventorymod.util;

import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.api.runtime.IIngredientListOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class InventoryUtils {

    private static final Logger LOGGER = LogManager.getLogger();
    private static IJeiRuntime jeiRuntime;

    public static void setJeiRuntime(IJeiRuntime runtime) {
        jeiRuntime = runtime;
    }

    private static File getSaveFile(String filename) {
        Path configDir = FMLPaths.CONFIGDIR.get();
        File exportDir = new File(configDir.toFile(), "InventoryExport");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        return new File(exportDir, filename);
    }

    public static void saveInventory(PlayerEntity player) {
        LOGGER.info("Saving Inventory");
        if (player == null) {
            LOGGER.warn("Player is null, cannot save inventory.");
            return;
        }

        File saveFile = getSaveFile("saved_inventory.txt");
        try (FileWriter writer = new FileWriter(saveFile)) {
            for (ItemStack stack : player.inventory.items) {
                if (!stack.isEmpty()) {
                    ResourceLocation itemRegistryName = ForgeRegistries.ITEMS.getKey(stack.getItem());
                    writer.write(String.format("%s\n", itemRegistryName));
                }
            }
            LOGGER.info("Inventory saved to " + saveFile.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.error("Failed to save inventory", e);
        }
    }

    public static void saveJeiPage() {
        LOGGER.info("Saving JEI Page");
        if (jeiRuntime == null) {
            LOGGER.warn("JEI runtime is not set.");
            return;
        }

        IIngredientListOverlay ingredientListOverlay = jeiRuntime.getIngredientListOverlay();
        List<Object> visibleIngredients = ingredientListOverlay.getVisibleIngredients();
        if (visibleIngredients.isEmpty()) {
            LOGGER.warn("No visible items in JEI overlay.");
            return;
        }

        File saveFile = getSaveFile("saved_jei_page.txt");
        try (FileWriter writer = new FileWriter(saveFile)) {
            for (Object ingredient : visibleIngredients) {
                if (ingredient instanceof ItemStack) {
                    ItemStack stack = (ItemStack) ingredient;
                    if (!stack.isEmpty()) {
                        ResourceLocation itemRegistryName = ForgeRegistries.ITEMS.getKey(stack.getItem());
                        writer.write(String.format("%s\n", itemRegistryName));
                    }
                }
            }
            LOGGER.info("JEI page saved to " + saveFile.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.error("Failed to save JEI page", e);
        }
    }
}
