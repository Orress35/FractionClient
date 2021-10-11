package xyz.fraction.util;

import net.minecraft.client.Minecraft;

public class MoveUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean isMoving() {
        return mc.thePlayer.moveStrafing != 0 || mc.thePlayer.moveForward != 0;
    }

    public static void strafe(double motion) {
        mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;

        float strafe = mc.thePlayer.moveStrafing;
        float forward = mc.thePlayer.moveForward;

        mc.thePlayer.moveFlying(strafe, forward, (float) motion);
    }

    public static void strafe() {
        mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;

        float strafe = mc.thePlayer.moveStrafing;
        float forward = mc.thePlayer.moveForward;

        mc.thePlayer.moveFlying(strafe, forward, (float) Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ));
    }
}
