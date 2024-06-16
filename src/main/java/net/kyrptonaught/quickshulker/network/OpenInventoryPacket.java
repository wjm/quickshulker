package net.kyrptonaught.quickshulker.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kyrptonaught.quickshulker.QuickShulkerMod;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class OpenInventoryPacket implements CustomPayload {

    public static final PacketCodec<PacketByteBuf, OpenInventoryPacket> CODEC = PacketCodec.of((value, buf) -> buf.writeInt(0), buf -> new OpenInventoryPacket());

    public static final Id<OpenInventoryPacket> ID = new Id<>(Identifier.of(QuickShulkerMod.MOD_ID, "openinv"));

    public static void send(ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, new OpenInventoryPacket());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
