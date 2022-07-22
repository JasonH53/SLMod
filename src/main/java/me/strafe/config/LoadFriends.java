package me.strafe.config;

import me.strafe.utils.Registers.Registers;
import net.minecraft.client.Minecraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LoadFriends {
    public static void LoadFile() {
        String path = Minecraft.getMinecraft().mcDataDir + File.separator + "mods" + File.separator + "strafeconfig_friends.txt";
        File file = new File(path);
        try {
            Boolean flag = file.createNewFile();
            if (flag) {
            } else {
                BufferedReader dataIn;
                String c;

                dataIn = new BufferedReader(new FileReader(path));
                String name;
                int GLOBAL_FRIENDS = 0;
                while ((name = dataIn.readLine()) != null) {
                    Registers.FriendsDatabase[GLOBAL_FRIENDS].setName(name);
                    GLOBAL_FRIENDS++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
