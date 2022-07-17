package me.strafe.utils.handlers;

import java.io.File;

import me.strafe.commands.clientcommand.ClientFriendCommand;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
    public static Configuration config;
    public static final String file = "config/WinnieTheMod2.cfg";

    public static int getInt(String category, String key) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                int n = config.get(category, key, 0).getInt();
                return n;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            config.save();
        }
        return 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static double getDouble(String category, String key) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                double d = config.get(category, key, 0.0).getDouble();
                return d;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            config.save();
        }
        return 0.0;
    }

    public static String getString(String category, String key) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                String string = config.get(category, key, "").getString();
                return string;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            config.save();
        }
        return "";
    }

    public static boolean getBoolean(String category, String key) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.getCategory(category).containsKey(key)) {
                boolean bl = config.get(category, key, false).getBoolean();
                return bl;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            config.save();
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writeIntConfig(String category, String key, int value) {
        config = new Configuration(new File(file));
        try {
            config.load();
            int set = config.get(category, key, value).getInt();
            config.getCategory(category).get(key).set(value);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            config.save();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writeDoubleConfig(String category, String key, double value) {
        config = new Configuration(new File(file));
        try {
            config.load();
            double set = config.get(category, key, value).getDouble();
            config.getCategory(category).get(key).set(value);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            config.save();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writeStringConfig(String category, String key, String value) {
        config = new Configuration(new File(file));
        try {
            config.load();
            String set = config.get(category, key, value).getString();
            config.getCategory(category).get(key).set(value);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            config.save();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writeBooleanConfig(String category, String key, boolean value) {
        config = new Configuration(new File(file));
        try {
            config.load();
            boolean set = config.get(category, key, value).getBoolean();
            config.getCategory(category).get(key).set(value);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            config.save();
        }
    }

    public static boolean hasKey(String category, String key) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (!config.hasCategory(category)) {
                boolean bl = false;
                return bl;
            }
            boolean bl = config.getCategory(category).containsKey(key);
            return bl;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            config.save();
        }
        return false;
    }

    public static void deleteCategory(String category) {
        config = new Configuration(new File(file));
        try {
            config.load();
            if (config.hasCategory(category)) {
                config.removeCategory(new ConfigCategory(category));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            config.save();
        }
    }

    public static void reloadConfig() {
        if (!ConfigHandler.hasKey("misc", "ModPrefixID")) {
            ConfigHandler.writeIntConfig("misc", "ModPrefixID", 0);
        }
        if (!ConfigHandler.hasKey("misc", "FriendList")) {
            ConfigHandler.writeStringConfig("misc", "FriendList", "");
        }
        ClientFriendCommand.FriendList = ConfigHandler.getString("misc", "FriendList");
    }
}
