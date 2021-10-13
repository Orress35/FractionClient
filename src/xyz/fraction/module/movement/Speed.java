package xyz.fraction.module.movement;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.BooleanSetting;
import xyz.fraction.setting.impl.DoubleSetting;
import xyz.fraction.setting.impl.ModeSetting;
import xyz.fraction.util.MoveUtil;

@ModuleInfo()
public class Speed extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"Vanilla"});
    private final DoubleSetting vanillaSpeed = new DoubleSetting(this, "Vanilla Speed", 0.1, 2.5, 1.0);
    private final BooleanSetting vanillaJump = new BooleanSetting(this, "Vanilla Jump", false);

    @Override
    public void onPre(PreMotionEvent e) {
        mc.thePlayer.swingItem();
        setDisplayName(getName() + " &7[" + mode.get() + "]");
        switch (mode.get()) {
            case "Vanilla":
                if (vanillaJump.get() && MoveUtil.isMoving() && e.isOnGround())
                    mc.thePlayer.jump();
                MoveUtil.strafe(vanillaSpeed.get());
                break;
        }
    }
}
