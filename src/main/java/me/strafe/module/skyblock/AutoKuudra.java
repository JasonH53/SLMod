package me.strafe.module.skyblock;

import me.strafe.StrafeLegitMod;
import me.strafe.events.MotionUpdateEvent;
import me.strafe.module.Category;
import me.strafe.module.Module;
import me.strafe.module.ModuleManager;
import me.strafe.module.render.PlayerDisplayer;
import me.strafe.settings.Setting;
import me.strafe.utils.RandomUtil;
import me.strafe.utils.Rotation;
import me.strafe.utils.RotationUtils;
import me.strafe.utils.Stolenutils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class AutoKuudra extends Module {
    public static Rotation startRot = null;
    public static Entity entity2 = null;
    public static boolean invopen;
    public static boolean dead;

    public AutoKuudra() {
        super("Auto Kuudra", "Kuudra Things", Category.SKYBLOCK);
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Aim Speed", this, 100, 50, 500, false));
//        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Aim Height", this, 8, 0, 15, false));
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Highlight Wither", this, false));
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Right Click Mode", this, false));
    }

    public void onEnable() {
        super.onEnable();
        RotationUtils.reset();
        RotationUtils.done = true;
        invopen = false;
        if (mc.thePlayer!=null) {
            startRot = new Rotation(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
        }
    }

    public void onDisable() {
        super.onDisable();
        RotationUtils.reset();
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END || mc.thePlayer == null || mc.theWorld == null) return;
        if (!invopen) {
            for (Iterator<Entity> entities = mc.theWorld.loadedEntityList.iterator(); entities.hasNext(); ) {
                Object theObject = entities.next();
                if (theObject instanceof EntityWither) {
                    EntityLivingBase entity = (EntityLivingBase) theObject;
                    if (entity instanceof EntityPlayerSP) continue;
                    if (mc.thePlayer.getDistanceToEntity(entity) <= 50) {
                        if (entity.isEntityAlive()) {
                            dead=false;
                            entity2 = entity;
                            RotationUtils.setup(RotationUtils.getRotation(entity.getPositionVector().addVector(0.0, entity.getEyeHeight() - 8, 0.0)), Long.valueOf((int) StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Aim Speed").getValDouble()));
                            if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Right Click Mode").getValBoolean()) {
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                            }
                            continue;
                        } else if (entity.isDead) {
                            dead=true;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent e) {
        if (mc.thePlayer == null || mc.theWorld == null || mc.gameSettings.showDebugInfo) return;
        if (!RotationUtils.done && invopen == false) {
            RotationUtils.update();
        }
    }

    @SubscribeEvent
    public void onGUI(GuiOpenEvent event) {
        invopen = true;
        if (mc.currentScreen==null) {
            invopen = false;
        }
    }

    @SubscribeEvent
    public void r1(RenderWorldLastEvent e) {
        if (mc.thePlayer == null || mc.theWorld == null || mc.gameSettings.showDebugInfo) return;
        if(StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Highlight Wither").getValBoolean() && !dead) {
            Stolenutils.HUD.drawBoxAroundEntity(entity2, 1, 7, 0, 0, false);
        }
    }

    @SubscribeEvent
    public void onWorldChange (WorldEvent.Load event){
        entity2 = null;
        dead=false;
        invopen=false;
    }

}
