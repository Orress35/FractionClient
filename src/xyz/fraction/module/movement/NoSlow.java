package xyz.fraction.module.movement;

import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.BooleanSetting;
import xyz.fraction.setting.impl.DoubleSetting;
import xyz.fraction.setting.impl.IntSetting;
import xyz.fraction.setting.impl.ModeSetting;
import xyz.fraction.util.Stopwatch;

@ModuleInfo()
public class NoSlow extends Module {
    private final ModeSetting mode = new ModeSetting(this, "Mode", new String[] {"Vanilla", "Packet"});
    private final DoubleSetting multiplier = new DoubleSetting(this, "Multiplier", 0.2, 1.0, 0.5);
    private final BooleanSetting canSprint = new BooleanSetting(this, "Can Sprint", false);
    private final IntSetting packetTimer = new IntSetting(this, "Packet Timer", 0, 100, 0);

    private final Stopwatch stopwatch = new Stopwatch();

    @Override
    public void onDisable() {
        mc.gameSettings.keyBindSprint.setPressed(false);
    }

    @Override
    public void onPre(PreMotionEvent e) {
        setDisplayName(getName() + " &7" + mode.get());
        if (mc.thePlayer.isBlocking() && mode.get().equals("Packet")) {
            if (stopwatch.elapsedNoReset(packetTimer.get()))
                unblock();
        }
    }

    @Override
    public void onPost() {
        if (mc.thePlayer.isBlocking() && mode.get().equals("Packet")) {
            if (stopwatch.elapsed(packetTimer.get()))
                block();
        }
    }

    public void block() {
        mc.thePlayer.sendQueue.addToSendQueueSilent(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
    }

    public void unblock() {
        mc.thePlayer.sendQueue.addToSendQueueSilent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
    }

    public double getMultiplier() {
        return multiplier.get();
    }

    public boolean canSprint() {
        return canSprint.get();
    }
}
