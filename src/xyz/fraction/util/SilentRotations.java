package xyz.fraction.util;

import net.minecraft.client.Minecraft;
import xyz.fraction.event.impl.PreMotionEvent;

public class SilentRotations {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static float yaw, pitch;
    private static long last;

    public static void set(float yaw, float pitch) {
        SilentRotations.yaw = yaw;
        SilentRotations.pitch = pitch;
        last = System.currentTimeMillis();
    }

    public static void add(float yaw, float pitch) {
        if (isSilentRots()) {
            SilentRotations.yaw += yaw;
            SilentRotations.pitch += pitch;
        } else {
            SilentRotations.yaw = mc.thePlayer.rotationYaw + yaw;
            SilentRotations.pitch = mc.thePlayer.rotationPitch + pitch;
        }
        last = System.currentTimeMillis();
    }

    public static float getYaw() {
        return isSilentRots() ? yaw : mc.thePlayer.rotationYaw;
    }

    public static float getPitch() {
        return isSilentRots() ? pitch : mc.thePlayer.rotationPitch;
    }

    public static boolean isSilentRots() {
        return System.currentTimeMillis() - last < 250L;
    }

    public static void clear() {
        yaw = pitch = 0;
    }

    public static void onPre(PreMotionEvent e) {
        if (!isSilentRots())
            return;

        e.setYaw(yaw);
        e.setPitch(pitch);
    }
}
