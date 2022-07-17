package me.strafe.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.*;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.List;

import static me.strafe.utils.Utils.mc;

public class RotationUtils {

    public static float lastReportedPitch;

    private RotationUtils() {
    }

    public static float[] getAngles(Vec3 vec) {
        double diffX = vec.xCoord - mc.thePlayer.posX;
        double diffY = vec.yCoord - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
        double diffZ = vec.zCoord - mc.thePlayer.posZ;
        double dist = MathHelper.sqrt_double((double)(diffX * diffX + diffZ * diffZ));
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
        return new float[]{mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float((float)(yaw - mc.thePlayer.rotationYaw)), mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float((float)(pitch - mc.thePlayer.rotationPitch))};
    }

//    public static float[] getServerAngles(Vec3 vec) {
//        double diffX = vec.xCoord - mc.thePlayer.posX;
//        double diffY = vec.yCoord - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
//        double diffZ = vec.zCoord - mc.thePlayer.posZ;
//        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI) - 90.0f;
//        double dist = MathHelper.sqrt_double((double)(diffX * diffX + diffZ * diffZ));
//        float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / Math.PI));
//        return new float[]{((PlayerSPAccessor)((Object)mc.thePlayer)).getLastReportedYaw() + MathHelper.wrapAngleTo180_float((float)(yaw - ((PlayerSPAccessor)((Object)mc.thePlayer)).getLastReportedYaw())), ((PlayerSPAccessor)((Object)mc.thePlayer)).getLastReportedPitch() + MathHelper.wrapAngleTo180_float((float)(pitch - ((PlayerSPAccessor)((Object).mc.thePlayer)).getLastReportedPitch()))};
//    }

    public static float[] getBowAngles(Entity entity) {
        double xDelta = (entity.posX - entity.lastTickPosX) * 0.4;
        double zDelta = (entity.posZ - entity.lastTickPosZ) * 0.4;
        double d = mc.thePlayer.getDistanceToEntity(entity);
        d -= d % 0.8;
        double xMulti = d / 0.8 * xDelta;
        double zMulti = d / 0.8 * zDelta;
        double x = entity.posX + xMulti - mc.thePlayer.posX;
        double z = entity.posZ + zMulti - mc.thePlayer.posZ;
        double y = mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight() - (entity.posY + (double)entity.getEyeHeight());
        double dist = mc.thePlayer.getDistanceToEntity(entity);
        float yaw = (float)Math.toDegrees(Math.atan2(z, x)) - 90.0f;
        double d1 = MathHelper.sqrt_double((double)(x * x + z * z));
        float pitch = (float)(-(Math.atan2(y, d1) * 180.0 / Math.PI)) + (float)dist * 0.11f;
        return new float[]{yaw, -pitch};
    }

    public static boolean isWithinFOV(EntityLivingBase entity, double fov) {
        float yawDifference = Math.abs(RotationUtils.getAngles(entity)[0] - mc.thePlayer.rotationYaw);
        return (double)yawDifference < fov && (double)yawDifference > -fov;
    }

    public static float getYawDifference(EntityLivingBase entity1, EntityLivingBase entity2) {
        return Math.abs(RotationUtils.getAngles(entity1)[0] - RotationUtils.getAngles(entity2)[0]);
    }

    public static float getYawDifference(EntityLivingBase entity1) {
        return Math.abs(mc.thePlayer.rotationYaw - RotationUtils.getAngles(entity1)[0]);
    }

    public static boolean isWithinPitch(EntityLivingBase entity, double pitch) {
        float pitchDifference = Math.abs(RotationUtils.getAngles(entity)[1] - mc.thePlayer.rotationPitch);
        return (double)pitchDifference < pitch && (double)pitchDifference > -pitch;
    }

    public static float[] getAngles(Entity en) {
        return RotationUtils.getAngles(new Vec3(en.posX, en.posY + ((double)en.getEyeHeight() - (double)en.height / 1.5) + 0.5, en.posZ));
    }

//    public static float[] getServerAngles(Entity en) {
//        return RotationUtils.getServerAngles(new Vec3(en.posX, en.posY + ((double)en.getEyeHeight() - (double)en.height / 1.5) + 0.5, en.posZ));
//    }
}

