package xyz.fraction.module.combat;

import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.input.Mouse;
import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;

@ModuleInfo()
public class Triggerbot extends Module {
    private final int[] delays = new int[] {1,2,2,1,2,3,2,3,2,2,3,2,1,2,2,1,2,2,3,2,1,2,2,3,2,2,3,2,2,1,2,2,2,1,2,2,1,2,2,2,1,2,2,2,1,2,2,2,5,2,2,1,2,2,2,3,2,1,2,2,2,3,2,2,2,2,1,2,2,1,2,2,3,2,2,2,1,2,2,2,2,1,2,2,2,1,2,2,2,1,2,2,2,3,2,2,3,2,2,1,2,2,2,2,2,2,1,2,2,2,2,3,2,2,2,5,2,2,2,2,1,2,2,2,1,2,2,2,2,2,2,2,2,1,2,2,2,1,2,2,2,3,2,2,2,1,2,2,2,2,2,1,2,2,2,2,1,2,2,2,3,2,3,2,2,2,2,2,2,1,2,4,2,2,1,2,2,2,2,2,2,2,3,2,2,2,2,2,2,2,1,2,2,2,2,2,3,2,2,2,2,2,2,3,2,2,2,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,2,2,2,2,2,2,2,2,1,2,5,2,2,2};
    private int clickTicks = 0;
    private int index = 0;

    @Override
    public void onPre(PreMotionEvent e) {
        clickTicks = Math.min(clickTicks + 1, 50);

        if (clickTicks < delays[index]) {
            return;
        }

        clickTicks = 0;

        index++;

        if (index >= delays.length)
            index = (int) (Math.random() * 8);

        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            mc.gameSettings.keyBindAttack.setPressTime(1);
        }
    }
}
