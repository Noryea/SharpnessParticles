package me.noryea.sharpnessparticles.mixin;

import me.noryea.sharpnessparticles.config.SharpnessParticlesConfig;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onEntityAnimation", at = @At("HEAD"), cancellable = true)
    public void onEntityAnimation(EntityAnimationS2CPacket packet, CallbackInfo ci) {
        if ((SharpnessParticlesConfig.showSharpnessParticles == SharpnessParticlesConfig.SharpnessParticlesMode.ALWAYS || SharpnessParticlesConfig.showSharpnessParticles == SharpnessParticlesConfig.SharpnessParticlesMode.NEVER) && packet.getAnimationId() == 5) {
            ci.cancel();
        }
    }

}
