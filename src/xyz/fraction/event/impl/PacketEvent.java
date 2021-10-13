package xyz.fraction.event.impl;

import net.minecraft.network.Packet;
import xyz.fraction.event.Event;

public class PacketEvent extends Event {
    private final Packet<?> packet;

    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }
}
