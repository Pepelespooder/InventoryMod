package pepe.inventorymod.util;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
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
import pepe.inventorymod.config.ModConfig;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import me.shedaniel.rei.api.client.REIRuntime;
import me.shedaniel.rei.api.client.overlay.OverlayListWidget;
import me.shedaniel.rei.api.client.overlay.ScreenOverlay;
import me.shedaniel.rei.api.common.entry.EntryStack;

public class InventoryUtils {
    private static final Logger LOGGER = LogManager.getLogger(InventoryUtils.class);

    private static ModConfig getConfig() {
        return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }

    public static void saveInventory(Player player) {
        if (player == null) {
            LOGGER.warn("Player is null, cannot save inventory");
            return;
        }

        ModConfig config = getConfig();
        String regex = config.inventoryRegex;
        Pattern pattern = Pattern.compile(regex);

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
                        String entry = modid + ":" + itemName;
                        if (pattern.matcher(entry).matches()) {
                            writer.write(entry + "\n");
                        }
                    }
                }
                LOGGER.info("Inventory saved at {}", inventoryFilePath.toAbsolutePath());
            }
        } catch (IOException e) {
            LOGGER.error("Failed to create config directory or save inventory", e);
        }
    }

    public static void saveREIPage() {
        if (REIRuntime.getInstance() == null || !REIRuntime.getInstance().isOverlayVisible()) {
            LOGGER.warn("REI runtime is not available or the overlay is not visible, cannot save REI page");
            return;
        }

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

        ModConfig config = getConfig();
        String regex = config.reiPageRegex;
        Pattern pattern = Pattern.compile(regex);

        // Handle entries based on their actual type
        Iterable<?> entries = listWidget.getEntries().toList(); // Assuming this returns Iterable

        LOGGER.info("Found entries in REI overlay.");

        Path configDirPath = Paths.get("config", Inventorymod.MODID);
        try {
            Files.createDirectories(configDirPath);
            LOGGER.info("Config directory created at {}", configDirPath.toAbsolutePath());

            Path reiPageFilePath = configDirPath.resolve("rei_page.txt");
            try (FileWriter writer = new FileWriter(reiPageFilePath.toFile())) {
                writer.write("Current REI Ingredient List Overlay:\n");
                for (Object entry : entries) {
                    if (entry instanceof EntryStack) {
                        EntryStack<?> stack = (EntryStack<?>) entry;
                        if (stack.getValue() instanceof ItemStack) {
                            ItemStack itemStack = (ItemStack) stack.getValue();
                            String modid = BuiltInRegistries.ITEM.getKey(itemStack.getItem()).getNamespace();
                            String itemName = BuiltInRegistries.ITEM.getKey(itemStack.getItem()).getPath();
                            String itemEntry = modid + ":" + itemName;
                            if (pattern.matcher(itemEntry).matches()) {
                                writer.write(itemEntry + "\n");
                            }
                        }
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
