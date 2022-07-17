//package me.strafe.mixins;
//
//import me.strafe.events.MotionUpdateEvent;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.entity.EntityPlayerSP;
//import net.minecraftforge.common.MinecraftForge;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Overwrite;
//
//@Mixin(value={EntityPlayerSP.class}, priority=1)
//public class PlayerSPMixin {
//    @Overwrite
//    public void onUpdateWalkingPlayer() {
//        System.out.println("MIXIN CHECK!");
//        MotionUpdateEvent.Pre event = new MotionUpdateEvent.Pre(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minY, Minecraft.getMinecraft().thePlayer.posZ, Minecraft.getMinecraft().thePlayer.rotationYaw, Minecraft.getMinecraft().thePlayer.rotationPitch, Minecraft.getMinecraft().thePlayer.onGround, Minecraft.getMinecraft().thePlayer.isSprinting(), Minecraft.getMinecraft().thePlayer.isSneaking());
//        if (MinecraftForge.EVENT_BUS.post(event)) {
//            return;
//        }
//        MinecraftForge.EVENT_BUS.post(new MotionUpdateEvent.Post(event));
//    }
//}
