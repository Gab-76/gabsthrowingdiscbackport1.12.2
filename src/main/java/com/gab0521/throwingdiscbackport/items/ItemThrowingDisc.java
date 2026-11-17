package com.gab0521.throwingdiscbackport.items;

import com.gab0521.throwingdiscbackport.ThrowingDiscBackport;
import com.gab0521.throwingdiscbackport.entities.throwing_disc.diamond.EntityDiamondThrowingDisc;
import com.gab0521.throwingdiscbackport.entities.throwing_disc.gold.EntityGoldThrowingDisc;
import com.gab0521.throwingdiscbackport.entities.throwing_disc.iron.EntityIronThrowingDisc;
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
    private final Item.ToolMaterial material;
    private final float attackSpeed;

    public ItemThrowingDisc(Item.ToolMaterial material, String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        this.material = material;
        this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses());
        this.setCreativeTab(CreativeTabs.COMBAT);
        this.attackDamage = 1.5F;
        this.attackSpeed = -3.0F;
        ModItems.ITEMS.add(this);
    }

    public float getAttackDamage()
    {
        return this.material.getAttackDamage();
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
        playerIn.getCooldownTracker().setCooldown(this, 999999999);

        if (!worldIn.isRemote)
        {
            int var = 0;

            if (this.material == ToolMaterial.GOLD) {
                var = 1;
            }
            if (this.material == ToolMaterial.DIAMOND) {
                var = 2;
            }
            ItemStack discStack = itemstack;
            switch (var) {
                case 1:
                    EntityGoldThrowingDisc entitydiscgold = new EntityGoldThrowingDisc(worldIn, playerIn);
                    entitydiscgold.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 0.5F, 0.5F);
                    entitydiscgold.setSourceItem(this);
                    entitydiscgold.setSourceItemStack(discStack);
                    worldIn.spawnEntity(entitydiscgold);
                    break;
                case 2:
                    EntityDiamondThrowingDisc entitydiscdiamond = new EntityDiamondThrowingDisc(worldIn, playerIn);
                    entitydiscdiamond.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 0.5F, 0.5F);
                    entitydiscdiamond.setSourceItem(this);
                    entitydiscdiamond.setSourceItemStack(discStack);
                    worldIn.spawnEntity(entitydiscdiamond);
                    break;
                default:
                    EntityIronThrowingDisc entitydisciron = new EntityIronThrowingDisc(worldIn, playerIn);
                    entitydisciron.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 0.5F, 0.5F);
                    entitydisciron.setSourceItem(this);
                    entitydisciron.setSourceItemStack(discStack);
                    worldIn.spawnEntity(entitydisciron);
                    break;
            }
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
        return this.material.getEnchantability();
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        ItemStack mat = this.material.getRepairItemStack();
        if (!mat.isEmpty() && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) return true;
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

    public ToolMaterial getMaterial() {
        return this.material;
    }

    @Override
    public void registerModels() {
        ThrowingDiscBackport.proxy.registerItemRenderer(this, 0, "inventory");
    }
}
