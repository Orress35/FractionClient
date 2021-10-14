package xyz.fraction.util;

import net.minecraft.client.Minecraft;

public class Rotations {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static float gcd() {
        float f = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
        return f * f * f * 1.2F;
    }

    public static float gcd(int sensitivity) {
        float f = sensitivity / 200F * 0.6F + 0.2F;
        return f * f * f * 1.2F;
    }

    public static float getYaw(float player, float perfect) {
        float playerYaw = player % 360;
        if (playerYaw < 0)
            playerYaw = 360 - Math.abs(playerYaw);

        if (perfect < 0)
            perfect = 360 - Math.abs(perfect);

        float rotations = perfect - playerYaw;
        if (rotations >= 180)
            rotations = -(360 - perfect + playerYaw);

        if (rotations <= -180)
            rotations = 360 - playerYaw + perfect;

        return rotations;
    }

    public static float getPitch(float player, float perfect) {
        return perfect - player;
    }
}
