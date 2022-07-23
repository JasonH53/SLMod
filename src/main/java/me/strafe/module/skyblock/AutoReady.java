package me.strafe.module.skyblock;

import me.strafe.module.Category;
import me.strafe.module.Module;
import me.strafe.module.render.EntityReach;
import me.strafe.utils.Location;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class AutoReady extends Module {

    boolean startKuudra = false;
    boolean checkingEntities = false;
    static int windowId;
    private static boolean readied = false;

    public AutoReady() {
        super("Auto Ready", "Ready automatically in Kuudra", Category.SKYBLOCK);
    }

    @SubscribeEvent
    public void renderWorld(RenderWorldLastEvent event) {
        if (!startKuudra || checkingEntities) return;
        new Thread(() -> {
            try {
                checkingEntities = true;
                Thread.sleep(500);
                Entity elle = null;
                for (Entity entity1 : (mc.theWorld.loadedEntityList)) {
                    if (entity1.getName().contains("Elle")) {
                        elle = entity1;
                        interactWithEntity(elle);
                        startKuudra = false;
                        break;
                    }
                }
                checkingEntities = false;
            } catch (Exception e) {}
        }).start();
    }

    @SubscribeEvent
    public void chat(ClientChatReceivedEvent event) {
        String message = StringUtils.stripControlCodes(event.message.getUnformattedText()).toLowerCase();
        if (message.contains("[NPC] Elle: Talk to me to begin!")) {
            startKuudra = true;
        }
    }

    @SubscribeEvent
    public void guiDraw(GuiScreenEvent.BackgroundDrawnEvent event) {
        if (readied) return;
        if (event.gui instanceof GuiChest) {
            Container container = ((GuiChest) event.gui).inventorySlots;
            if (container instanceof ContainerChest) {
                String chestName = ((ContainerChest) container).getLowerChestInventory().getDisplayName().getUnformattedText();
                List<Slot> invSlots = container.inventorySlots;
                if (chestName.contains("Ready Up")) {
                    int i;
                    for (i = 0; i < invSlots.size(); i++) {
                        if (!invSlots.get(i).getHasStack()) continue;
                        String slotName = StringUtils.stripControlCodes(invSlots.get(i).getStack().getDisplayName());
                        if (slotName.equals("Not Ready")) {
                            clickSlot(invSlots.get(i));
                            readied = true;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        readied = false;
    }

    private void clickSlot(Slot slot) {
        windowId = mc.thePlayer.openContainer.windowId;
        mc.playerController.windowClick(windowId, slot.slotNumber, 1, 0, mc.thePlayer);
    }

    private static void interactWithEntity(Entity entity) {
        PlayerControllerMP playerControllerMP = mc.playerController;
        playerControllerMP.interactWithEntitySendPacket(mc.thePlayer, entity);
    }

    private static ArrayList<Entity> getAllEntitiesInRange() {
        if (mc.theWorld!=null) {
            ArrayList<Entity> entities = new ArrayList<>();
            for (Entity entity1 : (mc.theWorld.loadedEntityList)) {
                if (!(entity1 instanceof EntityItem) && !(entity1 instanceof EntityXPOrb) && !(entity1 instanceof EntityWither) && !(entity1 instanceof EntityPlayerSP)) {
                    entities.add(entity1);
                }
            }
            return entities;
        }
        return null;
    }

}
