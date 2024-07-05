package pepe.inventorymod.util;

import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.runtime.IJeiRuntime;
import mezz.jei.api.runtime.IIngredientListOverlay;
import mezz.jei.api.constants.VanillaTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.loading.FMLPaths;
import pepe.inventorymod.InventoryMod;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryUtils {

    private static IJeiRuntime jeiRuntime;

    public static void setJeiRuntime(IJeiRuntime runtime) {
        jeiRuntime = runtime;
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
                    String modid = BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace();
                    String itemName = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
                    writer.write(modid + ":" + itemName + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveJEIPage() {
        if (jeiRuntime == null) return;

        IIngredientListOverlay ingredientListOverlay = jeiRuntime.getIngredientListOverlay();
        if (ingredientListOverlay != null) {
            File configDir = new File(FMLPaths.CONFIGDIR.get().toFile(), InventoryMod.MODID);
            if (!configDir.exists()) {
                configDir.mkdirs();
            }

            File jeiPageFile = new File(configDir, "jei_page.txt");
            try (FileWriter writer = new FileWriter(jeiPageFile)) {
                writer.write("Current JEI Ingredient List Overlay:\n");
                IIngredientType<ItemStack> itemStackType = VanillaTypes.ITEM_STACK;
                List<ItemStack> visibleStacks = ingredientListOverlay.getVisibleIngredients(itemStackType);
                String visibleStacksStr = visibleStacks.stream()
                        .map(stack -> BuiltInRegistries.ITEM.getKey(stack.getItem()).toString())
                        .collect(Collectors.joining("\n"));
                writer.write(visibleStacksStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
