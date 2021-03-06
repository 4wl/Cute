package sb.love.managers;

import com.mojang.realmsclient.gui.ChatFormatting;
import sb.love.module.Module;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import sb.love.module.client.*;
import sb.love.module.combat.*;
import sb.love.module.gui.*;
import sb.love.module.misc.*;
import sb.love.module.movement.LongJump;
import sb.love.module.movement.Speed;
import sb.love.module.render.BlockHighlight;
import sb.love.module.misc.Power;
import sb.love.module.render.HoleESP;
import sb.love.module.render.NoRender;

import javax.annotation.Nullable;
import java.util.*;

public class ModuleManager {
    private static HashMap<String, Module> modules;
    private static List<Module> enabledModules;

    public ModuleManager(){
        enabledModules = new ArrayList<>();
        modules = new HashMap<>();

		// register modules here
        new AutoTrap();
        new KillAura();
        new AntiDesync();
        new FastUse();
        new Step();
        new Chat();
        new SwingAnim();
        new HoleESP();
        new Power();
        new ArmorWarning();
        new PacketMine();
        new FreeOP();
        new OffhandCrystal();
        new AutoTotem();
        new Surround();
        new SoundEffects();
        new LongJump();
        new Speed();
        new NoRender();
        new DiscordRpcModule();
        new Players();
        new BlockHighlight();
        new Reach();
        new AutoCrystal();
        new InventoryPreview();
        new HoleTP();
        new OffhandMode();
        new PvpInfo();
        new Watermark();
        new ClickGuiModule();
        new Sprint();
        new Colors();
        new Settings();

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void register(Module module, String name){
        modules.put(name.toLowerCase(), module);
    }

    @Nullable
    public Module getModuleByName(String name){
        return modules.getOrDefault(name.toLowerCase(), null);
    }

    public ChatFormatting getEnabledColor(String module){
        return getModuleByName(module).isEnabled() ? ChatFormatting.GREEN : ChatFormatting.RED;
    }

    public HashMap<String, Module> getModules(){
        return modules;
    }

    public List<Module> getEnabledModules(){
        return enabledModules;
    }

    public List<Module> getModulesInCategory(Module.Category category){
        List<Module> list = new ArrayList<>();
        modules.forEach((name, module) ->{
            if(module.getCategory().equals(category))
                list.add(module);
        });
        return list;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event){
        for(Module m : enabledModules){
            m.onUpdate();
        }
    }

    @SubscribeEvent
    public void onRenderGameOverLay(RenderGameOverlayEvent event){
        if(event instanceof RenderGameOverlayEvent.Pre) {
            for(Module m : enabledModules){
                m.preRender2D();
            }
        } else if(event instanceof RenderGameOverlayEvent.Post){
            if(event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR)){
                for(Module m : enabledModules){
                    m.postRender2D();
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event){
        for(Module m : enabledModules){
            m.onRender3D();
        }
    }

    @SubscribeEvent
    public void keyPressed(InputEvent.KeyInputEvent event){
        if(Keyboard.getEventKey() != 0){
            modules.forEach((name, m) ->{
                if(m.getBind() == Keyboard.getEventKey()) {
                    switch (m.getBindBehaviour()) {
                        case TOGGLE:
                            if (Keyboard.getEventKeyState()) m.toggle();
                            break;
                        case HOLD:
                            m.toggle();
                            break;
                    }
                }
            });
        }
    }
}
