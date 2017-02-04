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
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

/**
 *
 * @author Raffaele Francesco Mancino
 */
public class BuildingStairs extends BlockStairs {
    public BuildingStairs(Block block, String name)
    {
        super(block, 0);
        this.setBlockName(name);
        this.setBlockTextureName(Info.ID + ":build/" + block.getUnlocalizedName());
        this.setHardness(1.5F);
        this.setResistance(10F);
        this.setHarvestLevel("pickaxe", 0);
    }
    
}
