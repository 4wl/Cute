package sb.love;

import sb.love.gui.ClickGUI;
import sb.love.managers.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sb.love.util.*;

import java.io.IOException;

@Mod(modid = Cute.MODID, name = Cute.MODNAME, version = Cute.MODVER)
public class Cute {
    public static final String MODID = "cute";
    public static final String MODNAME = "Cute";
    public static final String MODVER = "0.1";

    public Logger log = LogManager.getLogger(MODNAME);

    private static Cute instance;

    public ModuleManager moduleManager;
    public CommandManager commandManager;
    public RainbowManager rainbow;
    public ClientSettings clientSettings;
    public SettingManager settingManager;
    public HideManager hideManager;
    public PlayerStatus playerStatus;
    public HudComponentManager hudComponentManager;
    public ClickGUI clickGUI;
    public RotationManager rotationManager;

    public Cute() {
        instance = this;
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        new DiscordRpc();
        playerStatus = new PlayerStatus();
        rainbow = new RainbowManager();
        hideManager = new HideManager();
        settingManager = new SettingManager();
        moduleManager = new ModuleManager();
        clientSettings = new ClientSettings();
        commandManager = new CommandManager();
        hudComponentManager = new HudComponentManager();
        clickGUI = new ClickGUI();
        rotationManager = new RotationManager();
        new RenderUtils();
        new EntityUtils();
        new KillEventHelper();
        Config config = new Config();

        try {
            config.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(MODNAME + " shutdown hook"){
            @Override
            public void run(){
                try {
                    config.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        log.info(toString() + " initialized");
    }

	// used to access non-static fields and methods
    public static Cute getInstance(){
        return instance;
    }

    @Override
    public String toString(){
        return MODNAME + " " + MODVER;
    }
}
