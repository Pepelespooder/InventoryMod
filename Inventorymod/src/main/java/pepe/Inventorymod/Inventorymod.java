package com.yourdomain.inventorymod;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Mod(Inventorymod.MOD_ID)
@Mod.EventBusSubscriber(modid = Inventorymod.MOD_ID, value = Dist.CLIENT)
public class Inventorymod {
    public static final String MOD_ID = "inventorymod";
    private static final int KEY_F7 = GLFW.GLFW_KEY_F7;

    public Inventorymod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Client setup, if needed
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (event.getKey() == KEY_F7 && event.getAction() == GLFW.GLFW_PRESS) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                Player player = mc.player;
                AbstractContainerMenu inventory = player.containerMenu;
                StringBuilder content = new StringBuilder();

                inventory.slots.forEach(slot -> {
                    ItemStack stack = slot.getItem();
                    if (!stack.isEmpty()) {
                        String blockName = stack.getItem().builtInRegistryHolder().key().location().toString();
                        content.append(String.format("%s%n", blockName));
                    }
                });

                if (content.length() > 0) {
                    File file = new File(FMLPaths.CONFIGDIR.get().toFile(), "inventory_item_names.txt");
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write(content.toString());
                        System.out.println("Inventory item names exported to: " + file.getAbsolutePath());
                    } catch (IOException ex) {
                        System.err.println("Unable to write to the file. Error details:");
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("Inventory contains no items. No files were written.");
                }
            }
        }
    }
}
