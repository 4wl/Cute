package sb.love.mixin.mixins;

import sb.love.Cute;
import sb.love.module.combat.Reach;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
@SideOnly(Side.CLIENT)
public class MixinPlayerControllerMP {
	// Reach
    @Inject(method = "getBlockReachDistance", at = @At("HEAD"), cancellable = true)
    private void reach(CallbackInfoReturnable<Float> cir){
        Reach reach = (Reach) Cute.getInstance().moduleManager.getModuleByName("Reach");
        if(reach.isEnabled()){
            cir.cancel();
            cir.setReturnValue(reach.distance.getValue());
        }
    }
}
