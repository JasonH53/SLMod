//package me.strafe.module.skyblock;
//
//import me.strafe.events.MotionUpdateEvent;
//import me.strafe.module.Category;
//import me.strafe.module.Module;
//import me.strafe.utils.RotationUtils;
//import net.minecraft.client.entity.EntityPlayerSP;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.network.play.client.C02PacketUseEntity;
//import net.minecraft.network.play.client.C0APacketAnimation;
//import net.minecraft.util.Vec3;
//import net.minecraftforge.event.world.WorldEvent;
//import net.minecraftforge.fml.common.eventhandler.EventPriority;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//
//import java.util.Iterator;
//
//import static me.strafe.utils.Utils.mc;
//
//
//public class AutoKuudra extends Module {
//    public static boolean attack;
//
//    public AutoKuudra() {
//        super("Auto Kuudra", "Kuudra Things", Category.SKYBLOCK);
//    }
//
//    public void onEnable() {
//        super.onEnable();
//    }
//
//    @SubscribeEvent(priority = EventPriority.HIGH)
//    public void onMove(MotionUpdateEvent.Pre event) {
////            block0:
////            for (Entity entity : mc.theWorld.playerEntities) {
////                if (!(entity.getDistanceToEntity(mc.thePlayer) < 5.0f) || !(entity instanceof EntityPlayer) || entity.isDead)
////                    continue;
//////                attack = true;
////                float[] angles = RotationUtils.getAngles(new Vec3(entity.posX, entity.posY, entity.posZ));
////                event.yaw = angles[0];
////                event.pitch = angles[1];
////                mc.playerController.attackEntity(mc.thePlayer, entity);
////                mc.thePlayer.swingItem();
////                continue block0;
////            }
//        for (Iterator<Entity> entities = mc.theWorld.loadedEntityList.iterator(); entities.hasNext(); ) {
//            Object theObject = entities.next();
//
//            if (theObject instanceof EntityLivingBase) {
//                EntityLivingBase entity = (EntityLivingBase) theObject;
//
//                if (entity instanceof EntityPlayerSP) continue;
//
//                if (mc.thePlayer.getDistanceToEntity(entity) <= 6.2173613F) {
//                    if (entity.isEntityAlive()) {
//                        float[] angles = RotationUtils.getAngles(new Vec3(entity.posX, entity.posY, entity.posZ));
//                        event.yaw = angles[0];
//                        event.pitch = angles[1];
//                        mc.playerController.attackEntity(mc.thePlayer, entity);
//                        mc.thePlayer.swingItem();
//                        continue;
//                    }
//                }
//            }
//        }
//    }
//
//    @SubscribeEvent
//    public void onMovePost(MotionUpdateEvent.Post event) {
////        if (!attack) {
////            return;
////        }
////        mc.thePlayer.swingItem();
////        mc.getNetHandler().addToSendQueue(new C02PacketUseEntity());
////        attack = false;
//    }
//
//
//}
