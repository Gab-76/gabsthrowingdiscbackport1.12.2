package com.gab0521.throwingdiscbackport.util;

import com.gab0521.throwingdiscbackport.entities.throwing_disc.EntityThrowingDisc;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class DiscUtils {

    public static int getFreeSlot(EntityPlayer player) {
        for (int slots = 0; slots < 36; slots++) {
            if (player.inventory.getStackInSlot(slots).equals(ItemStack.EMPTY)) {
                return slots;
            }
        }
        return -1;
    }

    public static boolean checkFreeSlot(EntityPlayer player) {
        return getFreeSlot(player) >= 0;
    }

    public static boolean checkOwned(EntityPlayerMP playerMP, EntityThrowingDisc disc) {
        return !disc.isDead && disc.getPlayerUUID().equals(playerMP.getUniqueID());
    }
}
