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
package com.raffaele.ancient.blocks.bigfurnace;

import com.raffaele.ancient.blocks.BigFurnaceBlock;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

/**
 *
 * @author Raffaele Francesco Mancino
 */
public class TileEntityBigFurnace extends TileEntity implements ISidedInventory
{
    private static final int[] slotsTop = new int[] {0, 1, 2, 3};
    private static final int[] slotsBottom = new int[] {4, 5, 6, 7, 8};
    private static final int[] slotsSides = new int[] {4};
    /** The ItemStacks that hold the items currently being used in the furnace */
    private ItemStack[] furnaceItemStacks = new ItemStack[9];
    /** The number of ticks that the furnace will keep burning */
    public int furnaceBurnTime;
    /** The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for */
    public int currentItemBurnTime;
    /** The number of ticks that the current item has been cooking for */
    public int furnaceCookTime;
    private String name;

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory()
    {
        return this.furnaceItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(int i)
    {
        return this.furnaceItemStacks[i];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        if (this.furnaceItemStacks[i] != null)
        {
            ItemStack itemstack;

            if (this.furnaceItemStacks[i].stackSize <= j)
            {
                itemstack = this.furnaceItemStacks[i];
                this.furnaceItemStacks[i] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.furnaceItemStacks[i].splitStack(j);

                if (this.furnaceItemStacks[i].stackSize == 0)
                {
                    this.furnaceItemStacks[i] = null;
                }

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
        if (this.furnaceItemStacks[i] != null)
        {
            ItemStack itemstack = this.furnaceItemStacks[i];
            this.furnaceItemStacks[i] = null;
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
        this.furnaceItemStacks[i] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit())
        {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    /**
     * Returns the name of the inventory
     */
    @Override
    public String getInventoryName()
    {
        return this.hasCustomInventoryName() ? this.name : "container.furnace";
    }

    /**
     * Returns if the inventory is named
     */
    @Override
    public boolean hasCustomInventoryName()
    {
        return this.name != null && this.name.length() > 0;
    }

    public void func_145951_a(String n)
    {
        this.name = n;
    }

    @Override
    public void readFromNBT(NBTTagCompound bTTagCompound)
    {
        super.readFromNBT(bTTagCompound);
        NBTTagList nbttaglist = bTTagCompound.getTagList("Items", 10);
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.furnaceItemStacks.length)
            {
                this.furnaceItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.furnaceBurnTime = bTTagCompound.getShort("BurnTime");
        this.furnaceCookTime = bTTagCompound.getShort("CookTime");
        this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[4]);

        if (bTTagCompound.hasKey("CustomName", 8))
        {
            this.name = bTTagCompound.getString("CustomName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound bTTagCompound)
    {
        super.writeToNBT(bTTagCompound);
        bTTagCompound.setShort("BurnTime", (short)this.furnaceBurnTime);
        bTTagCompound.setShort("CookTime", (short)this.furnaceCookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.furnaceItemStacks.length; ++i)
        {
            if (this.furnaceItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.furnaceItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        bTTagCompound.setTag("Items", nbttaglist);

        if (this.hasCustomInventoryName())
        {
            bTTagCompound.setString("CustomName", this.name);
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
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int i)
    {
        return this.furnaceCookTime * i / 200;
    }

    /**
     * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
     * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
     */
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int p_145955_1_)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = 200;
        }

        return this.furnaceBurnTime * p_145955_1_ / this.currentItemBurnTime;
    }

    /**
     * Furnace isBurning
     */
    public boolean isBurning()
    {
        return this.furnaceBurnTime > 0;
    }
    
    /**
     * Decrementa il combustibile
     */
    @Override
    public void updateEntity()
    {
        boolean flag = this.furnaceBurnTime > 0;
        boolean flag1 = false;

        if (this.furnaceBurnTime > 0)
        {
            --this.furnaceBurnTime;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.furnaceBurnTime != 0 || this.furnaceItemStacks[4] != null && (this.furnaceItemStacks[0] != null || this.furnaceItemStacks[1] != null || this.furnaceItemStacks[2] != null || this.furnaceItemStacks[3] != null))
            {
                if (this.furnaceBurnTime == 0 && (this.canSmelt(0) || this.canSmelt(1) || this.canSmelt(2) || this.canSmelt(3)))
                {
                    this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[4]);

                    if (this.furnaceBurnTime > 0)
                    {
                        flag1 = true;

                        if (this.furnaceItemStacks[4] != null)
                        {
                            --this.furnaceItemStacks[4].stackSize;

                            if (this.furnaceItemStacks[4].stackSize == 0)
                            {
                                this.furnaceItemStacks[4] = furnaceItemStacks[4].getItem().getContainerItem(furnaceItemStacks[4]);
                            }
                        }
                    }
                }

                if (this.isBurning() && ( this.canSmelt(0) || this.canSmelt(1) || this.canSmelt(2) || this.canSmelt(3)))
                {
                    ++this.furnaceCookTime;

                    if (this.furnaceCookTime == 200)
                    {
                        this.furnaceCookTime = 0;
                        for(int i=0;i<4;i++)
                            if(this.canSmelt(i))
                                this.smeltItem(i);
                        flag1 = true;
                    }
                }
                else
                {
                    this.furnaceCookTime = 0;
                }
            }

            if (flag != this.furnaceBurnTime > 0)
            {
                flag1 = true;
                BigFurnaceBlock.updateFurnaceBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
    }

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     * vero se puo trasformare il materiale e c'è spazio
     */
    private boolean canSmelt(int i)
    {
        
        if (this.furnaceItemStacks[i] == null )
        {
            return false;
        }
        else
        {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[i]);
            
            if (itemstack == null) return false;
            if (this.furnaceItemStacks[i+5] == null) return true;
            if (!this.furnaceItemStacks[i+5].isItemEqual(itemstack)) return false;
            int result = furnaceItemStacks[i+5].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.furnaceItemStacks[i+5].getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }

    /**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     * trasforma il materiale aggiorna le quantità
     */
    public void smeltItem(int i)
    {
        if (this.canSmelt(i))
        {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.furnaceItemStacks[i]);

            if (this.furnaceItemStacks[i+5] == null)
            {
                this.furnaceItemStacks[i+5] = itemstack.copy();
            }
            else if (this.furnaceItemStacks[i+5].getItem() == itemstack.getItem())
            {
                this.furnaceItemStacks[i+5].stackSize += itemstack.stackSize; // Forge BugFix: Results may have multiple items
            }

            --this.furnaceItemStacks[i].stackSize;

            if (this.furnaceItemStacks[i].stackSize <= 0)
            {
                this.furnaceItemStacks[i] = null;
            }
        }
    }

    /**
     * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
     * fuel
     * //200 tick -> 1 item cooked
     */
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

    public static boolean isItemFuel(ItemStack itemStack)
    {
        /**
         * Returns the number of ticks that the supplied fuel item will keep the furnace burning, or 0 if the item isn't
         * fuel
         */
        return getItemBurnTime(itemStack) > 0;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : entityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    public void openInventory() {}

    public void closeInventory() {}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack)
    {
        
        if( slot==5 || slot==6 || slot==7 || slot==8)
        {
            return false;
        }else if(slot==4)
        {
            return isItemFuel(itemStack);
        }else{
            return true;
        }
    }

    /**
     * Returns an array containing the indices of the slots that can be accessed by automation on the given side of this
     * block.
     */
    @Override
    public int[] getAccessibleSlotsFromSide(int i)
    {
        return i == 0 ? slotsBottom : (i == 1 ? slotsTop : slotsSides);
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    @Override
    public boolean canInsertItem(int i, ItemStack itemStack, int j)
    {
        return this.isItemValidForSlot(i, itemStack);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    @Override
    public boolean canExtractItem(int i, ItemStack itemStack, int j)
    {
        return j != 0 || i != 4 || itemStack.getItem() == Items.bucket;
    }
}
