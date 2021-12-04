package sb.love.module.misc;

import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sb.love.event.PacketReceivedEvent;
import sb.love.module.Module;
import sb.love.setting.Setting;

import static sb.love.util.Wrapper.mc;

public class Power extends Module {
    public Power() {
        super("Power", Category.RENDER);
    }

    private Setting<Boolean> power = register("Power", true);

    private float previousSetting = 1.0f;

    @Override
    public void onEnable() {
        this.previousSetting = mc.gameSettings.gammaSetting;
    }


    @Override
    public void onUpdate(){
       if (power.getValue()){
           mc.player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH));
       }
    }

    @Override
    public void onDisable() {
        if (power.getValue()) {
          mc.player.removePotionEffect(MobEffects.STRENGTH);
        }
      mc.gameSettings.gammaSetting = this.previousSetting;
    }

    @SubscribeEvent
    public void onPacketReceive(PacketReceivedEvent event) {
        if (event.getPacket() instanceof SPacketEntityEffect && power.getValue().booleanValue()) {
            SPacketEntityEffect packet = (SPacketEntityEffect) event.getPacket();
            if (mc.player != null && packet.getEntityId() == mc.player.getEntityId() && (packet.getEffectId() == 9 || packet.getEffectId() == 15)) {
                event.setCanceled(true);
            }
        }
    }

}
