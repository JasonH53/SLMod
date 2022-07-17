package me.strafe.commands;

import me.strafe.commands.Command;
import me.strafe.utils.ChatUtils;
import me.strafe.utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class CommandManager {
    public List<Command> commandList = new ArrayList<Command>();
    public List<Command> sortedCommandList = new ArrayList<Command>();

    public CommandManager() {

    }

    public void addCommand(Command c) {
        this.commandList.add(c);
    }

    public List<Command> getCommandList() {
        return this.commandList;
    }

    public Command getCommandByName(String name) {
        for (Command command : this.commandList) {
            if (command.getName().equalsIgnoreCase(name)) {
                return command;
            }
            for (String alias : command.getAliases()) {
                if (!alias.equalsIgnoreCase(name)) continue;
                return command;
            }
        }
        return null;
    }

    public void noSuchCommand(String name) {
        ChatUtils.sendMessage("Command '" + name + "' not found! Report this on the discord if this is an error!");
    }

    public void executeCommand(String commandName, String[] args) {
        Command command = getCommandByName(commandName);
        if (command == null) {
            this.noSuchCommand(commandName);
            return;
        }
        command.onCall(args);
    }
}
