package xyz.fraction.module.movement.simple;

import net.minecraft.potion.Potion;
import xyz.fraction.Fraction;
import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.setting.impl.BooleanSetting;
import xyz.fraction.setting.impl.DoubleSetting;
import xyz.fraction.setting.impl.ModeSetting;
import xyz.fraction.util.MoveUtil;

public class Sprint extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"Basic", "Legit"});

    private final BooleanSetting usingItem = new BooleanSetting(this, "Using Item", false);
    private final BooleanSetting blindness = new BooleanSetting(this, "Blindness", false);
    private final BooleanSetting collision = new BooleanSetting(this, "Collision", false);
    private final BooleanSetting hunger = new BooleanSetting(this, "Hunger", false);
    private final BooleanSetting gui = new BooleanSetting(this, "GUI", false);
    private final BooleanSetting sneaking = new BooleanSetting(this, "Sneaking", false);

    private static boolean omni = false;

    @Override
    public void onPre(PreMotionEvent e) {
        setDisplayName(getName() + "&7 " + mode.get());

        if (mode.get().equals("Basic")) {
            NoSlow noSlow = (NoSlow) Fraction.INSTANCE.getModuleManager().getModule(NoSlow.class);
            omni = true;
            mc.thePlayer.setSprinting(MoveUtil.isMoving() && !mc.thePlayer.isSneaking() && (!mc.thePlayer.isUsingItem() || (noSlow.isEnabled() && noSlow.canSprint()) || usingItem.get()) && (!mc.thePlayer.isPotionActive(Potion.blindness) || blindness.get()) && (!mc.thePlayer.isCollidedHorizontally || collision.get()) && (mc.thePlayer.getFoodStats().getFoodLevel() >= 6 || hunger.get()) && (mc.currentScreen == null || gui.get()));
        } else {
            NoSlow noSlow = (NoSlow) Fraction.INSTANCE.getModuleManager().getModule(NoSlow.class);
            omni = false;
            mc.thePlayer.setSprinting(mc.thePlayer.moveForward > 0 && !mc.thePlayer.isSneaking() && (!mc.thePlayer.isUsingItem() || (noSlow.isEnabled() && noSlow.canSprint()) || usingItem.get()) && (!mc.thePlayer.isPotionActive(Potion.blindness) || blindness.get()) && (!mc.thePlayer.isCollidedHorizontally || collision.get()) && (mc.thePlayer.getFoodStats().getFoodLevel() >= 6 || hunger.get()) && (mc.currentScreen == null || gui.get()));
        }
    }

    public static boolean isOmni() {
        return omni && Fraction.INSTANCE.getModuleManager().getModule(Sprint.class).isEnabled();
    }

    public String getMode() {
        if (!isEnabled())
            return "Legit";
        return mode.get();
    }

    public boolean getUsingItem() {
        if (!isEnabled())
            return false;
        return usingItem.get();
    }

    public boolean getBlindness() {
        if (!isEnabled())
            return false;
        return blindness.get();
    }

    public boolean getCollision() {
        if (!isEnabled())
            return false;
        return collision.get();
    }

    public boolean getHunger() {
        if (!isEnabled())
            return false;
        return hunger.get();
    }

    public boolean getSneaking() {
        if (!isEnabled())
            return false;
        return sneaking.get();
    }
}
