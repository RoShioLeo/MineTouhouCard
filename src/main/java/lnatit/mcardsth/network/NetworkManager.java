package lnatit.mcardsth.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.commons.codec.BinaryDecoder;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

public class NetworkManager
{
    private static final String PROTOCOL_VERSION = "1.0.0";
    private static int index = 0;

    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerPacket()
    {
        INSTANCE.registerMessage(index++,
                NBTPacket.class,
                NBTPacket::encode,
                NBTPacket::decode,
                NBTPacket::handle
        );

        INSTANCE.registerMessage(index++,
                CardActivationPacket.class,
                CardActivationPacket::encode,
                CardActivationPacket::decode,
                CardActivationPacket::handle
        );
    }

    public static void playerSendToServer(IPacket packet)
    {
        INSTANCE.sendToServer(packet);
    }

    public static void serverSendToPlayer(IPacket packet, ServerPlayerEntity player)
    {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void serverSendToAllPlayer(IPacket packet)
    {
        INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
    }
}