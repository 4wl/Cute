package sb.love.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import joptsimple.internal.Strings;
import sb.love.Cute;
import sb.love.command.Command;
import sb.love.module.Module;

// simple command with only one argument, in this case the argument can contain spaces
public class ToggleCommand extends Command {
    public ToggleCommand() {
        super(new String[]{"toggle", "t"}, "toggle <Module>");
    }

    @Override
    public void exec(String args) throws Exception {
        if(Strings.isNullOrEmpty(args)){
            sendErrorMessage("Module expected", false);
            return;
        }

        Module m = Cute.getInstance().moduleManager.getModuleByName(args);
        if(m == null){
            sendErrorMessage("Unknown module: " + args, false);
            return;
        }

        sendClientMessage(m.getName() + (m.toggle() ? ChatFormatting.GREEN + " enabled" : ChatFormatting.RED + " disabled"), false);
    }
}
