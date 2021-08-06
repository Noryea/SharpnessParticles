package me.noryea.sharpnessparticles;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class Main implements ClientModInitializer {
	public void onInitializeClient() {
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
		{
			boolean hasSharpness = false;
			/* 
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
			*/
			if (!hasSharpness && !player.isSpectator()) {
				//产生粒子
				EntityAnimationS2CPacket packet = new EntityAnimationS2CPacket(entity, EntityAnimationS2CPacket.ENCHANTED_HIT);
				if (MinecraftClient.getInstance().isInSingleplayer()) {
					ServerPlayerEntity serverPlayer = MinecraftClient.getInstance().getServer().getPlayerManager().getPlayerList().get(0);
					serverPlayer.networkHandler.sendPacket(packet);
				} else
					MinecraftClient.getInstance().getNetworkHandler().onEntityAnimation(packet);
			}
			return ActionResult.PASS;
		});
	}
}
