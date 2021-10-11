package xyz.fraction.module.movement;

import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.BooleanSetting;
import xyz.fraction.setting.impl.DoubleSetting;
import xyz.fraction.setting.impl.IntSetting;
import xyz.fraction.setting.impl.ModeSetting;

@ModuleInfo()
public class Fly extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"Vanilla"});
    private final DoubleSetting vanillaSpeed = new DoubleSetting(this, "Vanilla Speed", 0.1, 2.5, 1.0);

    private final BooleanSetting testA = new BooleanSetting(this, "Test A", false);
    private final BooleanSetting testB = new BooleanSetting(this, "Test B", false);
    private final BooleanSetting testC = new BooleanSetting(this, "Test C", false);
    private final BooleanSetting testD = new BooleanSetting(this, "Test D", false);
    private final BooleanSetting testE = new BooleanSetting(this, "Test E", false);
    private final BooleanSetting testF = new BooleanSetting(this, "Test F", false);
    private final BooleanSetting testG = new BooleanSetting(this, "Test G", false);
    private final BooleanSetting testH = new BooleanSetting(this, "Test H", false);
    private final BooleanSetting testI = new BooleanSetting(this, "Test I", false);
    private final BooleanSetting testJ = new BooleanSetting(this, "Test J", false);
    private final BooleanSetting testK = new BooleanSetting(this, "Test K", false);
    private final IntSetting testL = new IntSetting(this, "Test L", 0, 10, 5);

    @Override
    public void onPre(PreMotionEvent e) {
        setDisplayName(getName() + " &7[" + mode.get() + "]");
        switch (mode.get()) {
            case "Vanilla":
                mc.thePlayer.motionY = 0;

                if (mc.gameSettings.keyBindJump.isKeyDown())
                    mc.thePlayer.motionY += vanillaSpeed.get();

                if (mc.gameSettings.keyBindSneak.isKeyDown())
                    mc.thePlayer.motionY -= vanillaSpeed.get();


                break;
        }
    }
}
