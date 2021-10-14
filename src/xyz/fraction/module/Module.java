package xyz.fraction.module;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import xyz.fraction.event.impl.JumpEvent;
import xyz.fraction.event.impl.PacketEvent;
import xyz.fraction.event.impl.PreMotionEvent;
import xyz.fraction.setting.Setting;
import xyz.fraction.setting.impl.BooleanSetting;
import xyz.fraction.setting.impl.DoubleSetting;
import xyz.fraction.setting.impl.IntSetting;
import xyz.fraction.setting.impl.ModeSetting;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Module {
    private int key;
    private String displayName;
    private final String name;
    private final Category category;
    private final List<Setting> settings = new ArrayList<>();
    protected static final Minecraft mc = Minecraft.getMinecraft();

    private boolean enabled = false;

    public Module() {
        this.key = this.getClass().getAnnotation(ModuleInfo.class).key();
        this.name = displayName = this.getClass().getSimpleName();

        String fullName = this.getClass().getName();
        if (fullName.contains("combat.")) {
            category = Category.COMBAT;
        } else if (fullName.contains("movement.")) {
            category = Category.MOVEMENT;
        } else if (fullName.contains("render.")) {
            category = Category.RENDER;
        } else if (fullName.contains("testing.")) {
            category = Category.TESTING;
        } else {
            category = Category.OTHER;
        }
    }

    public void load() {
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field: fields) {
                boolean accessible = field.isAccessible();
                if (!accessible)
                    field.setAccessible(true);

                if (field.isAccessible()) {
                    Object obj = field.get(this);
                    if (obj instanceof Setting)
                        settings.add((Setting) obj);
                }

                if (!accessible)
                    field.setAccessible(false);
            }
        } catch (Exception e) {
            System.out.println("Failed to get settings from fields!");
            e.printStackTrace();
        }
    }

    public void loadSettings(List<String> info) {
        for (String line: info) {
            for (Setting setting: settings) {
                if (setting.getName().equals(line)) {
                    String value = line.split(":")[2];

                    if (setting instanceof BooleanSetting) {
                        ((BooleanSetting) setting).set(Boolean.parseBoolean(value));
                    } else if (setting instanceof DoubleSetting) {
                        ((DoubleSetting) setting).set(Double.parseDouble(value));
                    } else if (setting instanceof IntSetting) {
                        ((IntSetting) setting).set(Integer.parseInt(value));
                    } else if (setting instanceof ModeSetting) {
                        ((ModeSetting) setting).set(Integer.parseInt(value));
                    }

                    break;
                }
            }
        }
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public void onPre(PreMotionEvent e) { }
    public void onPost() { }
    public void onUpdate() { }
    public void onSend(PacketEvent e) { }
    public void onReceive(PacketEvent e) { }
    public void onJump(JumpEvent e) { }
    public void onRender() { }

    public void onKey(int key) {
        if (key == this.key)
            toggle();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void toggle() {
        enabled = !enabled;
        if (enabled)
            onEnable();
        else
            onDisable();
    }

    public void onEnable  () { }
    public void onDisable () { }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName.replace("&", "§");
    }

    public void send(String message) {
        mc.thePlayer.addChatMessage(new ChatComponentText(("&8[&aFraction&8] &7" + message).replace("&", "§")));
    }
}
