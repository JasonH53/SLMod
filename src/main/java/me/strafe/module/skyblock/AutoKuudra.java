package me.strafe.module.skyblock;

import me.strafe.StrafeLegitMod;
import me.strafe.module.Category;
import me.strafe.module.Module;
import me.strafe.settings.Setting;
import me.strafe.utils.Location;
import me.strafe.utils.Rotation;
import me.strafe.utils.RotationUtils;
import me.strafe.utils.Stolenutils;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

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
        if (!invopen && Location.isInKuudra()) {
            for (Entity entity : mc.theWorld.loadedEntityList) {
                if (entity instanceof EntityWither) {
                    EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
//                    if (entity instanceof EntityPlayerSP) continue;
                    if (mc.thePlayer.getDistanceToEntity(entityLivingBase) <= 50) {
                        if (entityLivingBase.isEntityAlive()) {
                            dead=false;
                            entity2 = entityLivingBase;
                            RotationUtils.setup(RotationUtils.getRotation(entityLivingBase.getPositionVector().addVector(0.0, entityLivingBase.getEyeHeight() - 8, 0.0)), (long) StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Aim Speed").getValDouble());
                            if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Right Click Mode").getValBoolean()) {
                                KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                            }
                        } else if (entityLivingBase.isDead) {
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
            Stolenutils.HUD.drawBoxAroundEntity(entity2, 1, 8, 0, 0, false);
        }
    }

    @SubscribeEvent
    public void onWorldChange (WorldEvent.Load event){
        entity2 = null;
        dead=false;
        invopen=false;
    }



}
