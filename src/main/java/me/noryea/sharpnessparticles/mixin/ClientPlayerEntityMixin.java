package me.noryea.sharpnessparticles.mixin;

import me.noryea.sharpnessparticles.config.SharpnessParticlesConfig;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @Inject(method = "addEnchantedHitParticles", at = @At("HEAD"), cancellable = true)
    public void noEnchantedHitParticles(Entity target, CallbackInfo ci) {
        if (SharpnessParticlesConfig.showSharpnessParticles == SharpnessParticlesConfig.SharpnessParticlesMode.ALWAYS || SharpnessParticlesConfig.showSharpnessParticles == SharpnessParticlesConfig.SharpnessParticlesMode.NEVER) {
            ci.cancel();
        }
    }

}
