package pepe.inventorymod.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pepe.inventorymod.Inventorymod;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import me.shedaniel.rei.api.client.REIRuntime;
import me.shedaniel.rei.api.client.overlay.OverlayListWidget;
import me.shedaniel.rei.api.client.overlay.ScreenOverlay;
import me.shedaniel.rei.api.common.entry.EntryStack;

public class InventoryUtils {
    private static final Logger LOGGER = LogManager.getLogger(InventoryUtils.class);

    public static void saveInventory(Player player) {
        if (player == null) {
            LOGGER.warn("Player is null, cannot save inventory");
            return;
        }

        Path configDirPath = Paths.get("config", Inventorymod.MODID);
        try {
            Files.createDirectories(configDirPath);
            LOGGER.info("Config directory created at {}", configDirPath.toAbsolutePath());

            Path inventoryFilePath = configDirPath.resolve("inventory.txt");
            try (FileWriter writer = new FileWriter(inventoryFilePath.toFile())) {
                for (ItemStack stack : player.getInventory().items) {
                    if (!stack.isEmpty()) {
                        String modid = BuiltInRegistries.ITEM.getKey(stack.getItem()).getNamespace();
                        String itemName = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
                        writer.write(modid + ":" + itemName + "\n");
                    }
                }
                LOGGER.info("Inventory saved at {}", inventoryFilePath.toAbsolutePath());
            }
        } catch (IOException e) {
            LOGGER.error("Failed to create config directory or save inventory", e);
        }
    }

    public static void saveREIPage() {
        // Check if the REI runtime is available and the overlay is visible
        if (REIRuntime.getInstance() == null || !REIRuntime.getInstance().isOverlayVisible()) {
            LOGGER.warn("REI runtime is not available or the overlay is not visible, cannot save REI page");
            return;
        }

        // Retrieve the REI overlay
        Optional<ScreenOverlay> overlayOpt = REIRuntime.getInstance().getOverlay();
        if (overlayOpt.isEmpty()) {
            LOGGER.warn("REI overlay is not available.");
            return;
        }

        ScreenOverlay overlay = overlayOpt.get();
        OverlayListWidget listWidget = overlay.getEntryList();
        if (listWidget == null) {
            LOGGER.warn("REI entry list widget is not available.");
            return;
        }

        // Collect entries from Stream<EntryStack<?>> to List<EntryStack<?>>
        List<EntryStack<?>> visibleStacks = listWidget.getEntries()
                .filter(entry -> entry instanceof EntryStack<?>)
                .map(entry -> (EntryStack<?>) entry)
                .collect(Collectors.toList());

        Path configDirPath = Paths.get("config", Inventorymod.MODID);
        try {
            Files.createDirectories(configDirPath);
            LOGGER.info("Config directory created at {}", configDirPath.toAbsolutePath());

            Path reiPageFilePath = configDirPath.resolve("rei_page.txt");
            try (FileWriter writer = new FileWriter(reiPageFilePath.toFile())) {
                writer.write("Current REI Ingredient List Overlay:\n");
                for (EntryStack<?> stack : visibleStacks) {
                    if (stack.getValue() instanceof ItemStack) {
                        ItemStack itemStack = (ItemStack) stack.getValue();
                        String modid = BuiltInRegistries.ITEM.getKey(itemStack.getItem()).getNamespace();
                        String itemName = BuiltInRegistries.ITEM.getKey(itemStack.getItem()).getPath();
                        writer.write(modid + ":" + itemName + "\n");
                    }
                }
                LOGGER.info("Saved REI page at {}", reiPageFilePath.toAbsolutePath());
            }
        } catch (IOException e) {
            LOGGER.error("Failed to save REI page", e);
        }
    }

    public static void saveBlockAndItemStates(Player player) {
        if (player == null) {
            LOGGER.warn("Player is null, cannot save block and item states");
            return;
        }

        HitResult hitResult = player.pick(20.0D, 0.0F, false);
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
            BlockState blockState = player.level().getBlockState(blockPos);
            Block block = blockState.getBlock();

            Path configDirPath = Paths.get("config", Inventorymod.MODID);
            try {
                Files.createDirectories(configDirPath);
                LOGGER.info("Config directory created at {}", configDirPath.toAbsolutePath());

                Path statesFilePath = configDirPath.resolve("block_item_states.txt");
                try (FileWriter writer = new FileWriter(statesFilePath.toFile())) {
                    writer.write("Possible block states for " + BuiltInRegistries.BLOCK.getKey(block).toString() + ":\n");
                    for (BlockState state : block.getStateDefinition().getPossibleStates()) {
                        writer.write(state.toString() + "\n");
                    }
                    LOGGER.info("Saved block states at {}", statesFilePath.toAbsolutePath());
                }
            } catch (IOException e) {
                LOGGER.error("Failed to save block states", e);
            }
        } else {
            LOGGER.warn("No block targeted, cannot save block states");
        }
    }
}
