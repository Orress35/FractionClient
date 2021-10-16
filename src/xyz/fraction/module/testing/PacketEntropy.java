package xyz.fraction.module.testing;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import xyz.fraction.event.impl.PacketEvent;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleInfo;
import xyz.fraction.setting.impl.BooleanSetting;

import java.util.Arrays;
import java.util.List;

@ModuleInfo()
public class PacketEntropy extends Module {
    private final BooleanSetting debug = new BooleanSetting(this, "Debug", false);
    private final BooleanSetting positions = new BooleanSetting(this, "Positions", true);
    private final BooleanSetting packets = new BooleanSetting(this, "Packets", false);

    @Override
    public void onSend(PacketEvent e) {
        final Packet<?> packet = e.getPacket();
        if (packet instanceof C0BPacketEntityAction) {
            if (packets.get()) {
                if (Math.random() > 0.75) {
                    ((C0BPacketEntityAction) packet).setAction(C0BPacketEntityAction.Action.values()[(int) (Math.random() * C0BPacketEntityAction.Action.values().length)]);
                    alert("action", packet);
                } else if (Math.random() > 0.75) {
                    ((C0BPacketEntityAction) packet).setAuxData((int) (1 + Math.random() * 5));
                    alert("auxData", packet);
                }
            }
        } else if (packet instanceof C0CPacketInput) {
            if (packets.get()) {
                if (Math.random() > 0.99) {
                    ((C0CPacketInput) packet).setForwardSpeed((float) Math.random() * Math.signum(((C0CPacketInput) packet).getForwardSpeed()));
                    alert("forwardSpeed", packet);
                }

                if (Math.random() > 0.99) {
                    ((C0CPacketInput) packet).setStrafeSpeed((float) Math.random() * Math.signum(((C0CPacketInput) packet).getStrafeSpeed()));
                    alert("strafeSpeed", packet);
                }
            }
        } else if (packet instanceof C02PacketUseEntity) {
            if (packets.get()) {
                if (Math.random() > 0.75) {
                    ((C02PacketUseEntity) packet).setEntityId((int) (Math.random() * 1000 + 1));
                    alert("entityId", packet);
                }
            }
        } else if (packet instanceof C03PacketPlayer) {
            if (packets.get()) {
                if (Math.random() > 0.99) {
                    ((C03PacketPlayer) packet).setMoving(Math.random() > 0.5);
                    alert("moving", packet);
                }

                if (Math.random() > 0.99) {
                    ((C03PacketPlayer) packet).setRotating(Math.random() > 0.5);
                    alert("rotating", packet);
                }

                if (Math.random() > 0.99) {
                    ((C03PacketPlayer) packet).setOnGround(Math.random() > 0.5);
                    alert("onGround", packet);
                }
            }

            if (positions.get()) {
                if (Math.random() > 0.975) {
                    int from = (int) ((C03PacketPlayer) packet).getPositionX();
                    ((C03PacketPlayer) packet).setX(change((int) ((C03PacketPlayer) packet).getPositionX(), (int) (Math.random() * 8 + 1)) + ((C03PacketPlayer) packet).getPositionX() % 1.0);
                    int to = (int) ((C03PacketPlayer) packet).getPositionX();
                    send("flipped a bit in the &aX&7 : &a" + from + "&7 -> &a" + to);
                }

                if (Math.random() > 0.975) {
                    int from = (int) ((C03PacketPlayer) packet).getPositionY();
                    ((C03PacketPlayer) packet).setY(change((int) ((C03PacketPlayer) packet).getPositionY(), (int) (Math.random() * 8 + 1)) + ((C03PacketPlayer) packet).getPositionY() % 1.0);
                    int to = (int) ((C03PacketPlayer) packet).getPositionY();
                    send("flipped a bit in the &aY&7 : &a" + from + "&7 -> &a" + to);
                }

                if (Math.random() > 0.975) {
                    int from = (int) ((C03PacketPlayer) packet).getPositionZ();
                    ((C03PacketPlayer) packet).setZ(change((int) ((C03PacketPlayer) packet).getPositionZ(), (int) (Math.random() * 8 + 1)) + ((C03PacketPlayer) packet).getPositionZ() % 1.0);
                    int to = (int) ((C03PacketPlayer) packet).getPositionZ();
                    send("flipped a bit in the &aZ&7 : &a" + from + "&7 -> &a" + to);
                }
            }
        } else if (packet instanceof C09PacketHeldItemChange) {
            if (packets.get()) {
                if (Math.random() > 0.75) {
                    ((C09PacketHeldItemChange) packet).setSlotId(((C09PacketHeldItemChange) packet).getSlotId() - (int) (Math.random() * 5) + (int) (Math.random() * 5));
                    alert("slotId", packet);
                }
            }
        }
    }

    public void alert(String field, Packet<?> packet) {
        if (debug.get())
            send("changed field &a" + field + "&7 in &a" + packet.getClass().getSimpleName());
    }

    public int change(int x, int n) {
        return x ^ (1 << n);
    }

    public long change(long x, long n) {
        return x ^ (1L << n);
    }
}
