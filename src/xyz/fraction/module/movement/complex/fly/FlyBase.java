package xyz.fraction.module.movement.complex.fly;

import net.minecraft.client.Minecraft;
import xyz.fraction.Fraction;
import xyz.fraction.event.impl.PreMotionEvent;

public abstract class FlyBase {
    protected static final Minecraft mc = Minecraft.getMinecraft();

    public void onPre(PreMotionEvent e) {}
    public void onPost() {}

    public void bobbing() {
        mc.thePlayer.cameraYaw += 0.25;
    }

    public Fly getFly() {
        return (Fly) Fraction.INSTANCE.getModuleManager().getModule(Fly.class);
    }
}
