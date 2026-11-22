package com.gab0521.throwingdiscbackport.events;

import com.gab0521.throwingdiscbackport.entities.throwing_disc.EntityThrowingDisc;
import com.gab0521.throwingdiscbackport.items.ItemThrowingDisc;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public abstract class DiscDispenser extends BehaviorDefaultDispenseItem {
        public ItemStack dispenseStack(IBlockSource source, ItemStack stack)
        {
            World world = source.getWorld();
            IPosition iposition = BlockDispenser.getDispensePosition(source);
            EnumFacing enumfacing = (EnumFacing)source.getBlockState().getValue(BlockDispenser.FACING);
            double spawnX = source.getX() + enumfacing.getFrontOffsetX() * 0.55D;
            double spawnY = source.getY() + enumfacing.getFrontOffsetY() * 0.55D;
            double spawnZ = source.getZ() + enumfacing.getFrontOffsetZ() * 0.55D;

            EntityThrowingDisc entityThrowingDisc = new EntityThrowingDisc(world, spawnX, spawnY, spawnZ);
            ItemThrowingDisc itemThrowingDisc = (ItemThrowingDisc) stack.getItem();
            if (stack.attemptDamageItem(1, world.rand, null)) {
                stack.setCount(0);
                source.getWorld().playSound(source.getX(), source.getY(), source.getZ(), SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }
            stack.attemptDamageItem(1, world.rand, null);
            entityThrowingDisc.setDamage(itemThrowingDisc.getDamageThrown());
            entityThrowingDisc.setBane_damage(itemThrowingDisc.getDamageThrown());
            entityThrowingDisc.setSmite_damage(itemThrowingDisc.getDamageThrown());
            entityThrowingDisc.setVariant(itemThrowingDisc.getMaterial());
            entityThrowingDisc.setFlyingTicks(itemThrowingDisc.getFloatingTime());
            entityThrowingDisc.setMaxBounces(itemThrowingDisc.getMaxBounces());
            entityThrowingDisc.setDispX(spawnX);
            entityThrowingDisc.setDispY(spawnY);
            entityThrowingDisc.setDispZ(spawnZ);

            entityThrowingDisc.setSourceItem(itemThrowingDisc);
            entityThrowingDisc.setSourceItemStack(stack);
            entityThrowingDisc.shoot((double)enumfacing.getFrontOffsetX(), (double)((float)enumfacing.getFrontOffsetY() + 0.1F), (double)enumfacing.getFrontOffsetZ(), itemThrowingDisc.getSpeed(), this.getProjectileInaccuracy());
            world.spawnEntity((Entity)entityThrowingDisc);

            return ItemStack.EMPTY;
        }

        protected void playDispenseSound(IBlockSource source)
        {
            source.getWorld().playEvent(1002, source.getBlockPos(), 0);
        }

        protected float getProjectileInaccuracy()
        {
            return 0.5F;
        }

    protected abstract IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn);
}
