package xyz.fraction.util;

import net.minecraft.client.Minecraft;

public class MoveUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean isMoving() {
        return mc.thePlayer.moveStrafing != 0 || mc.thePlayer.moveForward != 0;
    }

    public static float getInputYaw() {
        float yaw = mc.thePlayer.rotationYaw + 90;

        float strafe = mc.thePlayer.moveStrafing;
        float forward = mc.thePlayer.moveForward;

        if (forward > 0) {
            if (strafe > 0) {
                yaw -= 45;
            } else if (strafe < 0) {
                yaw += 45;
            }
        } else if (forward < 0) {
            yaw += 180;

            if (strafe > 0) {
                yaw += 45;
            } else if (strafe < 0) {
                yaw -= 45;
            }
        } else {
            if (strafe > 0) {
                yaw -= 90;
            } else if (strafe < 0) {
                yaw += 90;
            }
        }

        return yaw;
    }

    public static void boost(double motion) {
        if (!MoveUtil.isMoving())
            return;

        mc.thePlayer.motionX += Math.cos(Math.toRadians(getInputYaw())) * motion;
        mc.thePlayer.motionZ += Math.sin(Math.toRadians(getInputYaw())) * motion;
    }

    public static void strafe(double motion) {
        if (!MoveUtil.isMoving())
            return;

        mc.thePlayer.motionX = Math.cos(Math.toRadians(getInputYaw())) * motion;
        mc.thePlayer.motionZ = Math.sin(Math.toRadians(getInputYaw())) * motion;
    }

    public static void strafe() {
        if (!MoveUtil.isMoving())
            return;

        double motion = Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ);

        mc.thePlayer.motionX = Math.cos(Math.toRadians(getInputYaw())) * motion;
        mc.thePlayer.motionZ = Math.sin(Math.toRadians(getInputYaw())) * motion;
    }
}
