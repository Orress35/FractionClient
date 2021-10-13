package xyz.fraction.event.impl;

import xyz.fraction.event.Event;

public class JumpEvent extends Event {
    private double motion;

    public JumpEvent(double motion) {
        this.motion = motion;
    }

    public double getMotion() {
        return motion;
    }

    public void setMotion(double motion) {
        this.motion = motion;
    }
}
