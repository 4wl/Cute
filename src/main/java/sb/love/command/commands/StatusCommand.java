package sb.love.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import sb.love.Cute;
import sb.love.command.Command;

// more complex command with multiple arguments
// TODO: list
public class StatusCommand extends Command {
    public StatusCommand() {
        super(new String[]{"status", "s", "st"}, "status <Player> <Friend | Enemy | Neutral>");
    }

    @Override
    public void exec(String args) throws Exception {
        String[] split = args.split(" ");
        if(split.length <= 0){
            sendErrorMessage(getSyntax(), false);
            return;
        }
        String name = split[0];
        if(name.equals("") || name.equals(" ")){
            sendErrorMessage(getSyntax(), false);
            return;
        }
        switch(split.length){
            case 1:
                sendClientMessage(name + ": " + getStatusString(name), false);
                return;
            case 2:
                switch(split[1].toLowerCase()){
                    case "":
                    case " ":
                        sendErrorMessage(getSyntax(), false);
                        return;
                    case "friend":
                    case "f":
                    case "fr":
                        switch(Cute.getInstance().playerStatus.getStatus(name)){
                            case 1:
                                sendErrorMessage(name + " is already a friend", false);
                                return;
                            case -1:
                                Cute.getInstance().playerStatus.delEnemy(name);
                                Cute.getInstance().playerStatus.addFriend(name);
                                sendClientMessage(name + " is an enemy, changing to " + ChatFormatting.GREEN + "friend", false);
                                return;
                            case 0:
                                Cute.getInstance().playerStatus.addFriend(name);
                                sendClientMessage("Set status of " + name + " to " + ChatFormatting.GREEN + "friend", false);
                                return;
                        }
                        break;
                    case "enemy":
                    case "e":
                    case "en":
                        switch(Cute.getInstance().playerStatus.getStatus(name)){
                            case -1:
                                sendErrorMessage(name + " is already an enemy", false);
                                return;
                            case 1:
                                Cute.getInstance().playerStatus.delFriend(name);
                                Cute.getInstance().playerStatus.addEnemy(name);
                                sendClientMessage(name + " is a friend, changing to " + ChatFormatting.RED + "enemy", false);
                                return;
                            case 0:
                                Cute.getInstance().playerStatus.addEnemy(name);
                                sendClientMessage("Set status of " + name + " to " + ChatFormatting.RED + "enemy", false);
                                return;
                        }
                        break;
                    case "neutral":
                    case "n":
                        switch(Cute.getInstance().playerStatus.getStatus(name)){
                            case -1:
                                Cute.getInstance().playerStatus.delEnemy(name);
                                sendClientMessage("Set status of " + name + " to neutral", false);
                                return;
                            case 1:
                                Cute.getInstance().playerStatus.delFriend(name);
                                sendClientMessage("Set status of " + name + " to neutral", false);
                                return;
                            case 0:
                                sendErrorMessage(name + "is already neutral", false);
                                return;
                        }
                        break;
                }
        }
        sendErrorMessage(getSyntax(), false);
    }

    private String getStatusString(String name){
        switch (Cute.getInstance().playerStatus.getStatus(name)){
            case 1:
                return ChatFormatting.GREEN + "Friend";
            case -1:
                return ChatFormatting.RED + "Enemy";
            case 0:
                return "Neutral";
        }
        return "";
    }
}
