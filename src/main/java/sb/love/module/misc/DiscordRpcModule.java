package sb.love.module.misc;

import joptsimple.internal.Strings;
import sb.love.Cute;
import sb.love.util.DiscordRpc;
import sb.love.module.Module;
import sb.love.module.combat.AutoCrystal;
import sb.love.util.Wrapper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class DiscordRpcModule extends Module {
    public DiscordRpcModule() {
        super("DiscordRPC", Category.MISC);
    }

    private int ticksSinceLastInput = 0;

    @Override
    public void onUpdate(){
        DiscordRpc rpc = DiscordRpc.INSTANCE;
        ticksSinceLastInput++;
        if(ticksSinceLastInput / 20 > 120){
            rpc.state = "AFK";
            return;
        }
        AutoCrystal autoCrystal = (AutoCrystal) Cute.getInstance().moduleManager.getModuleByName("AutoCrystal");
        if(autoCrystal.target != null){
            rpc.state = "Fighting " + autoCrystal.target.getName();
            return;
        }
        if(Wrapper.mc.isIntegratedServerRunning()){
            rpc.state = "Playing Singleplayer";
            return;
        }
        if(Wrapper.mc.getCurrentServerData() != null && !Strings.isNullOrEmpty(Wrapper.mc.getCurrentServerData().serverIP)){
            rpc.state = "Playing " + Wrapper.mc.getCurrentServerData().serverIP;
            return;
        }
        rpc.state = "Main Menu";
    }

    @SubscribeEvent
    public void onInput(InputEvent event){
        ticksSinceLastInput = 0;
    }

    @Override
    protected void onEnable() {
        DiscordRpc.INSTANCE.start();
    }

    @Override
    protected void onDisable() {
        DiscordRpc.INSTANCE.stop();
    }
}
