package me.strafe.module.skyblock;

import me.strafe.events.MotionUpdateEvent;
import me.strafe.module.Category;
import me.strafe.module.Module;
import me.strafe.utils.RotationUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoKuudra extends Module {
    public static boolean attack;
    public EntityLivingBase target;

    public AutoKuudra() {
        super("Auto Kuudra", "Kuudra Things", Category.SKYBLOCK);
    }

    public void onEnable() {
        super.onEnable();
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onMove(MotionUpdateEvent.Pre event) {
        System.out.println("e");
        block0: for (Entity entity : mc.theWorld.playerEntities) {
            if (!(entity.getDistanceToEntity(mc.thePlayer) < 20.0f) || !(entity instanceof EntityPlayer) || entity.isDead)
                continue;
            attack = true;
            float[] angles = RotationUtils.getAngles(new Vec3(entity.posX, entity.posY, entity.posZ));
            event.yaw = angles[0];
            event.pitch = angles[1];
            continue block0;
        }
    }

    @SubscribeEvent
    public void onMovePost(MotionUpdateEvent.Post event) {
        if (!attack) {
            return;
        }
        mc.getNetHandler().getNetworkManager().sendPacket(new C0APacketAnimation());
        attack = false;
    }

}
