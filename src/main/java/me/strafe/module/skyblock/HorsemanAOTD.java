package me.strafe.module.skyblock;

import me.strafe.SLM;
import me.strafe.events.TickEndEvent;
import me.strafe.module.Category;
import me.strafe.module.Module;
import me.strafe.utils.RotationUtils;
import me.strafe.utils.Stolenutils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Method;

public class HorsemanAOTD extends Module {

    private static Entity e = null;

    public HorsemanAOTD() {
        super("AOTD Horseman thing", "thing", Category.SKYBLOCK);
    }

    public void onEnable() {
        super.onEnable();
        RotationUtils.reset();
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null) return;
        for (Entity entity : (mc.theWorld.loadedEntityList)) {
            if (entity instanceof EntityHorse) {
                if (((EntityHorse) entity).getHorseType() == 4) {
                    if (entity.getDistanceToEntity(mc.thePlayer) <= 10) {
                        e = entity;
                        RotationUtils.setup(RotationUtils.getRotation(entity.getPositionVector().addVector(0.0, entity.getEyeHeight() - 0.5, 0.0)), (long) 500);
                        if (mc.thePlayer.ticksExisted % 3 == 0) rightClick();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderTick (TickEvent.RenderTickEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null) return;
        if (!RotationUtils.done && mc.currentScreen == null) {
            RotationUtils.update();
        }
    }

    @SubscribeEvent
    public void r1(RenderWorldLastEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null) return;
        if (e != null) {
            Stolenutils.HUD.drawBoxAroundEntity(e, 1, 0, 0, 0, false);
        }
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
        } catch (Exception ignored) {
        }
    }
 }
