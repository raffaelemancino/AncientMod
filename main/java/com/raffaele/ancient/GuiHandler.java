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
package com.raffaele.ancient;

import com.raffaele.ancient.blocks.bigfurnace.ContainerBigFurnace;
import com.raffaele.ancient.blocks.bigfurnace.GuiBigFurnace;
import com.raffaele.ancient.blocks.bigfurnace.TileEntityBigFurnace;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 *
 * @author Raffaele Francesco Mancino
 */
public class GuiHandler implements IGuiHandler{

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == Ancient.ID_BIGFURNACE) {
            TileEntityBigFurnace entity = (TileEntityBigFurnace)world.getTileEntity(x, y, z);
            return new ContainerBigFurnace(player.inventory, entity);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if (ID == Ancient.ID_BIGFURNACE) {
            TileEntityBigFurnace entity = (TileEntityBigFurnace)world.getTileEntity(x, y, z);
            return new GuiBigFurnace(player.inventory, entity);
        }
        return null;
    }
    
}
