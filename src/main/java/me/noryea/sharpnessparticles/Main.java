package me.noryea.sharpnessparticles;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import java.util.Objects;

import static net.minecraft.particle.ParticleTypes.ENCHANTED_HIT;

@Environment(EnvType.CLIENT)
public class Main implements ClientModInitializer {

	public void onInitializeClient() {
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
		{
			//判断是否有锋利附魔
			boolean hasSharpness = false;
			if (player.getMainHandStack().hasEnchantments()) {
				NbtList enchantments = player.getMainHandStack().getEnchantments();
				for (int i = 0; i < enchantments.size(); i++) {
					NbtCompound enchant = enchantments.getCompound(i);
					if (Objects.equals(enchant.getString("id"),"minecraft:sharpness")) {
						hasSharpness = true;
						break;
					}
				}
			}

			//产生粒子
			if (!hasSharpness && !player.isSpectator()) {
				Vec3d position = entity.getPos().add(0, entity.getHeight()/2, 0);
				ParticleS2CPacket packet = new ParticleS2CPacket(ENCHANTED_HIT, false, position.x, position.y, position.z, 0, 0, 0, 0.5f, 20);
				if (MinecraftClient.getInstance().isInSingleplayer()) {
					ServerPlayerEntity serverPlayer = MinecraftClient.getInstance().getServer().getPlayerManager().getPlayerList().get(0);
					serverPlayer.networkHandler.sendPacket(packet);
				} else
					MinecraftClient.getInstance().getNetworkHandler().onParticle(packet);
			}
			return ActionResult.PASS;
		});
	}
}
