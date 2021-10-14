package xyz.fraction;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import xyz.fraction.event.EventHandler;
import xyz.fraction.module.Module;
import xyz.fraction.module.ModuleManager;
import xyz.fraction.setting.Setting;
import xyz.fraction.setting.impl.BooleanSetting;
import xyz.fraction.setting.impl.DoubleSetting;
import xyz.fraction.setting.impl.IntSetting;
import xyz.fraction.setting.impl.ModeSetting;
import xyz.fraction.util.font.Fonts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public enum Fraction {
    INSTANCE;

    private final EventHandler eventHandler = new EventHandler();
    private final ModuleManager moduleManager = new ModuleManager();

    public static final String CLIENT_FOLDER = Minecraft.getMinecraft().mcDataDir.getAbsolutePath().substring(0, Minecraft.getMinecraft().mcDataDir.getAbsolutePath().length() - 1) + "Fraction/";
    public static final String CLIENT_SETTINGS = CLIENT_FOLDER + "settings.txt";
    public static final String CLIENT_ALTS = CLIENT_FOLDER + "alts.txt";
    public static final String CLIENT_CONFIGS = CLIENT_FOLDER + "configs/";

    public void start() {
        File dir = new File(CLIENT_CONFIGS);
        if (dir.mkdirs())
            System.out.println("Created client directory");

        Fonts.setup();

        for (Module module: moduleManager.getModules()) {
            module.load();
            module.loadSettings(readSettings(module));
        }
    }

    public void stop() {
        saveSettings();
    }

    public List<String> readSettings(Module module) {
        List<String> result = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(CLIENT_SETTINGS));
            String line;

            while ((line = br.readLine()) != null) {
                if (line.isEmpty())
                    continue;

                if (!line.contains(":")) {
                    String name = line.split(",")[0];
                    int key = Integer.parseInt(line.split(",")[1]);
                    boolean enabled = Boolean.parseBoolean(line.split(",")[2]);

                    if (name.equals(module.getName())) {
                        module.setKey(key);

                        if (enabled)
                            module.toggle();
                    }
                } else {
                    String name = line.split(":")[0];
                    String setting = line.split(":")[1];
                    String value = line.split(":")[2];

                    if (name.equals(module.getName())) {
                        for (Setting s: module.getSettings()) {
                            if (s.getName().equals(setting)) {
                                if (s instanceof BooleanSetting) {
                                    ((BooleanSetting) s).set(Boolean.parseBoolean(value));
                                } else if (s instanceof DoubleSetting) {
                                    ((DoubleSetting) s).set(Double.parseDouble(value));
                                } else if (s instanceof IntSetting) {
                                    ((IntSetting) s).set(Integer.parseInt(value));
                                } else if (s instanceof ModeSetting) {
                                    ((ModeSetting) s).set(Integer.parseInt(value));
                                }

                                break;
                            }

                            break;
                        }
                    }
                }
            }

            br.close();
        } catch (Exception ignored) { }

        return result;
    }

    public void saveSettings() {
        try {
            PrintWriter pw = new PrintWriter(CLIENT_SETTINGS);

            for (Module module: moduleManager.getModules()) {
                String name = module.getName();
                int key = module.getKey();
                boolean enabled = module.isEnabled();

                pw.println(name + "," + key + "," + enabled);

                for (Setting s: module.getSettings()) {
                    String setting = s.getName();

                    if (s instanceof BooleanSetting) {
                        pw.println(name + ":" + setting + ":" + ((BooleanSetting) s).get());
                    } else if (s instanceof DoubleSetting) {
                        pw.println(name + ":" + setting + ":" + ((DoubleSetting) s).get());
                    } else if (s instanceof IntSetting) {
                        pw.println(name + ":" + setting + ":" + ((IntSetting) s).get());
                    } else if (s instanceof ModeSetting) {
                        pw.println(name + ":" + setting + ":" + ((ModeSetting) s).getSelected());
                    }
                }
            }

            pw.close();
        } catch (Exception ignored) { }
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public void send(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(("&8[&aFraction&8] &7" + message).replace("&", "§")));
    }

    public void config(String[] args) {
        if (args.length < 2) {
            send(".config <load/save/list> [file]");
            return;
        }

        switch (args[1]) {
            case "load":
                if (args.length < 3) {
                    send(".config <load/save/list> [file]");
                    return;
                }

                try {
                    BufferedReader br = new BufferedReader(new FileReader(CLIENT_CONFIGS + args[2]));
                    String line;

                    while ((line = br.readLine()) != null) {
                        if (line.isEmpty())
                            continue;

                        if (!line.contains(":")) {
                            String name = line.split(",")[0];
                            Module module = moduleManager.getModule(name);
                            boolean enabled = Boolean.parseBoolean(line.split(",")[1]);

                            if (enabled)
                                module.toggle();
                        } else {
                            String name = line.split(":")[0];
                            Module module = moduleManager.getModule(name);
                            String setting = line.split(":")[1];
                            String value = line.split(":")[2];

                            for (Setting s: module.getSettings()) {
                                if (s.getName().equals(setting)) {
                                    if (s instanceof BooleanSetting) {
                                        ((BooleanSetting) s).set(Boolean.parseBoolean(value));
                                    } else if (s instanceof DoubleSetting) {
                                        ((DoubleSetting) s).set(Double.parseDouble(value));
                                    } else if (s instanceof IntSetting) {
                                        ((IntSetting) s).set(Integer.parseInt(value));
                                    } else if (s instanceof ModeSetting) {
                                        ((ModeSetting) s).set(Integer.parseInt(value));
                                    }

                                    break;
                                }

                                break;
                            }
                        }
                    }

                    br.close();

                    send("loaded config with name " + args[2]);
                } catch (Exception ignored) { }
                break;
            case "save":
                if (args.length < 3) {
                    send(".config <load/save/list> [file]");
                    return;
                }

                try {
                    PrintWriter pw = new PrintWriter(CLIENT_CONFIGS + args[2]);

                    for (Module module: moduleManager.getModules()) {
                        String name = module.getName();
                        boolean enabled = module.isEnabled();

                        pw.println(name + "," + enabled);

                        for (Setting s: module.getSettings()) {
                            String setting = s.getName();

                            if (s instanceof BooleanSetting) {
                                pw.println(name + ":" + setting + ":" + ((BooleanSetting) s).get());
                            } else if (s instanceof DoubleSetting) {
                                pw.println(name + ":" + setting + ":" + ((DoubleSetting) s).get());
                            } else if (s instanceof IntSetting) {
                                pw.println(name + ":" + setting + ":" + ((IntSetting) s).get());
                            } else if (s instanceof ModeSetting) {
                                pw.println(name + ":" + setting + ":" + ((ModeSetting) s).getSelected());
                            }
                        }
                    }

                    pw.close();

                    send("saved config with name &a" + args[2]);
                } catch (Exception ignored) { }
                break;
            case "list":
                send("Available configs:");
                for (String config: Objects.requireNonNull(new File(CLIENT_CONFIGS).list()))
                    send(" - &a" + config);
                break;
        }
    }
}
