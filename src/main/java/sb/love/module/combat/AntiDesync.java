package sb.love.module.combat;

import sb.love.event.PacketReceivedEvent;
import sb.love.module.Module;
import sb.love.util.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

// skidded from seppuku
public class AntiDesync extends Module {
    public AntiDesync() {
        super("AntiDesync", Category.COMBAT);
    }

    @SubscribeEvent
    public void onPacketReceive(PacketReceivedEvent event){
        if (event.getPacket() instanceof SPacketSoundEffect) {
            final SPacketSoundEffect packet = (SPacketSoundEffect) event.getPacket();
            if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                try {
                    for (Entity e : Wrapper.getWorld().loadedEntityList) {
                        if (e instanceof EntityEnderCrystal && e.getDistance(packet.getX(), packet.getY(), packet.getZ()) <= 6.0f) {
                                e.setDead();
                        }
                    }
                } catch(Exception e){e.printStackTrace();}
            }
        }
    }
}
