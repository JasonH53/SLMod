package me.strafe.module.skyblock;

import me.strafe.module.Category;
import me.strafe.module.Module;
import me.strafe.utils.TimeHelper;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class ChestStealer extends Module {

    private TimeHelper time = new TimeHelper();

    public ChestStealer() {
        super("Chest Stealer", "thing", Category.SKYBLOCK);
    }

    public void onEnable() {
        super.onEnable();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.currentScreen instanceof GuiChest) {
            if (!isContainerEmpty(mc.thePlayer.openContainer)) {
                for (Slot slot : (List<Slot>) mc.thePlayer.openContainer.inventorySlots) {
                    if (slot != null) {
                        if (slot.getStack() != null) {
                            if (time.hasReached(65L)) {
                                mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, slot.slotNumber, 0, 1, mc.thePlayer);
                                time.reset();
                            }
                        }
                    }
                }
            } else {
                mc.displayGuiScreen(null);
            }
        }
    }
    public boolean isContainerEmpty (Container container){
        boolean temp = true;
        for (int i = 0, slotAmount = (container.inventorySlots.size() == 90) ? 54 : 27; i < slotAmount; ++i) {
            if (container.getSlot(i).getHasStack()) {
                temp = false;
            }
        }
        return temp;
    }
}
