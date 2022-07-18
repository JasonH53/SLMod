package me.strafe.module.skyblock;

import me.strafe.StrafeLegitMod;
import me.strafe.events.MotionUpdateEvent;
import me.strafe.module.Category;
import me.strafe.module.Module;
import me.strafe.module.render.PlayerDisplayer;
import me.strafe.utils.RandomUtil;
import me.strafe.utils.Rotation;
import me.strafe.utils.RotationUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;




public class AutoKuudra extends Module {
    public static Rotation startRot = null;

    public AutoKuudra() {
        super("Auto Kuudra", "Kuudra Things", Category.SKYBLOCK);
    }

    public void onEnable() {
        super.onEnable();
        RotationUtils.reset();
        RotationUtils.done = true;
        startRot = new Rotation(mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
    }

    public void onDisable() {
        super.onDisable();
        RotationUtils.reset();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        for (Iterator<Entity> entities = mc.theWorld.loadedEntityList.iterator(); entities.hasNext(); ) {
            Object theObject = entities.next();
            if (theObject instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) theObject;
                if (entity instanceof EntityPlayerSP) continue;
                if (mc.thePlayer.getDistanceToEntity(entity) <= 60 && entity.getName().equalsIgnoreCase("dinnerbone")) {
                    if (entity.isEntityAlive()) {
                        RotationUtils.setup(RotationUtils.getRotation(entity.getPositionVector().addVector(0.0, entity.getEyeHeight(), 0.0)), Long.valueOf(100));
                        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
                        if (entity.isDead) {
                        }
                        continue;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent e) {
        if (!RotationUtils.done) {
            RotationUtils.update();
        }
    }

}
