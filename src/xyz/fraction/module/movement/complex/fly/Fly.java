package xyz.fraction.module.movement.complex.fly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.movement.complex.fly.impl.*;
import xyz.fraction.setting.impl.ModeSetting;

public class Fly extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"Dev", "Vanilla", "Negativity", "Verus", "Warden"});

    FlyBase dev = new FlyDev(), vanilla = new FlyVanilla(), negativity = new FlyNegativity(), verus = new FlyVerus(), warden = new FlyWarden();

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1F;
        BlockAir.solid = false;
    }

    @Override
    public void onPre(PreMotionEvent e) {
        setDisplayName(getName() + "&7 " + mode.get());
        getFly().onPre(e);

        if (!mc.thePlayer.onGround)
            mc.thePlayer.cameraYaw += 0.03;
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
            case "Verus":
                return verus;
            case "Warden":
                return warden;
            default:
                send("invalid speed mode : " + mode.get());
                return vanilla;
        }
    }
}
