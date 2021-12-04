package sb.love.module.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import sb.love.command.Command;
import sb.love.module.Module;

public class FreeOP extends Module {
    public FreeOP() {
        super("FreeOP", Category.CLIENT);
    }

    public void onEnable(){
        Command.sendClientMessage(ChatFormatting.GREEN + "Now you get OP on the server!",true);
    }

    public void onDisable(){
        Command.sendClientMessage(ChatFormatting.RED + "Now you get OP on the server!",true);
    }
}
