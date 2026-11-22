package com.gab0521.throwingdiscbackport.events;

import com.gab0521.throwingdiscbackport.items.ItemThrowingDisc;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

@Mod.EventBusSubscriber(Side.CLIENT)
public class DiscTooltip {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        if (stack.getItem() instanceof ItemThrowingDisc) {
            List<String> tooltip = event.getToolTip();
            ItemThrowingDisc disc = (ItemThrowingDisc) stack.getItem();
            double throwndamage = (double)disc.getDamageThrown();
            double flyingspeed = (double)(disc.getFloatingTime()/20);

            int sharpness = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack);
            if (sharpness > 0)
            {
                throwndamage = (throwndamage + (double)sharpness * 0.5D + 0.5D);
            }

            String formattedFlyingTime = (flyingspeed % 1.0F == 0.0F)
                    ? String.format("%.0f", flyingspeed)
                    : String.format("%.1f", flyingspeed);

            String formattedDamage = (throwndamage % 1.0F == 0.0F)
                    ? String.format("%.0f", throwndamage)
                    : String.format("%.1f", throwndamage);

            tooltip.add("");
            tooltip.add(I18n.format("item.tooltip.whenthrown.name"));
            tooltip.add(" " + formattedFlyingTime +I18n.format("item.tooltip.throwntime.name"));
            tooltip.add(" " + formattedDamage + " " + I18n.format("item.tooltip.throwndamage.name"));
        }
    }
}
