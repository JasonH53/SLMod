package me.strafe.commands.clientcommand;

import java.util.List;

import me.strafe.utils.ChatUtils;
import me.strafe.utils.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;

public class ClientFriendCommand extends CommandBase implements ICommand {
    public static String FriendList;

    public String getCommandName() {
        return "clientfriend";
    }

    public String getCommandUsage(ICommandSender arg0) {
        return "/" + this.getCommandName() + " <add/remove/removeall/list/playername>";
    }

    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return ClientFriendCommand.getListOfStringsMatchingLastWord((String[])args, (String[])new String[]{"add", "remove", "removeall", "list"});
        }
        return null;
    }

    public int getRequiredPermissionLevel() {
        return 0;
    }

    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        if (arg1.length == 0) {
            ChatUtils.addCommandUsageMessage(this.getCommandUsage(arg0));
            return;
        }
        switch (arg1[0].toLowerCase()) {
            case "add": {
                if (arg1.length == 1) {
                    ChatUtils.addCommandUsageMessage(this.getCommandUsage(arg0));
                    return;
                }
                if (!FriendList.contains(arg1[1])) {
                    FriendList = FriendList + ", " + arg1[1];
                    ChatUtils.addChatMessage("Added " + EnumChatFormatting.GOLD + arg1[1] + EnumChatFormatting.WHITE + " to your Friend List.");
                } else {
                    ChatUtils.addChatMessage(EnumChatFormatting.GOLD + arg1[1] + EnumChatFormatting.WHITE+ " is already exist.");
                }
                ConfigHandler.writeStringConfig("misc", "FriendList", FriendList);
                break;
            }
            case "remove": {
                if (arg1.length == 1) {
                    ChatUtils.addCommandUsageMessage(this.getCommandUsage(arg0));
                    return;
                }
                if (FriendList.contains(arg1[1])) {
                    FriendList = FriendList.replace(", " + arg1[1], "");
                    ChatUtils.addChatMessage("Added " + EnumChatFormatting.GOLD + arg1[1] + EnumChatFormatting.WHITE + " to your Friend List.");
                } else {
                    ChatUtils.addChatMessage(EnumChatFormatting.RED + "Couldn't find " + EnumChatFormatting.GOLD + arg1[1]);
                }
                ConfigHandler.writeStringConfig("misc", "FriendList", FriendList);
                break;
            }
            case "removeall": {
                FriendList = "";
                ConfigHandler.writeStringConfig("misc", "FriendList", FriendList);
                ChatUtils.addChatMessage("Removed all player from Friend List.");
                break;
            }
            case "list": {
                if (FriendList.replaceFirst(", ", "").equals("")) {
                    ChatUtils.addChatMessage(EnumChatFormatting.RED + "You dont have friend :(");
                    break;
                }
                ChatUtils.addChatMessage("Friend List: " + FriendList.replaceFirst(", ", ""));
                break;
            }
            default: {
                if (!FriendList.contains(arg1[0])) {
                    FriendList = FriendList + ", " + arg1[0];
                    ChatUtils.addChatMessage("Added " + EnumChatFormatting.GOLD + arg1[0] + EnumChatFormatting.WHITE + " to your Friend List.");
                } else {
                    FriendList = FriendList.replace(", " + arg1[0], "");
                    ChatUtils.addChatMessage("Removed " + EnumChatFormatting.GOLD + arg1[0] + EnumChatFormatting.WHITE + " from your Friend List.");
                }
                ConfigHandler.writeStringConfig("misc", "FriendList", FriendList);
            }
        }
    }
}

