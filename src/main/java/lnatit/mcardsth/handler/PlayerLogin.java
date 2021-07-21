package lnatit.mcardsth.handler;

import deeplake.idlframework.idlnbtutils.*;
import lnatit.mcardsth.item.ItemReg;
import lnatit.mcardsth.network.BarRenderPacket;
import lnatit.mcardsth.network.NetworkManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static lnatit.mcardsth.MineCardsTouhou.MOD_ID;

/**
 * when player logged in for the first time, it will give him an initial item.
 */
@Mod.EventBusSubscriber(modid = MOD_ID)
public class PlayerLogin
{
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        PlayerEntity player = event.getPlayer();
        if (player instanceof ServerPlayerEntity)
        {
            if (IDLNBTUtils.GetBoolean(player, IDLNBTConst.FIRST_LOGIN, true))
            {
                player.inventory.addItemStackToInventory(new ItemStack(ItemReg.BLANK.get()));
                IDLNBTUtils.SetBoolean(player, IDLNBTConst.FIRST_LOGIN, false);
            }
        }
    }
}
