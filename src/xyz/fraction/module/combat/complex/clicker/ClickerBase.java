package xyz.fraction.module.combat.complex.clicker;

import net.minecraft.client.Minecraft;
import xyz.fraction.Fraction;

public abstract class ClickerBase {
    protected static final Minecraft mc = Minecraft.getMinecraft();

    private final boolean onRender;

    public ClickerBase(boolean onRender) {
        this.onRender = onRender;
    }

    public abstract boolean click();

    public void next() {

    }

    public boolean isOnRender() {
        return onRender;
    }

    public long getMinCps() {
        return ((Clicker) Fraction.INSTANCE.getModuleManager().getModule(Clicker.class)).getMinCps();
    }

    public long getMaxCps() {
        return ((Clicker) Fraction.INSTANCE.getModuleManager().getModule(Clicker.class)).getMaxCps();
    }

    public long now() {
        return System.currentTimeMillis();
    }
}
