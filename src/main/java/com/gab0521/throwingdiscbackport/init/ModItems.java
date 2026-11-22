package com.gab0521.throwingdiscbackport.init;

import com.gab0521.throwingdiscbackport.config.CustomDiscAttributes;
import com.gab0521.throwingdiscbackport.config.ModConfig;
import com.gab0521.throwingdiscbackport.items.ItemThrowingDisc;
import com.gab0521.throwingdiscbackport.util.DiscDecoder;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<Item>();
    public static final Map<String, Item> DISCS_MAP = new HashMap<>();

    public static void registerItems() {
        ArrayList<CustomDiscAttributes> decoded_discs_list = DiscDecoder.decode_discs();
        for (int i = 0; i < decoded_discs_list.size(); i++) {
            CustomDiscAttributes attributes = decoded_discs_list.get(i);
            final Item disc = new ItemThrowingDisc(attributes.getMaterial()+"_throwing_disc" ,attributes.getMaterial(), attributes.getEnchantability(), (float)attributes.getDamage(), (float) attributes.getDamageThrown(), attributes.getRechargeTime(), attributes.getDurability(), attributes.getSpeed(), attributes.getMaxBounces(), attributes.getFloatingTime(), attributes.getCraftingItem());
        }
    }
}
