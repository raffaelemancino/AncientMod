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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 *
 * @author Raffaele Francesco Mancino
 */
class CraftingTable extends Block
{
    private IIcon icons[] = new IIcon[3];
    public int guiID;
    
    public CraftingTable(String name, int id)
    {
        super(Material.rock);
        this.setBlockName(name);
        this.setHardness(1.5F);
        this.setResistance(5.0F);
        this.setHarvestLevel("pickaxe", 0);
        this.guiID = id;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg)
    {       
        icons[0] = reg.registerIcon(this.getTextureName() + "_top");
        icons[1] = reg.registerIcon(this.getTextureName() + "_side");
        icons[2] = reg.registerIcon(this.getTextureName() + "_bottom");
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        switch (side)
        {
            case 0:
                return icons[2]; //side 0 is bottom
            case 1:
                return icons[0]; //side 1 is top
            default:
                return icons[1]; //for other side
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float a, float b, float c)
    {
        player.openGui(Ancient.instance, guiID, world, x, y, z);
        return true;
    }
}
