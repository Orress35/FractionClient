package xyz.fraction.module.movement.simple;

import xyz.fraction.module.Module;

public class FastLadder extends Module {
    public double getSpeed() {
        return 0.2D;
    }

    public double getDownSpeed() {
        return -0.15D;
    }
}
