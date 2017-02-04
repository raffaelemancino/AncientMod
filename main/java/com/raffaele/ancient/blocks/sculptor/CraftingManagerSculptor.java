/*
 * Copyright (C) 2016 Raffaele Francesco Mancino
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
package com.raffaele.ancient.blocks.sculptor;

import com.raffaele.ancient.blocks.ModBlocks;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

/**
 *
 * @author Raffaele Francesco Mancino
 */
public class CraftingManagerSculptor
{
    public static final CraftingManagerSculptor instance = new CraftingManagerSculptor();
    private List recipes = new ArrayList();

    public CraftingManagerSculptor()
    {
        this.addShapelessRecipe(new ItemStack(ModBlocks.marble), new ItemStack(Blocks.stone)); //marble
        this.addRecipe(new ItemStack(ModBlocks.marble_brick, 4), //marble brick
                "xx",
                "xx",
                'x',new ItemStack(ModBlocks.marble));
        this.addRecipe(new ItemStack(ModBlocks.marble_chiseled, 4), //marble brick
                "xx",
                "xx",
                'x',new ItemStack(ModBlocks.marble_brick));
        this.addRecipe(new ItemStack(ModBlocks.marble_slab_half, 6), //marble slab
                "xxx",
                'x', new ItemStack(ModBlocks.marble_brick));
        this.addRecipe(new ItemStack(ModBlocks.marble_stairs, 4), //marble stairs
                "  x",
                " xx",
                "xxx",
                'x', new ItemStack(ModBlocks.marble_brick));
        this.addRecipe(new ItemStack(ModBlocks.marble_doric), //marble doric
                "x",
                "x",
                "x",
                'x',new ItemStack(ModBlocks.marble));
        this.addRecipe(new ItemStack(ModBlocks.marble_ionic), //marble ionic
                "y",
                "x",
                "x",
                'x',new ItemStack(ModBlocks.marble),
                'y',new ItemStack(ModBlocks.marble_brick));
        this.addRecipe(new ItemStack(ModBlocks.marble_corinthian), //marble corinthian
                "y",
                "x",
                "y",
                'x',new ItemStack(ModBlocks.marble),
                'y',new ItemStack(ModBlocks.marble_brick));
        
    }
    

    public static CraftingManagerSculptor getInstance() {
        return instance;
    }
    
    public ItemStack findMatchingRecipe(InventoryCrafting inventoryCrafting, World world)
    {
        int i = 0;
        ItemStack itemstack = null;
        ItemStack itemstack1 = null;
        int j;

        for (j = 0; j < inventoryCrafting.getSizeInventory(); ++j)
        {
            ItemStack itemstack2 = inventoryCrafting.getStackInSlot(j);

            if (itemstack2 != null)
            {
                if (i == 0)
                {
                    itemstack = itemstack2;
                }

                if (i == 1)
                {
                    itemstack1 = itemstack2;
                }

                ++i;
            }
        }

        if (i == 2 && itemstack.getItem() == itemstack1.getItem() && itemstack.stackSize == 1 && itemstack1.stackSize == 1 && itemstack.getItem().isRepairable())
        {
            Item item = itemstack.getItem();
            int j1 = item.getMaxDamage() - itemstack.getItemDamageForDisplay();
            int k = item.getMaxDamage() - itemstack1.getItemDamageForDisplay();
            int l = j1 + k + item.getMaxDamage() * 5 / 100;
            int i1 = item.getMaxDamage() - l;

            if (i1 < 0)
            {
                i1 = 0;
            }

            return new ItemStack(itemstack.getItem(), 1, i1);
        }
        else
        {
            for (j = 0; j < this.recipes.size(); ++j)
            {
                IRecipe irecipe = (IRecipe)this.recipes.get(j);

                if (irecipe.matches(inventoryCrafting, world))
                {
                    return irecipe.getCraftingResult(inventoryCrafting);
                }
            }

            return null;
        }
    }
    
    public void addRecipe(ItemStack stack, Object ... object)
    {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if (object[i] instanceof String[])
        {
            String[] astring = (String[])((String[])object[i++]);

            for (int l = 0; l < astring.length; ++l)
            {
                String s1 = astring[l];
                ++k;
                j = s1.length();
                s = s + s1;
            }
        }
        else
        {
            while (object[i] instanceof String)
            {
                String s2 = (String)object[i++];
                ++k;
                j = s2.length();
                s = s + s2;
            }
        }

        HashMap hashmap;

        for (hashmap = new HashMap(); i < object.length; i += 2)
        {
            Character character = (Character)object[i];
            ItemStack itemstack1 = null;

            if (object[i + 1] instanceof Item)
            {
                itemstack1 = new ItemStack((Item)object[i + 1]);
            }
            else if (object[i + 1] instanceof Block)
            {
                itemstack1 = new ItemStack((Block)object[i + 1], 1, 32767);
            }
            else if (object[i + 1] instanceof ItemStack)
            {
                itemstack1 = (ItemStack)object[i + 1];
            }

            hashmap.put(character, itemstack1);
        }

        ItemStack[] aitemstack = new ItemStack[j * k];

        for (int i1 = 0; i1 < j * k; ++i1)
        {
            char c0 = s.charAt(i1);

            if (hashmap.containsKey(Character.valueOf(c0)))
            {
                aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).copy();
            }
            else
            {
                aitemstack[i1] = null;
            }
        }

        ShapedRecipes shapedrecipes = new ShapedRecipes(j, k, aitemstack, stack);
        this.recipes.add(shapedrecipes);
    }
    
    public void addShapelessRecipe(ItemStack stack, Object ... object)
    {
        ArrayList arraylist = new ArrayList();
        Object[] aobject = object;
        int i = object.length;

        for (int j = 0; j < i; ++j)
        {
            Object object1 = aobject[j];

            if (object1 instanceof ItemStack)
            {
                arraylist.add(((ItemStack)object1).copy());
            }
            else if (object1 instanceof Item)
            {
                arraylist.add(new ItemStack((Item)object1));
            }
            else
            {
                if (!(object1 instanceof Block))
                {
                    throw new RuntimeException("Invalid shapeless recipy!");
                }

                arraylist.add(new ItemStack((Block)object1));
            }
        }

        this.recipes.add(new ShapelessRecipes(stack, arraylist));
    }
}
