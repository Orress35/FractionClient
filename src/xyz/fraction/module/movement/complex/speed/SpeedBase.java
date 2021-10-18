package xyz.fraction.module.movement.complex.speed;

import net.minecraft.client.Minecraft;
import xyz.fraction.Fraction;
import xyz.fraction.event.impl.PreMotionEvent;

public abstract class SpeedBase {
    protected static final Minecraft mc = Minecraft.getMinecraft();

    public void onPre(PreMotionEvent e) {}
    public void onPost() {}

    public Speed getSpeed() {
        return (Speed) Fraction.INSTANCE.getModuleManager().getModule(Speed.class);
    }
}
