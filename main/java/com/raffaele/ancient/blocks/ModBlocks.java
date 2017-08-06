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

import com.raffaele.ancient.Ancient;
import com.raffaele.ancient.Info;
import com.raffaele.ancient.blocks.bigfurnace.TileEntityBigFurnace;
import com.raffaele.ancient.blocks.miner.TileEntityMiner;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

/**
 *
 * @author Raffaele Francesco Mancino
 */

public class ModBlocks
{
    public static Block bigFurnace;
    public static Block bigFurnace_a;
    public static Block miner_block;
    public static Block marble;
    public static Block marble_brick;
    public static Block marble_slab_half;
    public static Block marble_stairs;
    public static Block marble_doric;
    public static Block marble_ionic;
    public static Block marble_corinthian;
    public static Block marble_chiseled;
    public static Block table_sculptor;
    public static Block table_greeks;
    
    public static void preInit()
    {        
        bigFurnace = new BigFurnaceBlock(false).setBlockName("big_furnace").setBlockTextureName(Info.ID + ":bigfurnace").setCreativeTab(Ancient.tabAncient).setHardness(1.5F).setResistance(10.0F);
        bigFurnace_a = new BigFurnaceBlock(true).setBlockName("big_furnace_a").setBlockTextureName(Info.ID + ":bigfurnace").setLightLevel(10F).setHardness(1.5F).setResistance(10.0F);
        
        GameRegistry.registerBlock(bigFurnace, bigFurnace.getUnlocalizedName());
        GameRegistry.registerBlock(bigFurnace_a, bigFurnace_a.getUnlocalizedName());
        
        miner_block = new MinerBlock().setBlockName("miner").setBlockTextureName(Info.ID + ":miner").setCreativeTab(Ancient.tabAncient);
        
        GameRegistry.registerBlock(miner_block, miner_block.getUnlocalizedName());

        marble = new BuildingBlock("marble").setBlockTextureName(Info.ID + ":marble/" + "marble").setCreativeTab(Ancient.tabAncient);
        marble_brick = new BuildingBlock("marble_brick").setBlockTextureName(Info.ID + ":marble/" + "marble_brick").setCreativeTab(Ancient.tabAncient);
        marble_slab_half = new BuildingSlab(Material.rock, "marble_slab", false).setBlockTextureName(Info.ID + ":marble/" + "marble_brick").setCreativeTab(Ancient.tabAncient);
        marble_stairs = new BuildingStairs(marble_brick, "marble_stairs").setCreativeTab(Ancient.tabAncient);
        marble_doric = new BuildingPillar("marble_doric", "doric").setBlockTextureName(Info.ID + ":marble/" + "marble_pillar").setCreativeTab(Ancient.tabAncient);
        marble_ionic = new BuildingPillar("marble_ionic", "ionic").setBlockTextureName(Info.ID + ":marble/" + "marble_pillar").setCreativeTab(Ancient.tabAncient);
        marble_corinthian = new BuildingPillar("marble_corinthian", "corinthian").setBlockTextureName(Info.ID + ":marble/" + "marble_pillar").setCreativeTab(Ancient.tabAncient);
        marble_chiseled = new BuildingBlock("marble_chiseled").setBlockTextureName(Info.ID + ":marble/" + "marble_chiseled").setCreativeTab(Ancient.tabAncient);
        
        GameRegistry.registerBlock(marble, marble.getUnlocalizedName());
        GameRegistry.registerBlock(marble_brick, marble_brick.getUnlocalizedName());
        GameRegistry.registerBlock(marble_slab_half, marble_slab_half.getUnlocalizedName());
        GameRegistry.registerBlock(marble_stairs, marble_stairs.getUnlocalizedName());
        GameRegistry.registerBlock(marble_doric, marble_doric.getUnlocalizedName());
        GameRegistry.registerBlock(marble_ionic, marble_ionic.getUnlocalizedName());
        GameRegistry.registerBlock(marble_corinthian, marble_corinthian.getUnlocalizedName());
        GameRegistry.registerBlock(marble_chiseled, marble_chiseled.getUnlocalizedName());
        
        table_sculptor = new CraftingTable("table_sculptor", Ancient.ID_SCULPTOR).setBlockTextureName(Info.ID + ":table_sculptor").setCreativeTab(Ancient.tabAncient);
        table_greeks = new CraftingTable("table_greek", Ancient.ID_GREEK).setBlockTextureName(Info.ID + ":table_greek").setCreativeTab(Ancient.tabAncient);
        
        GameRegistry.registerBlock(table_sculptor, table_sculptor.getUnlocalizedName());
        GameRegistry.registerBlock(table_greeks, table_greeks.getUnlocalizedName());
        
        crafting();
    }
    
    public static void Init()
    {
        GameRegistry.registerTileEntity(TileEntityBigFurnace.class, "bigfurnace");
        GameRegistry.registerTileEntity(TileEntityMiner.class, "miner");
    }
    
    public static void postInit()
    {
        
    }
    
    private static void crafting()
    {
        GameRegistry.addRecipe(new ItemStack(table_sculptor),
                "xx",
                "xx",
                'x', new ItemStack(Blocks.stonebrick));
        GameRegistry.addRecipe(new ItemStack(bigFurnace),
                "xxx",
                "x x",
                "xxx",
                'x', new ItemStack(Blocks.stonebrick));
    }
}
