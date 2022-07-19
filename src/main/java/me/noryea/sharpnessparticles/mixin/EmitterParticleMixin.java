package me.noryea.sharpnessparticles.mixin;

import me.noryea.sharpnessparticles.config.SharpnessParticlesConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.EmitterParticle;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EmitterParticle.class)
@Environment(EnvType.CLIENT)
public abstract class EmitterParticleMixin extends NoRenderParticle {
    @Shadow private Entity entity;
    @Shadow private ParticleEffect parameters;
    @Shadow private int maxEmitterAge;
    @Shadow private int emitterAge;

    protected EmitterParticleMixin(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
    }

    @Override
    public void tick() {
        for (int i = 0; i < 16 + 8 * (SharpnessParticlesConfig.multiplier - 1); ++i) {
            double f;
            double e;
            double d = this.random.nextFloat() * 2.0f - 1.0f;
            if (d * d + (e = this.random.nextFloat() * 2.0f - 1.0f) * e + (f = this.random.nextFloat() * 2.0f - 1.0f) * f > 1.0) continue;
            double g = this.entity.offsetX(d / 4.0);
            double h = this.entity.getBodyY(0.5 + e / 4.0);
            double j = this.entity.offsetZ(f / 4.0);
            this.world.addParticle(this.parameters, false, g, h, j, d, e + 0.2, f);
        }
        ++this.emitterAge;
        if (this.emitterAge >= this.maxEmitterAge) {
            this.markDead();
        }
    }

}
