package com.gab0521.throwingdiscbackport.items;

import com.gab0521.throwingdiscbackport.ThrowingDiscBackport;
import com.gab0521.throwingdiscbackport.config.ModConfig;
import com.gab0521.throwingdiscbackport.entities.throwing_disc.EntityThrowingDisc;
import com.gab0521.throwingdiscbackport.init.ModItems;
import com.gab0521.throwingdiscbackport.util.IHasModel;
import com.google.common.collect.Multimap;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemThrowingDisc extends Item implements IHasModel {
    private final float attackDamage;
    private final float attackSpeed;
    private final int enchantability;
    private final double damageThrown;
    private final float speed;
    private final int maxBounces;
    private final int floatingTime;
    private final String material;
    private final String fullitemname;

    public ItemThrowingDisc(String name, String material, int enchantability, float damage, float damageThrown, float rechargeTime ,int durability, float speed, int maxBounces, int floatingTime, String fullitemname)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        this.maxStackSize = 1;
        this.setMaxDamage(durability);
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.attackDamage = damage;
        this.attackSpeed = rechargeTime;
        this.enchantability = enchantability;
        this.damageThrown = (double)damageThrown;
        this.speed = speed;
        this.maxBounces = maxBounces;
        this.floatingTime = floatingTime;
        this.material = material;
        this.fullitemname = fullitemname;
        ModItems.ITEMS.add(this);
        ModItems.DISCS_MAP.put(this.material.toUpperCase()+"_THROWING_DISC", this);
    }

    public float getAttackDamage()
    {
        return this.attackDamage;
    }

    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        Material material = state.getMaterial();
        return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD ? 1.0F : 1.5F;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (!playerIn.capabilities.isCreativeMode)
        {
            itemstack.damageItem(1, playerIn);
        }

        Vec3d lookvec = playerIn.getLookVec();

        worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote)
        {
            ItemStack discStack = itemstack.copy();
            EntityThrowingDisc entityThrowingDisc = new EntityThrowingDisc(worldIn, playerIn);

            entityThrowingDisc.setDamage(this.damageThrown);
            entityThrowingDisc.setBane_damage(this.damageThrown);
            entityThrowingDisc.setSmite_damage(this.damageThrown);
            entityThrowingDisc.setVariant(this.material);
            entityThrowingDisc.setFlyingTicks(this.floatingTime);
            entityThrowingDisc.setMaxBounces(this.maxBounces);
            entityThrowingDisc.setSpeed(this.speed);
            entityThrowingDisc.setPlayerUUID(playerIn.getUniqueID());

            entityThrowingDisc.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, this.speed, 0.5F);
            entityThrowingDisc.setSourceItem(this);
            entityThrowingDisc.setSourceItemStack(discStack);
            worldIn.spawnEntity(entityThrowingDisc);
        }

        if (!playerIn.capabilities.isCreativeMode)
        {
            itemstack.shrink(1);
        }

        playerIn.addStat(StatList.getObjectUseStats(this));
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.SHARPNESS || enchantment == Enchantments.SMITE || enchantment == Enchantments.BANE_OF_ARTHROPODS || enchantment == Enchantments.FLAME || enchantment == Enchantments.PUNCH || enchantment == Enchantments.UNBREAKING) {
            return true;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        stack.damageItem(2, attacker);
        return true;
    }

    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        if ((double)state.getBlockHardness(worldIn, pos) != 0.0D)
        {
            stack.damageItem(2, entityLiving);
        }

        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return false;
    }

    public int getItemEnchantability()
    {
        return this.enchantability;
    }

    public double getDamageThrown() {
        return this.damageThrown;
    }

    public String getMaterial() {
        return this.material;
    }

    public int getMaxBounces() {
        return this.maxBounces;
    }

    public int getFloatingTime() {
        return this.floatingTime;
    }

    public float getSpeed() {
        return this.speed;
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        if (repair.getItem() == Item.getByNameOrId(this.fullitemname) || repair.getItem() == this) {
            return true;
        }
        return super.getIsRepairable(toRepair, repair);
    }

    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", (double)this.attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)this.attackSpeed, 0));
        }

        return multimap;
    }


    @Override
    public void registerModels() {
        ThrowingDiscBackport.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
