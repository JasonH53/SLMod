//package me.strafe.mixins;
//
//import me.strafe.events.MotionUpdateEvent;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.entity.EntityPlayerSP;
//import net.minecraft.network.play.client.C03PacketPlayer;
//import net.minecraft.network.play.client.C0BPacketEntityAction;
//import net.minecraftforge.common.MinecraftForge;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Overwrite;
//
//@Mixin(value={EntityPlayerSP.class}, priority=1)
//public class PlayerSPMixin {
////    @Overwrite
////    public void onUpdateWalkingPlayer() {
////        boolean flag1;
////        MotionUpdateEvent.Pre event = new MotionUpdateEvent.Pre(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minY, Minecraft.getMinecraft().thePlayer.posZ, Minecraft.getMinecraft().thePlayer.rotationYaw, Minecraft.getMinecraft().thePlayer.rotationPitch, Minecraft.getMinecraft().thePlayer.onGround, Minecraft.getMinecraft().thePlayer.isSprinting(), Minecraft.getMinecraft().thePlayer.isSneaking());
////        if (MinecraftForge.EVENT_BUS.post(event)) {
////            return;
////        }
////        boolean flag = event.sprinting;
////        if (flag != this.serverSprintState) {
////            if (flag) {
////                this.sendQueue.sendPacket(new C0BPacketEntityAction((EntityPlayerSP)((Object)this), C0BPacketEntityAction.Action.START_SPRINTING));
////            } else {
////                this.field_71174_a.sendPacket(new C0BPacketEntityAction((EntityPlayerSP)((Object)this), C0BPacketEntityAction.Action.STOP_SPRINTING));
////            }
////            this.field_175171_bO = flag;
////        }
////        if ((flag1 = event.sneaking) != this.field_175170_bN) {
////            if (flag1) {
////                this.field_71174_a.sendPacket(new C0BPacketEntityAction((EntityPlayerSP)((Object)this), C0BPacketEntityAction.Action.START_SNEAKING));
////            } else {
////                this.field_71174_a.sendPacket(new C0BPacketEntityAction((EntityPlayerSP)((Object)this), C0BPacketEntityAction.Action.STOP_SNEAKING));
////            }
////            this.field_175170_bN = flag1;
////        }
////        if (this.func_175160_A()) {
////            boolean flag3;
////            double d0 = event.x - this.field_175172_bI;
////            double d1 = event.y - this.field_175166_bJ;
////            double d2 = event.z - this.field_175167_bK;
////            double d3 = event.yaw - this.field_175164_bL;
////            double d4 = event.pitch - this.field_175165_bM;
////            boolean flag2 = d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4 || this.field_175168_bP >= 20;
////            boolean bl = flag3 = d3 != 0.0 || d4 != 0.0;
////            if (this.field_70154_o == null) {
////                if (flag2 && flag3) {
////                    this.field_71174_a.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(event.x, event.y, event.z, event.yaw, event.pitch, event.onGround));
////                } else if (flag2) {
////                    this.field_71174_a.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(event.x, event.y, event.z, event.onGround));
////                } else if (flag3) {
////                    this.field_71174_a.sendPacket(new C03PacketPlayer.C05PacketPlayerLook(event.yaw, event.pitch, event.onGround));
////                } else {
////                    this.field_71174_a.sendPacket(new C03PacketPlayer(event.onGround));
////                }
////            } else {
////                this.field_71174_a.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(this.field_70159_w, -999.0, this.field_70179_y, event.yaw, event.pitch, event.onGround));
////                flag2 = false;
////            }
////            ++this.field_175168_bP;
////            if (flag2) {
////                this.field_175172_bI = event.x;
////                this.field_175166_bJ = event.y;
////                this.field_175167_bK = event.z;
////                this.field_175168_bP = 0;
////            }
////            RotationUtils.lastReportedPitch = this.field_175165_bM;
////            if (flag3) {
////                this.field_175164_bL = event.yaw;
////                this.field_175165_bM = event.pitch;
////            }
////        }
////        MinecraftForge.EVENT_BUS.post(new MotionUpdateEvent.Post(event));
////    }
//}
