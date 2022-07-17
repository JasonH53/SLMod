package me.strafe.utils.Registers;

import me.strafe.utils.Friendutils;
import me.strafe.utils.webhookClass;

public class Registers {

    public static Friendutils[] FriendsDatabase = new Friendutils[100];
    public static webhookClass[] webDB = new webhookClass[1000];

    public static int sentPlayers = 0;
    public static int webhookPlayers = 0;

    public static void init() {
        for (int i = 0; i <= FriendsDatabase.length-1; i ++) {
            FriendsDatabase[i] = new Friendutils();
        }
        for (int i = 0; i <= webDB.length-1; i ++) {
            webDB[i] = new webhookClass();
        }

    }

}
