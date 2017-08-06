/*
 * Copyright (C) 2017 Raffaele Francesco Mancino
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.raffaele.ancient.blocks.miner;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

/**
 *
 * @author Raffaele Francesco Mancino
 */
public class TileEntityMiner extends TileEntity implements IInventory, ISidedInventory
{
    private ItemStack[] chestContents = new ItemStack[28]; //27 inventario 1 carburante
    private int[] slotsTop=new int[] {0};
    private int[] slotsBottom=new int[27];
    /** The number of players currently using this chest */
    private String customName;
    /** The number of ticks that the furnace will keep burning */
    public int furnaceBurnTime;
    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    public int currentItemBurnTime;
    /** The number of ticks that the current item has been cooking for */
    public int furnaceCookTime;
    private int miningDepth;

    public TileEntityMiner()
    {
        this.miningDepth=1;
        for(int i=0;i<this.slotsBottom.length;i++)
        {
            this.slotsBottom[i]=i+1;
        }
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory()
    {
        return this.chestContents.length;
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(int i)
    {
        return this.chestContents[i];
    }
    
    public void func_145951_a(String n)
    {
        this.customName = n;
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        if (this.chestContents[i] != null)
        {
            ItemStack itemstack;

            if (this.chestContents[i].stackSize <= j)
            {
                itemstack = this.chestContents[i];
                this.chestContents[i] = null;
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = this.chestContents[i].splitStack(j);

                if (this.chestContents[i].stackSize == 0)
                {
                    this.chestContents[i] = null;
                }

                this.markDirty();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        if (this.chestContents[i] != null)
        {
            ItemStack itemstack = this.chestContents[i];
            this.chestContents[i] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack)
    {
        this.chestContents[i] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit())
        {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        
        this.markDirty();
    }

    /**
     * Returns the name of the inventory
     */
    @Override
    public String getInventoryName()
    {
        return this.hasCustomInventoryName() ? this.customName : "container.chest";
    }

    /**
     * Returns if the inventory is named
     */
    @Override
    public boolean hasCustomInventoryName()
    {
        return this.customName != null && this.customName.length() > 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound bTTagCompound)
    {
        super.readFromNBT(bTTagCompound);
        this.furnaceBurnTime = bTTagCompound.getShort("BurnTime");
        this.furnaceCookTime = bTTagCompound.getShort("CookTime");
        this.miningDepth = bTTagCompound.getShort("Depth");
        NBTTagList nbttaglist = bTTagCompound.getTagList("Items", 10);
        this.chestContents = new ItemStack[this.getSizeInventory()];

        if (bTTagCompound.hasKey("CustomName", 8))
        {
            this.customName = bTTagCompound.getString("CustomName");
        }

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.chestContents.length)
            {
                this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound bTTagCompound)
    {
        super.writeToNBT(bTTagCompound);
        bTTagCompound.setShort("BurnTime", (short)this.furnaceBurnTime);
        bTTagCompound.setShort("CookTime", (short)this.furnaceCookTime);
        bTTagCompound.setShort("Depth", (short)this.miningDepth);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.chestContents.length; ++i)
        {
            if (this.chestContents[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.chestContents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        bTTagCompound.setTag("Items", nbttaglist);

        if (this.hasCustomInventoryName())
        {
            bTTagCompound.setString("CustomName", this.customName);
        }
    }

    /**
     * Returns the maximum stack size for a inventory slot.
     */
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : entityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void updateEntity()
    {
        if(this.furnaceBurnTime>0)
            this.furnaceBurnTime--;
        
        if(!this.worldObj.isRemote)
        {
            if(this.canMine())
            {
                if(this.furnaceBurnTime==0 && this.isItemFuel(this.chestContents[0]))
                {
                    this.furnaceBurnTime=this.currentItemBurnTime=this.getItemBurnTime(this.chestContents[0]);
                    if(this.chestContents[0].stackSize>1)
                    {
                        this.chestContents[0].stackSize--;
                    }else{
                        this.chestContents[0]=null;
                    }
                    
                }
                
                if (this.isBurning())
                {
                    ++this.furnaceCookTime;

                    if (this.furnaceCookTime == 100)
                    {
                        this.furnaceCookTime = 0;
                        this.mine();
                    }
                }
                else
                {
                    this.furnaceCookTime = 0;
                }
            }
        }
    }
    
    private boolean canMine()
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                Block minedBlock = this.worldObj.getBlock(this.xCoord-1+i, this.yCoord-this.miningDepth, this.zCoord-1+j);
                if(minedBlock==Blocks.water || minedBlock==Blocks.lava)
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void mine()
    {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                Block minedBlock = this.worldObj.getBlock(this.xCoord-1+i, this.yCoord-this.miningDepth, this.zCoord-1+j);
                if(minedBlock==Blocks.grass)
                {
                    minedBlock=Blocks.dirt;
                }else if(minedBlock==Blocks.stone)
                {
                    minedBlock=Blocks.cobblestone;
                }
                if(minedBlock!=Blocks.bedrock && minedBlock!=Blocks.air)
                {
                    this.putInInventory(new ItemStack(minedBlock));
                    this.worldObj.setBlockToAir(this.xCoord-1+i, this.yCoord-this.miningDepth, this.zCoord-1+j);
                }
            }
        }
        if(this.miningDepth<this.yCoord)
            this.miningDepth++;
    }
    
    private void putInInventory(ItemStack itemStack)
    {
        int i=this.storeItemStack(itemStack);
        if(i==-1)
        {
            this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.xCoord+0.5F, this.yCoord+1, this.zCoord+0.5F, itemStack));
        }else{
            if(this.chestContents[i]==null)
            {
                this.chestContents[i]=itemStack;
            }else{
                this.chestContents[i].stackSize++;
            }
        }
    }
    
    private int storeItemStack(ItemStack itemStack)
    {
        for (int i = 1; i < this.chestContents.length; ++i)
        {
            if (this.chestContents[i] == null || this.chestContents[i] != null && this.chestContents[i].getItem() == itemStack.getItem() && this.chestContents[i].isStackable() && this.chestContents[i].stackSize < this.chestContents[i].getMaxStackSize() && this.chestContents[i].stackSize < this.getInventoryStackLimit() && (!this.chestContents[i].getHasSubtypes() || this.chestContents[i].getItemDamage() == itemStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(this.chestContents[i], itemStack))
            {
                return i;
            }
        }

        return -1;
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack)
    {
        if( slot==0 )
        {
            return this.isItemFuel(itemStack);
        }else{
            return true;
        }
    }

    /**
     * invalidates a tile entity
     */
    @Override
    public void invalidate()
    {
        super.invalidate();
        this.updateContainingBlockInfo();
    }
    
    public static boolean isItemFuel(ItemStack itemStack)
    {
        /**
         * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
         * fuel
         */
        return getItemBurnTime(itemStack) > 0;
    }
    
    public static int getItemBurnTime(ItemStack itemStack)
    {
        if (itemStack == null)
        {
            return 0;
        }
        else
        {
            Item item = itemStack.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air)
            {
                Block block = Block.getBlockFromItem(item);

                if (block == Blocks.wooden_slab)
                {
                    return 150;
                }

                if (block.getMaterial() == Material.wood)
                {
                    return 300;
                }

                if (block == Blocks.coal_block)
                {
                    return 16000;
                }
            }

            if (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item instanceof ItemHoe && ((ItemHoe)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item == Items.stick) return 100;
            if (item == Items.coal) return 1600;
            if (item == Items.lava_bucket) return 20000;
            if (item == Item.getItemFromBlock(Blocks.sapling)) return 100;
            if (item == Items.blaze_rod) return 2400;
            return GameRegistry.getFuelValue(itemStack);
        }
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}
    
    public boolean isBurning()
    {
        return this.furnaceBurnTime>0;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int i)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = 200;
        }

        return this.furnaceBurnTime * i / this.currentItemBurnTime;
    }
    
    @Override
    public int[] getAccessibleSlotsFromSide(int i)
    {
        if(i==1)
        {
            return this.slotsTop;
        }else{
            return this.slotsBottom;
        }
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemStack, int j)
    {
        return this.isItemValidForSlot(i, itemStack);
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemStack, int j)
    {
        return j != 0 || itemStack.getItem() == Items.bucket;
    }
}
