package xyz.fraction.module.movement.complex.fly.impl;

import net.minecraft.block.BlockAir;
import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.movement.complex.fly.FlyBase;

public class FlyVerus extends FlyBase {
    int add = 0;
    long last = System.currentTimeMillis();

    @Override
    public void onPre(PreMotionEvent e) {
        BlockAir.solid = true;
        if (System.currentTimeMillis() - last > 100L)
            BlockAir.solidY = (int) Math.round(e.getY()) - 1;

        mc.thePlayer.jumpMovementFactor *= 1.5;
        last = System.currentTimeMillis();
    }
}
