package xyz.fraction.module.movement;

import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import xyz.fraction.event.impl.PacketEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;

@ModuleInfo()
public class NoRotate extends Module {
    @Override
    public void onReceive(PacketEvent e) {
        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
            ((S08PacketPlayerPosLook) e.getPacket()).setYaw(mc.thePlayer.rotationYaw);
            ((S08PacketPlayerPosLook) e.getPacket()).setPitch(mc.thePlayer.rotationPitch);
        }
    }
}
