package com.gab0521.throwingdiscbackport.entities.throwing_disc;

import com.gab0521.throwingdiscbackport.util.DiscUtils;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.UUID;

public class EntityThrowingDisc extends EntityThrowable implements IProjectile {
    private static final DataParameter<String> VARIANT = EntityDataManager.<String>createKey(EntityThrowingDisc.class, DataSerializers.STRING);
    private double damage;
    private double smite_damage;
    private double bane_damage;
    private int xTile;
    private int yTile;
    private int zTile;
    private float timesbounced;
    private int flyingticks;
    private boolean isReturning;
    private boolean isFire;
    private Item sourceItem;
    private ItemStack sourceItemStack;
    private int knockbackStrength;
    private boolean hasSharpness;
    private int maxBounces;
    private boolean canHit;
    private float speed;
    private double dispX;
    private double dispY;
    private double dispZ;
    private UUID playerUUID;


    public EntityThrowingDisc(World worldIn) {
        super(worldIn);
        this.setSize(0.9F, 0.2F);

    }
    public EntityThrowingDisc(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
        this.setSize(0.9F, 0.2F);
    }

    public EntityThrowingDisc(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
        this.setSize(0.9F, 0.15F);
    }

    @Override
    protected void entityInit() {

        this.setSize(0.9F, 0.2F);
        this.dataManager.register(VARIANT, "default");
        this.canHit = true;
        setTimesBounced(0);
        setFlyingTicks(20);
        this.isReturning = false;
        this.isFire = false;
        this.hasSharpness = false;
        this.setDamage(1.5F);
        this.setSmite_damage(1.5F);
        this.setBane_damage(1.5F);
        this.setSpeed(0.5F);
        this.setDispX(0);
        this.setDispY(0);
        this.setDispZ(0);
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        compound.setInteger("xTile", this.xTile);
        compound.setInteger("yTile", this.yTile);
        compound.setInteger("zTile", this.zTile);
        compound.setDouble("damage", this.damage);
        compound.setFloat("timesBounced", this.timesbounced);
        compound.setFloat("speed", this.speed);
        compound.setInteger("maxBounces", this.maxBounces);
        compound.setInteger("flyingTicks", this.flyingticks);
        compound.setBoolean("isReturning", this.isReturning);
        compound.setBoolean("isFire", this.isFire);
        compound.setBoolean("canHit", this.canHit);
        compound.setDouble("dispX", this.dispX);
        compound.setDouble("dispY", this.dispY);
        compound.setDouble("dispZ", this.dispZ);
        compound.setUniqueId("playerUUID", this.playerUUID);
        if (this.sourceItemStack != null && !this.sourceItemStack.isEmpty()) {
            compound.setTag("itemStack", sourceItemStack.writeToNBT(new NBTTagCompound()));
        }
        compound.setString("Variant", this.getVariant());
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        this.xTile = compound.getInteger("xTile");
        this.yTile = compound.getInteger("yTile");
        this.zTile = compound.getInteger("zTile");
        this.timesbounced = compound.getInteger("timesBounced");
        this.flyingticks = compound.getInteger("flyingTicks");
        this.isReturning = compound.getBoolean("isReturning");
        this.isFire = compound.getBoolean("isFire");
        this.canHit = compound.getBoolean("canHit");
        this.speed = compound.getFloat("speed");
        this.setMaxBounces(compound.getInteger("maxBounces"));
        this.setVariant(compound.getString("Variant"));
        this.setDispX(compound.getDouble("dispX"));
        this.setDispY(compound.getDouble("dispY"));
        this.setDispZ(compound.getDouble("dispZ"));


        if (compound.hasKey("playerUUID")) {
            this.playerUUID = compound.getUniqueId("playerUUID");
        }

        if (compound.hasKey("itemStack")) {
            this.sourceItemStack = new ItemStack(compound.getCompoundTag("itemStack"));
        }

        if (compound.hasKey("damage", 99))
        {
            this.damage = compound.getDouble("damage");
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            if (this.canHit) {
                if (thrower != null) {
                if (result.entityHit != thrower && result.entityHit instanceof EntityLivingBase) {
                    if (this.hasSharpness) {
                        if (((EntityLivingBase) result.entityHit).isEntityUndead()) {
                            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.thrower), (float) this.damage);
                        }
                        else if (result.entityHit instanceof EntitySpider || result.entityHit instanceof EntityCaveSpider || result.entityHit instanceof EntitySilverfish || result.entityHit instanceof EntityEndermite) {
                            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.thrower), (float) this.damage);
                        }
                        else {
                            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.thrower), (float) this.damage);
                        }
                    }
                    else {
                        if (((EntityLivingBase) result.entityHit).isEntityUndead()) {
                            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.thrower), (float) this.smite_damage);
                        }
                        else if (result.entityHit instanceof EntitySpider || result.entityHit instanceof EntityCaveSpider || result.entityHit instanceof EntitySilverfish || result.entityHit instanceof EntityEndermite) {
                            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.thrower), (float) this.bane_damage);
                        }
                        else {
                            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.thrower), (float) this.damage);
                        }
                    }
                    EnchantmentHelper.applyThornEnchantments((EntityLivingBase) result.entityHit, this.thrower);
                    if (this.knockbackStrength > 0)
                    {
                        float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                        if (f1 > 0.0F)
                        {
                            result.entityHit.addVelocity(this.motionX * (double)this.knockbackStrength * 0.6000000238418579D / (double)f1, 0.1D, this.motionZ * (double)this.knockbackStrength * 0.6000000238418579D / (double)f1);
                        }
                    }
                    if (this.isFire) {
                        result.entityHit.setFire(100);
                    }
                    this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ARROW_HIT, SoundCategory.NEUTRAL, 0.5F, 0.4F);
                }
                else if (result.entityHit == thrower && thrower instanceof EntityPlayer) {
                    if (DiscUtils.checkFreeSlot((EntityPlayer)thrower) && !((EntityPlayer)thrower).isCreative()) {
                        this.world.playSound((EntityPlayer)null, thrower.posX, thrower.posY, thrower.posZ, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.NEUTRAL, 0.5F, 0.4F);
                        if (DiscUtils.getFreeSlot((EntityPlayer)thrower) >= 0) {
                            ((EntityPlayer) thrower).inventory.setInventorySlotContents(DiscUtils.getFreeSlot((EntityPlayer)thrower), this.sourceItemStack.copy());
                            this.setSourceItemStack(ItemStack.EMPTY);
                        }
                        this.setDead();
                    }
                    else if (((EntityPlayer)thrower).isCreative()) {
                        this.world.playSound((EntityPlayer)null, thrower.posX, thrower.posY, thrower.posZ, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.NEUTRAL, 0.5F, 0.4F);
                        this.setDead();
                    }
                }
                else if (result.entityHit instanceof EntityEnderCrystal) {
                    result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.thrower), (float)this.damage);
                    this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ARROW_HIT, SoundCategory.NEUTRAL, 0.5F, 0.4F);
                }
            }
            else {
                if (result.entityHit != thrower && result.entityHit instanceof EntityLivingBase) {
                    if (this.hasSharpness) {
                        if (((EntityLivingBase) result.entityHit).isEntityUndead()) {
                            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, null), (float) this.damage);
                        }
                        else if (result.entityHit instanceof EntitySpider || result.entityHit instanceof EntityCaveSpider || result.entityHit instanceof EntitySilverfish || result.entityHit instanceof EntityEndermite) {
                            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, null), (float) this.damage);
                        }
                        else {
                            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, null), (float) this.damage);
                        }
                    }
                    else {
                        if (((EntityLivingBase) result.entityHit).isEntityUndead()) {
                            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, null), (float) this.smite_damage);
                        }
                        else if (result.entityHit instanceof EntitySpider || result.entityHit instanceof EntityCaveSpider || result.entityHit instanceof EntitySilverfish || result.entityHit instanceof EntityEndermite) {
                            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, null), (float) this.bane_damage);
                        }
                        else {
                            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, null), (float) this.damage);
                        }
                    }
                    if (this.knockbackStrength > 0)
                    {
                        float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

                        if (f1 > 0.0F)
                        {
                            result.entityHit.addVelocity(this.motionX * (double)this.knockbackStrength * 0.6000000238418579D / (double)f1, 0.1D, this.motionZ * (double)this.knockbackStrength * 0.6000000238418579D / (double)f1);
                        }
                    }
                    if (this.isFire) {
                        result.entityHit.setFire(100);
                    }
                    this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ARROW_HIT, SoundCategory.NEUTRAL, 0.5F, 0.4F);
                }
                }
                }
            else if (result.entityHit == thrower && thrower instanceof EntityPlayer && ticksExisted > 10) {
                    if (DiscUtils.checkFreeSlot((EntityPlayer)thrower) && !((EntityPlayer)thrower).isCreative()) {
                        this.world.playSound((EntityPlayer)null, thrower.posX, thrower.posY, thrower.posZ, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.NEUTRAL, 0.5F, 0.4F);
                        if (DiscUtils.getFreeSlot((EntityPlayer)thrower) >= 0) {
                            ((EntityPlayer) thrower).inventory.setInventorySlotContents(DiscUtils.getFreeSlot((EntityPlayer)thrower), this.sourceItemStack.copy());
                            this.setSourceItemStack(ItemStack.EMPTY);
                        }
                        this.setDead();
                    }
                    else if (((EntityPlayer)thrower).isCreative()) {
                        this.world.playSound((EntityPlayer)null, thrower.posX, thrower.posY, thrower.posZ, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.NEUTRAL, 0.5F, 0.4F);
                        this.setDead();
                    }
            }
            else if (result.entityHit instanceof EntityEnderCrystal) {
                result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, null), (float)this.damage);
                this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ARROW_HIT, SoundCategory.NEUTRAL, 0.5F, 0.4F);
            }
        }
    }

    @Override
    public void onUpdate() {
        if (!this.world.isRemote ) {
            BlockPos pos = new BlockPos(posX, posY, posZ);
            if (this.world.getBlockState(pos).getBlock() == Blocks.LAVA || this.world.getBlockState(pos).getBlock() == Blocks.FIRE || this.world.getBlockState(pos).getBlock() == Blocks.FLOWING_LAVA) {
                this.isFire = true;
                this.setFire(100);
            }
            if (this.getPlayerUUID() != null) {
                MinecraftServer server = world.getMinecraftServer();
                this.thrower = server.getPlayerList().getPlayerByUUID(this.getPlayerUUID());
            }
            EntityLivingBase thrower = this.getThrower();
            if (thrower instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) this.getThrower();
                if (player.isDead) {
                    if (this.sourceItemStack != null) {
                        if (DiscUtils.checkFreeSlot(player) && !(player).isCreative()) {
                            this.world.playSound((EntityPlayer) null, thrower.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.NEUTRAL, 0.5F, 0.4F);
                            if (DiscUtils.getFreeSlot(player) >= 0) {
                                (player).inventory.setInventorySlotContents(DiscUtils.getFreeSlot(player), this.sourceItemStack.copy());
                                this.setSourceItemStack(ItemStack.EMPTY);
                                this.setDead();
                            }
                            else {
                                this.setIsReturning(true);
                            }
                        } else if (player.isCreative()) {
                            this.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.NEUTRAL, 0.5F, 0.4F);
                            this.setDead();
                            super.onUpdate();
                            return;
                        }
                    }
                }
            }
            if (thrower == null) {
                if (flyingticks <= 0) {
                   returnToDispenser();
                    super.onUpdate();
                    return;
                }
            }

            if (!this.isReturning) {
                if (flyingticks <= 0) {
                    this.motionX = 0;
                    this.motionY = 0;
                    this.motionZ = 0;
                    this.isReturning = true;
                } else {
                    flyingticks--;
                }
                Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
                Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
                RayTraceResult rayTraceResult = this.world.rayTraceBlocks(vec3d, vec3d1);
                if (rayTraceResult != null && rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK) {

                    BlockPos blockPos = rayTraceResult.getBlockPos();

                    if (this.world.getBlockState(blockPos).getCollisionBoundingBox(this.world, blockPos) == Block.NULL_AABB) {
                        return;
                    }

                    EnumFacing facing = rayTraceResult.sideHit;
                    timesbounced += 1;
                    if (this.timesbounced < this.maxBounces) {
                        setFlyingTicks(getFlyingTicks() + 10);
                    }
                    switch (facing) {
                        case UP:
                        case DOWN:
                            this.motionY = -this.motionY;
                            break;
                        case NORTH:
                        case SOUTH:
                            this.motionZ = -this.motionZ;
                            break;
                        case EAST:
                        case WEST:
                            this.motionX = -this.motionX;
                            break;
                        default:
                            break;
                    }
                    this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F);
                    this.velocityChanged = true;
                }
            }
            if (this.isReturning && this.getThrower() instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) this.getThrower();
                returnToPlayer(player);
            }
        }
        super.onUpdate();
    }

    public void returnToPlayer(EntityPlayer player) {
        double disx = player.posX - this.posX;
        double disy = (player.posY + (double)player.getEyeHeight()) - this.posY;
        double disz = player.posZ - this.posZ;
        double disSquare = disx * disx + disy * disy + disz * disz;
        if (disSquare <= 4.0D) {
            setCanHit(false);
         if (DiscUtils.checkFreeSlot((EntityPlayer)thrower) && !((EntityPlayer)thrower).isCreative()) {
             this.world.playSound((EntityPlayer)null, thrower.posX, thrower.posY, thrower.posZ, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.NEUTRAL, 0.5F, 0.4F);
             if (DiscUtils.getFreeSlot((EntityPlayer)thrower) >= 0) {
                 ((EntityPlayer) thrower).inventory.setInventorySlotContents(DiscUtils.getFreeSlot((EntityPlayer)thrower), this.sourceItemStack.copy());
                 this.setSourceItemStack(ItemStack.EMPTY);
             }
             this.setDead();
         }
         else if (((EntityPlayer)thrower).isCreative()) {
             this.world.playSound((EntityPlayer)null, thrower.posX, thrower.posY, thrower.posZ, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.NEUTRAL, 0.5F, 0.4F);
             this.setDead();
         }
        }
        else {
            double factor = (double)this.speed / Math.sqrt(disSquare);
            this.motionX = factor * disx;
            this.motionY = factor * disy;
            this.motionZ = factor * disz;
        }
    }

    public void returnToDispenser() {
        double disx = this.dispX - this.posX;
        double disy = this.dispY - this.posY;
        double disz = this.dispZ - this.posZ;
        double disSquare = disx * disx + disy * disy + disz * disz;
        if (disSquare <= 4.0D) {
            setCanHit(false);
            this.world.playSound(null, this.dispX, this.dispY, this.dispZ, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.NEUTRAL, 0.5F, 0.4F);
            entityDropItem(this.sourceItemStack.copy(), 0.1F);
            this.setSourceItemStack(ItemStack.EMPTY);
            this.setDead();
        }
        else {
            double factor = (double)this.speed / Math.sqrt(disSquare);
            this.motionX = factor * disx;
            this.motionY = factor * disy;
            this.motionZ = factor * disz;
        }
    }

    @Override
    protected float getGravityVelocity() {
        return 0.0F;
    }

    @Override
    public boolean handleWaterMovement() {
        return false;
    }

    protected boolean canTriggerWalking()
    {
        return false;
    }

    public void setVariant(String variantIn) {
        this.dataManager.set(VARIANT, variantIn);
    }

    public String getVariant() {
      return (String)this.dataManager.get(VARIANT);
    }

    public void setDamage(double damageIn)
    {
        this.damage = damageIn;
    }

    public double getDamage()
    {
        return this.damage;
    }

    public void setSpeed(float speedIn)
    {
        this.speed = speedIn;
    }

    public float getSpeed()
    {
        return this.speed;
    }

    public void setMaxBounces(int maxBouncesIn) {
        this.maxBounces = maxBouncesIn;
    }

    public int getMaxBounces() {
        return this.maxBounces;
    }

    public void setBane_damage(double damageIn)
    {
        this.bane_damage = damageIn;
    }

    public double getBane_damage()
    {
        return this.bane_damage;
    }

    public void setSmite_damage(double damageIn)
    {
        this.smite_damage = damageIn;
    }

    public double getSmite_damage()
    {
        return this.smite_damage;
    }

    public void setCanHit(boolean canHitIn)
    {
        this.canHit = canHitIn;
    }

    public boolean getCanHit()
    {
        return this.canHit;
    }

    public void setIsReturning(boolean isReturningIn)
    {
        this.isReturning = isReturningIn;
    }

    public boolean getIsReturning()
    {
        return this.isReturning;
    }

    public void setTimesBounced(float bouncedIn)
    {
        this.timesbounced = bouncedIn;
    }

    public float getTimesbounced()
    {
        return this.timesbounced;
    }

    public void setFlyingTicks(int flyingticksIn)
    {
        this.flyingticks = flyingticksIn;
    }

    public int getFlyingTicks()
    {
        return this.flyingticks;
    }

    public void setSourceItem(Item sourceItem) {
        this.sourceItem = sourceItem;
    }

    public Item getSourceItem() {
        return this.sourceItem;
    }

    public void setDispX(double dispXIn)
    {
        this.dispX = dispXIn;
    }

    public double getDispX()
    {
        return this.dispX;
    }

    public void setDispY(double dispYIn)
    {
        this.dispY = dispYIn;
    }

    public double getDispY()
    {
        return this.dispY;
    }

    public void setDispZ(double dispZIn)
    {
        this.dispZ = dispZIn;
    }

    public double getDispZ()
    {
        return this.dispZ;
    }

    public void setKnockbackStrength(int knockbackStrengthIn)
    {
        this.knockbackStrength = knockbackStrengthIn;
    }

    public boolean canBeAttackedWithItem()
    {
        return false;
    }

    public float getEyeHeight()
    {
        return 0.0F;
    }

    public ItemStack getSourceItemStack() {return this.sourceItemStack;}

    public int getKnockbackStrength() {return this.knockbackStrength;}

    public void setSourceItemStackNoEnchant(ItemStack stack) {
        this.sourceItemStack = stack;
    }

    public void setPlayerUUID(UUID playerUUIDIn) {
        this.playerUUID = playerUUIDIn;
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    @Override
    public boolean getIsInvulnerable() {
        return true;
    }

    public void setSourceItemStack(ItemStack stack)
    {
        this.sourceItemStack = stack;

        if (!this.sourceItemStack.isEmpty() && this.sourceItemStack.isItemEnchanted()) {
            int sharpness = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack);
            int smite = EnchantmentHelper.getEnchantmentLevel(Enchantments.SMITE, stack);
            int bane_of_arthropods = EnchantmentHelper.getEnchantmentLevel(Enchantments.BANE_OF_ARTHROPODS, stack);
            int punch = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);

            if (sharpness > 0)
            {
                this.hasSharpness = true;
                this.setDamage(this.getDamage() + (double)sharpness * 0.5D + 0.5D);
            }

            if (smite > 0)
            {
                this.setSmite_damage(this.getSmite_damage() + (double)smite * 2.5D);
            }

            if (bane_of_arthropods > 0)
            {
                this.setBane_damage(this.getBane_damage() + (double)bane_of_arthropods * 2.5D);
            }

            if (punch > 0)
            {
                this.setKnockbackStrength(punch);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
            {
                this.isFire = true;
                this.setFire(100);
            }
        }
    }
}
