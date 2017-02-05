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
package com.raffaele.ancient.blocks.table;

import com.raffaele.ancient.Info;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Raffaele Francesco Mancino
 */
public class GuiGreek extends GuiContainer{

    private ResourceLocation texture;
    
    public GuiGreek(InventoryPlayer inventory, World world, int x, int y, int z) {
        super(new ContainerGreek(inventory, world, x, y, z));
        
        this.texture =  new ResourceLocation(Info.ID + ":textures/gui/" + "gui_table.png");
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        this.fontRendererObj.drawString(StatCollector.translateToLocal("Greeks Workbanch"), 28, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("Inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        this.mc.getTextureManager().bindTexture(this.texture);
	int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
	drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
    
}
