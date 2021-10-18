package xyz.fraction.module.combat.complex.clicker;

import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;
import xyz.fraction.Fraction;
import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.combat.complex.clicker.mode.Clicker1;
import xyz.fraction.module.combat.complex.clicker.mode.Clicker2;
import xyz.fraction.module.combat.complex.clicker.mode.ClickerRnd;
import xyz.fraction.module.combat.simple.NoSwing;
import xyz.fraction.setting.impl.IntSetting;
import xyz.fraction.setting.impl.ModeSetting;

public class Clicker extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"%1", "%2", "Random"});

    private final IntSetting minCps = new IntSetting(this, "Min CPS", 1, 20, 1);
    private final IntSetting maxCps = new IntSetting(this, "Max CPS", 1, 20, 1);

    ClickerBase clicker1 = new Clicker1(), clicker2 = new Clicker2(), clickerRnd = new ClickerRnd();

    @Override
    public void onRender() {
        if (!Mouse.isButtonDown(0) || mc.currentScreen != null)
            return;

        ClickerBase clicker = getClicker();
        if (clicker.isOnRender()) {
            if (clicker.click()) {
                if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                    mc.gameSettings.keyBindAttack.setPressTime(1);
                } else if (!mc.thePlayer.isUsingItem()) {
                    NoSwing noSwing = (NoSwing) Fraction.INSTANCE.getModuleManager().getModule(NoSwing.class);
                    if (!noSwing.isEnabled() || !noSwing.getHitting())
                        mc.thePlayer.swingItem();
                }

                clicker.next();
            }
        }
    }

    @Override
    public void onPre(PreMotionEvent e) {
        setDisplayName(getName() + "&7 " + mode.get());

        if (!Mouse.isButtonDown(0) || mc.currentScreen != null)
            return;

        ClickerBase clicker = getClicker();
        if (!clicker.isOnRender()) {
            if (clicker.click()) {
                if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
                    mc.gameSettings.keyBindAttack.setPressTime(1);
                } else if (!mc.thePlayer.isUsingItem()) {
                    NoSwing noSwing = (NoSwing) Fraction.INSTANCE.getModuleManager().getModule(NoSwing.class);
                    if (!noSwing.isEnabled() || !noSwing.getHitting())
                        mc.thePlayer.swingItem();
                }

                clicker.next();
            }
        }
    }

    public ClickerBase getClicker() {
        switch (mode.get()) {
            case "%1":
                return clicker1;
            case "%2":
                return clicker2;
            case "Random":
                return clickerRnd;
            default:
                send("invalid clicker mode : " + mode.get());
                return clicker1;
        }
    }

    public long getMinCps() {
        return minCps.get();
    }

    public long getMaxCps() {
        return maxCps.get();
    }
}
