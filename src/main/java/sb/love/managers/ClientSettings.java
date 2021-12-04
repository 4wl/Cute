package sb.love.managers;

import sb.love.Cute;
import sb.love.module.client.Colors;
import sb.love.module.client.Settings;

import java.awt.*;

// just a wrapper to get values from the client modules
public class ClientSettings {

    public Colors colors;
    public Settings settings;

    public ClientSettings(){
        colors = (Colors) Cute.getInstance().moduleManager.getModuleByName("Colors");
        settings = (Settings) Cute.getInstance().moduleManager.getModuleByName("Settings");
    }

    public Color getColorr(int alpha){
        if(colors.rainbow.getValue()) return Cute.getInstance().rainbow.getColor(alpha);
        return new Color(colors.red.getValue(), colors.green.getValue(), colors.blue.getValue(), alpha);
    }

    public int getColor(){
        if(colors.rainbow.getValue()) return Cute.getInstance().rainbow.getHex();
        return new Color(colors.red.getValue(), colors.green.getValue(), colors.blue.getValue()).getRGB();
    }

    public int getColor(int alpha){
        if(colors.rainbow.getValue()) return Cute.getInstance().rainbow.getColor(alpha).getRGB();
        return new Color(colors.red.getValue(), colors.green.getValue(), colors.blue.getValue(), alpha).getRGB();
    }

    public String getPrefix(){
        return settings.commandPrefix.getValue();
    }
}
