package me.strafe.commands;

import me.strafe.utils.ChatUtils;
import net.minecraft.client.Minecraft;

public class Command {
    private final String name;
    private final String help;
    private final int minArgs;
    private final int maxArgs;
    private final String[] alias;
    private String[] args;

    public Command(String name, String help, int minArgs, int maxArgs, String[] args, String[] alias) {
        this.name = name;
        this.help = help;
        this.minArgs = minArgs;
        this.maxArgs = maxArgs;
        this.args = args;
        this.alias = alias;
    }

    public Command(String name, String help, int minArgs, int maxArgs, String[] args) {
        this(name, help, minArgs, maxArgs, args, new String[0]);
    }

    public String getName() {
        return this.name;
    }

    public String getHelp() {
        return this.help;
    }

    public int getMinArgs() {
        return this.minArgs;
    }

    public int getMaxArgs() {
        return this.maxArgs;
    }

    public String[] getArgs() {
        return this.args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public void onCall(String[] args) {
    }

    public void incorrectArgs() {
        ChatUtils.sendMessage("Incorrect arguments! Run help " + this.getName() + " for usage info");
    }

    public String[] getAliases() {
        return this.alias;
    }
}


