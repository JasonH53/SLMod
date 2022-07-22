package me.strafe.module.skyblock;

import me.strafe.StrafeLegitMod;
import me.strafe.module.Category;
import me.strafe.module.Module;
import me.strafe.module.render.EntityReach;
import me.strafe.utils.ChatUtils;
import me.strafe.utils.Location;
import me.strafe.utils.TimeHelper;
import me.strafe.utils.handlers.ScoreboardHandler;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class AutoBuy extends Module {
    private static boolean Multi40;
    private static boolean Bonus40;
    private static boolean Multi80;
    private static boolean Bonus80;
    private static int debounce = 60;

    public AutoBuy() {
        super("Auto Buy", "Auto Buy from shop", Category.SKYBLOCK);
    }

    public void onEnable() {
        super.onEnable();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (!Location.isInKuudra() || mc.currentScreen != null) return;
        if (ScoreboardHandler.getToken() >= 50) {
            if (Multi40) {
                EntityReach.openShop();
            } else if (Bonus40) {
                EntityReach.openShop();
            }
        }
        if (ScoreboardHandler.getToken() >= 80) {
            if (Multi80) {
                EntityReach.openShop();
            } else if (Bonus80) {
                EntityReach.openShop();
            }
        }
    }

    @SubscribeEvent
    public void onDrawGuiBackground(GuiScreenEvent.BackgroundDrawnEvent event) {
        if (!Location.isInKuudra()) return;
        if (ScoreboardHandler.getToken() >= 50) {
            if (Multi40) {
                debounce--;
                if (debounce == 0) {
                    mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, 6, 0, 0, mc.thePlayer);
                    Multi40 = false;
                    debounce = 60;
                    mc.thePlayer.closeScreen();
                }
            } else if (Bonus40) {
                debounce--;
                if (debounce == 0) {
                    mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, 3, 0, 0, mc.thePlayer);
                    Bonus40 = false;
                    debounce = 60;
                    mc.thePlayer.closeScreen();
                }
            }
        }
        if (ScoreboardHandler.getToken() >= 80) {
            if (Multi80) {
                debounce--;
                if (debounce == 0) {
                    mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, 6, 0, 0, mc.thePlayer);
                    Multi80 = false;
                    debounce = 60;
                    mc.thePlayer.closeScreen();
                }
            } else if (Bonus80) {
                debounce--;
                if (debounce == 0) {
                    mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, 3, 0, 0, mc.thePlayer);
                    Bonus80 = false;
                    debounce = 60;
                    mc.thePlayer.closeScreen();
                }
            }
        }
    }


    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        Bonus80 = true;
        Multi80 = true;
        Bonus40 = true;
        Multi40 = true;
    }

}
