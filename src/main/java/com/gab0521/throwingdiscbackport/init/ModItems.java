package com.gab0521.throwingdiscbackport.init;

import com.gab0521.throwingdiscbackport.items.ItemThrowingDisc;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Item IRON_THROWING_DISC = new ItemThrowingDisc(Item.ToolMaterial.IRON, "iron_throwing_disc");
    public static final Item GOLD_THROWING_DISC = new ItemThrowingDisc(Item.ToolMaterial.GOLD, "gold_throwing_disc");
    public static final Item DIAMOND_THROWING_DISC = new ItemThrowingDisc(Item.ToolMaterial.DIAMOND, "diamond_throwing_disc");

}
