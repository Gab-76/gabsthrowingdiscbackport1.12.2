package com.gab0521.throwingdiscbackport.util;

import com.gab0521.throwingdiscbackport.config.CustomDiscAttributes;
import com.gab0521.throwingdiscbackport.config.ModConfig;

import java.util.ArrayList;

public class DiscDecoder {

    public static ArrayList<CustomDiscAttributes> decoded_discs = new ArrayList<CustomDiscAttributes>() {};
    public static int discs_to_decode = ModConfig.discs.length;
    public static ArrayList<CustomDiscAttributes> decode_discs() {
        for (int i = 0; i < discs_to_decode; i++) {
            CustomDiscAttributes newdisc = new CustomDiscAttributes();
            String stringtodecode = ModConfig.discs[i];
            String[] decoded_disc = stringtodecode.split(":");
            if (decoded_disc.length < 11) {
                System.out.println("The "+i+"th disc you've configured has less arguments than needed so it won't load!");
            }
            else {
                String itemfullid = decoded_disc[0] + ":" + decoded_disc[1];
                if (!itemfullid.equals("minecraft:diamond") && !itemfullid.equals("minecraft:iron_ingot") && !itemfullid.equals("minecraft:gold_ingot")) {
                    newdisc.setMaterial(decoded_disc[0]+"_"+decoded_disc[1]);
                }
                else {
                    if (itemfullid.equals("minecraft:diamond")) {
                        newdisc.setMaterial(decoded_disc[1]);
                    }
                    else {
                        String[] materialwithout_ = decoded_disc[1].split("_");
                        newdisc.setMaterial(materialwithout_[0]);
                    }
                }
                newdisc.setMeta(Integer.parseInt(decoded_disc[2]));
                newdisc.setEnchantability(Integer.parseInt(decoded_disc[3]));
                newdisc.setDamage(Float.parseFloat(decoded_disc[4]));
                newdisc.setDamageThrown(Float.parseFloat(decoded_disc[5]));
                newdisc.setRechargeTime(Float.parseFloat(decoded_disc[6]));
                newdisc.setDurability(Integer.parseInt(decoded_disc[7]));
                newdisc.setSpeed(Float.parseFloat(decoded_disc[8]));
                newdisc.setMaxBounces(Integer.parseInt(decoded_disc[9]));
                newdisc.setFloatingTime(Integer.parseInt(decoded_disc[10]));
                newdisc.setCraftingItem(itemfullid);
                decoded_discs.add(newdisc);
            }
        }
        return decoded_discs;
    }
}
