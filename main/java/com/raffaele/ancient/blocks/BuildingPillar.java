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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 *
 * @author Raffaele Francesco Mancino
 */
public class BuildingPillar extends BuildingBlock
{
    
    private IIcon[] icons;
    private String capital;
    
    public BuildingPillar(String name, String capital)
    {
        super(name);
        this.capital = capital;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg)
    {
        icons = new IIcon[3];
        icons[0] = reg.registerIcon(this.getTextureName() + "_top");
        icons[1] = reg.registerIcon(this.getTextureName() + "_side");
        icons[2] = reg.registerIcon(this.getTextureName() + "_" + this.capital);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (side==0 || side==1) //side 0 - 1 is top and bottom
        {
            return icons[0];
        }else if (meta==1){
            return icons[2];
        }else{
            return icons[1];
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        if (world.getBlock(x, y+1, z) != this)
        {
            world.setBlockMetadataWithNotify(x, y, z, 1, 1);
        }else{
            world.setBlockMetadataWithNotify(x, y, z, 0, 1);
        }
        world.markBlockForUpdate(x, y, z);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item)
    {
        if (world.getBlock(x, y+1, z) != this)
        {
            world.setBlockMetadataWithNotify(x, y, z, 1, 1);
        }else{
            world.setBlockMetadataWithNotify(x, y, z, 0, 1);
        }
        world.markBlockForUpdate(x, y, z);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
    {
        if (world.getBlock(x, y+1, z) != this)
        {
            meta=1;
        }else{
            meta=0;
        }
        return meta;
    }
}
