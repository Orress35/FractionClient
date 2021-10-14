package xyz.fraction.module.movement;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.BooleanSetting;
import xyz.fraction.setting.impl.DoubleSetting;
import xyz.fraction.setting.impl.IntSetting;
import xyz.fraction.setting.impl.ModeSetting;
import xyz.fraction.util.MoveUtil;

@ModuleInfo()
public class Fly extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"Vanilla"});
    private final DoubleSetting vanillaSpeed = new DoubleSetting(this, "Vanilla Speed", 0.1, 2.5, 1.0);

    @Override
    public void onPre(PreMotionEvent e) {
        setDisplayName(getName() + " &7" + mode.get());
        switch (mode.get()) {
            case "Vanilla":
                mc.thePlayer.motionY = 0;

                if (mc.gameSettings.keyBindJump.isKeyDown())
                    mc.thePlayer.motionY += vanillaSpeed.get();

                if (mc.gameSettings.keyBindSneak.isKeyDown())
                    mc.thePlayer.motionY -= vanillaSpeed.get();

                MoveUtil.strafe(vanillaSpeed.get());
                break;
        }
    }
}
