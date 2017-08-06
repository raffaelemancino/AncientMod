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
import com.raffaele.ancient.items.ModItems;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

@Mod(modid = Info.ID, name = Info.NAME, version = Info.VERSION, acceptedMinecraftVersions = Info.MINECRAFT)
public class Ancient
{
    @Mod.Instance
    public static Ancient instance = new Ancient();
    
    public static final int ID_BIGFURNACE = 0;
    public static final int ID_SCULPTOR = 1;
    public static final int ID_GREEK = 2;
    public static final int ID_MINER = 3;
    
    public static final CreativeTabs tabAncient = new CreativeTabs("tab_ancient")
    {
        @Override
        public Item getTabIconItem()
        {
            return Items.golden_helmet;
        }
            
    };
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ModBlocks.preInit();
        ModItems.preInit();
    }
    
    @EventHandler
    public void Init(FMLInitializationEvent event)
    {
        ModBlocks.Init();
        ModItems.Init();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        ModBlocks.postInit();
        ModItems.postInit();
    }
}
