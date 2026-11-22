package com.gab0521.throwingdiscbackport.config;

public class CustomDiscAttributes {
    private String material;
    private int meta;
    private int enchantability;
    private float damage;
    private float damageThrown;
    private float rechargeTime;
    private int durability;
    private float speed;
    private int maxBounces;
    private int floatingTime;
    private String craftingItem;
    private String modId;

    public CustomDiscAttributes() {}

    public CustomDiscAttributes (String material, int meta, int enchantability ,float damage, float damageThrown ,float rechargeTime, int durability, float speed, int maxBounces, int floatingTime, String craftingItem, String modId) {
        this.material = material;
        this.meta = meta;
        this.enchantability = enchantability;
        this.damage = damage;
        this.damageThrown = damageThrown;
        this.rechargeTime = rechargeTime;
        this.durability = durability;
        this.speed = speed;
        this.maxBounces = maxBounces;
        this.floatingTime = floatingTime;
        this.craftingItem = craftingItem;
    }

    public void setMaterial (String materialIn) {
        this.material = materialIn;
    }
    public String getMaterial () {
        return this.material;
    }
    public void setMeta (int metaIn) {
        this.meta = metaIn;
    }
    public int getMeta () {
        return this.meta;
    }
    public void setEnchantability (int enchantabilityIn) {
        this.enchantability = enchantabilityIn;
    }
    public int getEnchantability () {
        return this.enchantability;
    }
    public void setDamage (float damageIn) {
        this.damage = damageIn;
    }
    public double getDamage () {
        return this.damage;
    }
    public void setDamageThrown (float damageThrownIn) {
        this.damageThrown = damageThrownIn;
    }
    public double getDamageThrown () {
        return this.damageThrown;
    }
    public void setDurability (int durabilityIn) {
        this.durability = durabilityIn;
    }
    public int getDurability () {
        return this.durability;
    }
    public void setSpeed (float speedIn) {
        this.speed = speedIn;
    }
    public float getSpeed () {
        return this.speed;
    }
    public void setMaxBounces (int maxBouncesIn) {
        this.maxBounces = maxBouncesIn;
    }
    public int getMaxBounces () {
        return this.maxBounces;
    }
    public void setFloatingTime (int floatingTimeIn) {
        this.floatingTime = floatingTimeIn;
    }
    public int getFloatingTime () {
        return this.floatingTime;
    }
    public void setRechargeTime(float rechargeTimeIn) {this.rechargeTime = rechargeTimeIn;}
    public float getRechargeTime() {return this.rechargeTime;}
    public void setCraftingItem(String craftingItemIn) {this.craftingItem = craftingItemIn;}
    public String getCraftingItem() {return this.craftingItem;}
}