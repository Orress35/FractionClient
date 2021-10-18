package xyz.fraction.module.movement.complex.fly;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.movement.complex.fly.impl.FlyDev;
import xyz.fraction.module.movement.complex.fly.impl.FlyNegativity;
import xyz.fraction.module.movement.complex.fly.impl.FlyVanilla;
import xyz.fraction.module.movement.complex.fly.impl.FlyWarden;
import xyz.fraction.setting.impl.ModeSetting;

public class Fly extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"Dev", "Vanilla", "Negativity", "Warden"});

    FlyBase dev = new FlyDev(), vanilla = new FlyVanilla(), negativity = new FlyNegativity(), warden = new FlyWarden();

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1F;
    }

    @Override
    public void onPre(PreMotionEvent e) {
        setDisplayName(getName() + "&7 " + mode.get());
        getFly().onPre(e);
    }

    @Override
    public void onPost() {
        getFly().onPost();
    }

    public FlyBase getFly() {
        switch (mode.get()) {
            case "Dev":
                return dev;
            case "Vanilla":
                return vanilla;
            case "Negativity":
                return negativity;
            case "Warden":
                return warden;
            default:
                send("invalid speed mode : " + mode.get());
                return vanilla;
        }
    }
}
