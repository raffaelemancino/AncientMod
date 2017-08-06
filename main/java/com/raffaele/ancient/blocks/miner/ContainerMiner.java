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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Raffaele Francesco Mancino
 */
public class ContainerMiner extends Container
{
    private TileEntityMiner tileEntityMiner;
    private int numRows;
    
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;

    public ContainerMiner(InventoryPlayer inventoryPlayer, TileEntityMiner entityMiner)
    {
        this.tileEntityMiner=entityMiner;
        this.numRows=3;
        this.addSlotToContainer(new Slot(entityMiner, 0, 8, 23)); //burnSlot
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(entityMiner, j + i * 9 + 1, 8 + j * 18, 45 + i * 18));
            }
        }
        for (int i = 0; i < this.numRows; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 113 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 171));
        }
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int i)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(i);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            
            if (TileEntityMiner.isItemFuel(itemstack1))
            {
                if (!this.mergeItemStack(itemstack1, 0, 1, false))
                {
                    return null;
                }
            }
            else if (i < this.tileEntityMiner.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, this.tileEntityMiner.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.tileEntityMiner.getSizeInventory(), false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    /**
     * Called when the container is closed.
     */
    @Override
    public void onContainerClosed(EntityPlayer p_75134_1_)
    {
        super.onContainerClosed(p_75134_1_);
        this.tileEntityMiner.closeInventory();
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return this.tileEntityMiner.isUseableByPlayer(entityPlayer);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int i, int j)
    {
        if (i == 0)
        {
            this.tileEntityMiner.furnaceCookTime = j;
        }

        if (i == 1)
        {
            this.tileEntityMiner.furnaceBurnTime = j;
        }

        if (i == 2)
        {
            this.tileEntityMiner.currentItemBurnTime = j;
        }
    }
    
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastCookTime != this.tileEntityMiner.furnaceCookTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.tileEntityMiner.furnaceCookTime);
            }

            if (this.lastBurnTime != this.tileEntityMiner.furnaceBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 1, this.tileEntityMiner.furnaceBurnTime);
            }

            if (this.lastItemBurnTime != this.tileEntityMiner.currentItemBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 2, this.tileEntityMiner.currentItemBurnTime);
            }
        }

        this.lastCookTime = this.tileEntityMiner.furnaceCookTime;
        this.lastBurnTime = this.tileEntityMiner.furnaceBurnTime;
        this.lastItemBurnTime = this.tileEntityMiner.currentItemBurnTime;
    }
}
