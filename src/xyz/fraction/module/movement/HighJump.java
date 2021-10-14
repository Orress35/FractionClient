package xyz.fraction.module.movement;

import net.minecraft.potion.Potion;
import xyz.fraction.event.impl.JumpEvent;
import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.BooleanSetting;
import xyz.fraction.setting.impl.DoubleSetting;
import xyz.fraction.setting.impl.IntSetting;
import xyz.fraction.setting.impl.ModeSetting;
import xyz.fraction.util.MoveUtil;

@ModuleInfo()
public class HighJump extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"Vanilla", "Clip", "FakeStep", "FakeStep2", "MultiJump"});
    private final DoubleSetting vanillaHeight = new DoubleSetting(this, "Vanilla Height", 0.42, 2.5, 1.0);
    private final DoubleSetting clipHeight = new DoubleSetting(this, "Clip Height", 0.1, 5, 1.0);
    private final IntSetting clipTick = new IntSetting(this, "Clip Tick", 1, 10, 1);
    private final IntSetting fakeStepMultiplier = new IntSetting(this, "FakeStep Multiplier", 1, 64, 32);
    private final IntSetting fakeStep2Multiplier = new IntSetting(this, "FakeStep2 Multiplier", 1, 64, 32);
    private final IntSetting multiJumpLimit = new IntSetting(this, "MultiJump Limit", 1, 10, 1);
    private final IntSetting multiJumpDelay = new IntSetting(this, "MultiJump Delay", 1, 10, 1);
    private final BooleanSetting multiJumpACR = new BooleanSetting(this, "MultiJump ACR", false);

    int airTicks = 0, jumps = 0;
    boolean jump = false, delayedJumpA = false, delayedJumpB = false;

    double fakeStepY = 0;

    @Override
    public void onPre(PreMotionEvent e) {
        setDisplayName(getName() + " &7" + mode.get());

        if (mc.thePlayer.onGround) {
            jumps = 0;
            airTicks = 0;
        } else {
            airTicks = Math.min(airTicks + 1, 100);
        }

        if (airTicks == 1) {
            jump = mc.thePlayer.motionY > 0;
        }

        switch (mode.get()) {
            case "Clip":
                if (jump && airTicks == clipTick.get())
                    mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + clipHeight.get(), mc.thePlayer.posZ);
                break;
            case "MultiJump":
                if (jump && airTicks > 0) {
                    if (airTicks % multiJumpDelay.get() == 0 && ++jumps <= multiJumpLimit.get()) {
                        if (multiJumpACR.get()) {
                            mc.thePlayer.onGround = true;
                            mc.thePlayer.motionY = 0.42F;

                            double limit = 0.36 * Math.pow(0.985, airTicks + 1) - 0.032F;

                            MoveUtil.strafe(Math.max(limit * 0.75, 0));
                        } else {
                            mc.thePlayer.jump();
                        }
                    }
                }
                break;
        }

        if (airTicks == 2) {
            if (delayedJumpA) {
                mc.thePlayer.setPosition(mc.thePlayer.posX, fakeStepY, mc.thePlayer.posZ);

                mc.thePlayer.jumpNoEvent();
                delayedJumpA = false;
            }

            if (delayedJumpB) {
                mc.thePlayer.setPosition(mc.thePlayer.posX, fakeStepY, mc.thePlayer.posZ);

                double motionY = 0.42F;

                if (mc.thePlayer.isPotionActive(Potion.jump))
                    motionY += (float) (mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;

                motionY -= 0.08D;
                motionY *= 0.9800000190734863D;

                mc.thePlayer.motionY = motionY;

                delayedJumpB = false;
            }
        }
    }

    @Override
    public void onJump(JumpEvent e) {
        switch (mode.get()) {
            case "Vanilla":
                e.setMotion(vanillaHeight.get());
                break;
            case "FakeStep":
                e.setMotion(0);

                delayedJumpB = true;

                mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + (1/64F) * fakeStepMultiplier.get(), mc.thePlayer.posZ);

                fakeStepY = mc.thePlayer.posY;
                break;
            case "FakeStep2":
                e.setCancelled(true);
                mc.thePlayer.motionY = 0;

                delayedJumpA = true;

                mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + (1/64F) * fakeStep2Multiplier.get(), mc.thePlayer.posZ);

                fakeStepY = mc.thePlayer.posY;
                break;
        }
    }
}
