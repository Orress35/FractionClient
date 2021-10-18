package xyz.fraction.module.movement.complex.speed;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.movement.complex.speed.impl.*;
import xyz.fraction.setting.impl.ModeSetting;

public class Speed extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"Dev", "Vanilla", "VanillaHop", "NCP", "Negativity"});

    SpeedBase dev = new SpeedDev(), vanilla = new SpeedVanilla(), vanillaHop = new SpeedVanillaHop(), ncp = new SpeedNCP(), negativity = new SpeedNegativity();

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1F;
    }

    @Override
    public void onPre(PreMotionEvent e) {
        setDisplayName(getName() + "&7 " + mode.get());
        getSpeed().onPre(e);
    }

    @Override
    public void onPost() {
        getSpeed().onPost();
    }

    public SpeedBase getSpeed() {
        switch (mode.get()) {
            case "Dev":
                return dev;
            case "Vanilla":
                return vanilla;
            case "VanillaHop":
                return vanillaHop;
            case "NCP":
                return ncp;
            case "Negativity":
                return negativity;
            default:
                send("invalid speed mode : " + mode.get());
                return vanilla;
        }
    }
}
