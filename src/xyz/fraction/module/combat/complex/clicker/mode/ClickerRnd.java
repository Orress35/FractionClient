package xyz.fraction.module.combat.complex.clicker.mode;

import xyz.fraction.module.combat.complex.clicker.ClickerBase;

public class ClickerRnd extends ClickerBase {
    public ClickerRnd() {
        super(true);
    }

    long last, delay;

    @Override
    public boolean click() {
        return now() - last > delay;
    }

    @Override
    public void next() {
        long min = 1000L / getMinCps();
        long max = 1000L / getMaxCps();

        delay = (long) (Math.random() * (max - min) + min);

        last = now();
    }
}
