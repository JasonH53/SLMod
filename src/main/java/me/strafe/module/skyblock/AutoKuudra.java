package me.strafe.module.skyblock;

import me.strafe.StrafeLegitMod;
import me.strafe.events.TickEndEvent;
import me.strafe.module.Category;
import me.strafe.module.Module;
import me.strafe.settings.Setting;
import me.strafe.utils.*;
import me.strafe.utils.pathfinding.Pathfinder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Method;
import java.util.List;

public class AutoKuudra extends Module {
    public static Rotation startRot = null;
    public static Entity entity2 = null;
    public static boolean dead;
    public static boolean mountedCannon = false;
    public static boolean initWalk = false;

    private static BlockPos[] cannonPositions = {
            new BlockPos(-89, 41, -120),
            new BlockPos(-83, 41, -107),
            new BlockPos(-91, 41, -90),
            new BlockPos(-101, 41, -87),
            new BlockPos(-111, 41, -91),
            new BlockPos(-121, 40, -108),
            new BlockPos(-113, 41, -119),
    };

    public AutoKuudra() {
        super("Auto Kuudra", "Kuudra Things", Category.SKYBLOCK);
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Aim Speed", this, 100, 50, 500, false));
//        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Aim Height", this, 8, 0, 15, false));
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Highlight Wither", this, false));
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Right Click Mode", this, false));
        StrafeLegitMod.instance.settingsManager.rSetting(new Setting("Cannon Number", this, 1, 1, 7, false));
    }

    public void onEnable() {
        super.onEnable();
        RotationUtils.reset();
        RotationUtils.done = true;
        if (mc.thePlayer != null) {
            startRot = new Rotation(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
        }
        mountedCannon=false;
        initWalk=false;
    }

    public void onDisable() {
        super.onDisable();
        RotationUtils.reset();
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
    }

    public static void rightClick() {
        try {
            Method rightClickMouse;
            try {
                rightClickMouse = Minecraft.class.getDeclaredMethod("func_147121_ag");
            } catch (NoSuchMethodException e) {
                rightClickMouse = Minecraft.class.getDeclaredMethod("rightClickMouse");
            }
            rightClickMouse.setAccessible(true);
            rightClickMouse.invoke(mc);
        } catch (Exception ignored) {}
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null) return;
        if (mc.currentScreen == null && Location.isInKuudra()) {
            if (mountedCannon) {
                for (Entity entity : mc.theWorld.loadedEntityList) {
                    if (entity instanceof EntityWither) {
                        EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                        if (mc.thePlayer.getDistanceToEntity(entityLivingBase) <= 50) {
                            if (entityLivingBase.isEntityAlive()) {
                                dead = false;
                                entity2 = entityLivingBase;
                                RotationUtils.setup(RotationUtils.getRotation(entityLivingBase.getPositionVector().addVector(0.0, entityLivingBase.getEyeHeight() - 8, 0.0)), (long) StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Aim Speed").getValDouble());
                                if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Right Click Mode").getValBoolean()) {
                                    KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                                }
                            } else if (entityLivingBase.isDead) {
                                dead = true;
                            }
                        }
                    }
                }
            } else {
                if(!Pathfinder.hasPath()) {
                    if (!initWalk) {
                        BlockPos blockPos = cannonPositions[(int) StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Cannon Number").getValDouble() - 1];
                        ChatUtils.addVanillaMessage("started walking to "+blockPos);
                        new Thread(() -> {
                            Pathfinding.initWalk();
                            Pathfinder.setup(new BlockPos(VecUtils.floorVec(mc.thePlayer.getPositionVector())), blockPos, 0.0);
                        }).start();
                        initWalk = true;
                    } else {
                        ChatUtils.addChatMessage("finished walking now mount");
                        rightClick();
                        mountedCannon = true;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent e) {
        if (mc.thePlayer == null || mc.theWorld == null || mc.gameSettings.showDebugInfo) return;
        if (!RotationUtils.done && mc.currentScreen == null) {
            RotationUtils.update();
        }
    }

    @SubscribeEvent
    public void r1(RenderWorldLastEvent e) {
        if (mc.thePlayer == null || mc.theWorld == null || mc.gameSettings.showDebugInfo) return;
        if (StrafeLegitMod.instance.settingsManager.getSettingByName(this, "Highlight Wither").getValBoolean() && !dead) {
            Stolenutils.HUD.drawBoxAroundEntity(entity2, 1, 8, 0, 0, false);
        }
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        mountedCannon=false;
        initWalk=false;
    }

}
