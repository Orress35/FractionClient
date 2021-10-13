package xyz.fraction.util;

public class Stopwatch {
    private long last = now();

    private long now() {
        return System.currentTimeMillis();
    }

    public boolean elapsedNoReset(long ms) {
        return now() - last > ms;
    }

    public boolean elapsed(long ms) {
        if (now() - last > ms) {
            reset();
            return true;
        }

        return false;
    }

    public void reset() {
        last = now();
    }
}
