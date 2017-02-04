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

package com.raffaele.ancient.blocks;

import com.raffaele.ancient.Info;
import com.raffaele.ancient.blocks.bigfurnace.TileEntityBigFurnace;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

/**
 *
 * @author Raffaele Francesco Mancino
 */

public class ModBlocks
{
    public static Block bigFurnace;
    public static Block bigFurnace_a;
    
    public static void preInit()
    {
        GameRegistry.registerTileEntity(TileEntityBigFurnace.class, "bigfurnace");
        bigFurnace = new BigFurnaceBlock(false).setBlockName("big_furnace").setBlockTextureName(Info.ID + ":bigfurnace").setCreativeTab(CreativeTabs.tabDecorations);
        bigFurnace_a = new BigFurnaceBlock(true).setBlockName("big_furnace_a").setBlockTextureName(Info.ID + ":bigfurnace").setLightLevel(1.5F);
        GameRegistry.registerBlock(bigFurnace, bigFurnace.getUnlocalizedName());
        GameRegistry.registerBlock(bigFurnace_a, bigFurnace_a.getUnlocalizedName());
        
    }
    
    public static void Init()
    {
        
    }
    
    public static void postInit()
    {
        
    }
}
