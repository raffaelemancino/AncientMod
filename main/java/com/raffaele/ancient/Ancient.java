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

package com.raffaele.ancient;

import com.raffaele.ancient.blocks.ModBlocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Info.ID, name = Info.NAME, version = Info.VERSION, acceptedMinecraftVersions = Info.MINECRAFT)
public class Ancient
{
    @Mod.Instance
    public static Ancient instance = new Ancient();
    
    public static final int ID_BIGFURNACE = 0;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ModBlocks.preInit();
    }
    
    @EventHandler
    public void Init(FMLInitializationEvent event)
    {
        ModBlocks.Init();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        ModBlocks.postInit();
    }
}
