package xyz.fraction.module.combat.complex.clicker.mode;

import xyz.fraction.module.combat.complex.clicker.ClickerBase;

public class Clicker2 extends ClickerBase {
    public Clicker2() {
        super(false);
    }

    @Override
    public boolean click() {
        return mc.thePlayer.ticksExisted % 2 == 0;
    }
}
