package xyz.fraction.event.impl;

import xyz.fraction.event.Event;

public class PreMotionEvent extends Event {
    private double x, y, z;
    private float yaw, pitch;
    private boolean onGround, sprinting, sneaking;

    public PreMotionEvent(double x, double y, double z, float yaw, float pitch, boolean onGround, boolean sprinting, boolean sneaking) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
        this.sprinting = sprinting;
        this.sneaking = sneaking;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public boolean isSprinting() {
        return sprinting;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public void setSneaking(boolean sneaking) {
        this.sneaking = sneaking;
    }

    public void setSprinting(boolean sprinting) {
        this.sprinting = sprinting;
    }
}
