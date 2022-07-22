package me.strafe.module.skyblock;

import me.strafe.events.SecondEvent;
import me.strafe.module.Category;
import me.strafe.module.Module;
import me.strafe.module.render.EntityReach;
import me.strafe.utils.Location;
import me.strafe.utils.handlers.ScoreboardHandler;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoBuy extends Module {
    private static boolean Multi40;
    private static boolean Bonus40;
    private static boolean Multi80;
    private static boolean Bonus80;

    public AutoBuy() {
        super("Auto Buy", "Auto Buy from shop", Category.SKYBLOCK);
    }

    public void onEnable() {
        super.onEnable();
    }

    @SubscribeEvent
    public void onSecond(SecondEvent event) {
        if (!Location.isInKuudra()) return;
        if (ScoreboardHandler.getToken() >= 50) {
            if (Multi40 || Bonus40) {
                if (mc.currentScreen == null) {
                    EntityReach.openShop();
                } else {
                    if (Multi40) {
                        mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, 6, 0, 0, mc.thePlayer);
                        Multi40 = false;
                    } else {
                        mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, 3, 0, 0, mc.thePlayer);
                        Bonus40 = false;
                    }
                    mc.thePlayer.closeScreen();
                }
            }
        }
        if (!Multi40 && !Bonus40) {
            if (ScoreboardHandler.getToken() >= 80) {
                if (Multi80 || Bonus80) {
                    if (mc.currentScreen == null) {
                        EntityReach.openShop();
                    } else {
                        if (Multi80) {
                            mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, 6, 0, 0, mc.thePlayer);
                            Multi80 = false;
                        } else {
                            mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, 3, 0, 0, mc.thePlayer);
                            Bonus80 = false;
                        }
                        mc.thePlayer.closeScreen();
                    }
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
