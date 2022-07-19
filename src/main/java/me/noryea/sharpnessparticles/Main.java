package me.noryea.sharpnessparticles;

import me.noryea.sharpnessparticles.config.SharpnessParticlesConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;

@Environment(EnvType.CLIENT)
public class Main implements ClientModInitializer {

	public static DefaultParticleType MOD_ENCHANTED_HIT;

	@Override
	public void onInitializeClient() {
		SharpnessParticlesConfig.init("sharpness-particles", SharpnessParticlesConfig.class);

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
		{
			if (SharpnessParticlesConfig.showSharpnessParticles == SharpnessParticlesConfig.SharpnessParticlesMode.ALWAYS && !player.isSpectator()) {
				MinecraftClient.getInstance().particleManager.addEmitter(entity, ParticleTypes.ENCHANTED_HIT);	//产生粒子
			}
			return ActionResult.PASS;
		});
	}

}
