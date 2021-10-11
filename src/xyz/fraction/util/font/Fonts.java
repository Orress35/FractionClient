package xyz.fraction.util.font;

import java.awt.Font;

public class Fonts {
    public static FontRenderer fractionSmall;
    public static FontRenderer fractionNormal;
    public static FontRenderer fractionBig;

    public static void setup() {
        fractionSmall = new FontRenderer(new Font("Abadi", Font.PLAIN, 15), true, true);
        fractionNormal = new FontRenderer(new Font("Abadi", Font.PLAIN, 20), true, true);
        fractionBig = new FontRenderer(new Font("Abadi", Font.PLAIN, 25), true, true);
    }
}
