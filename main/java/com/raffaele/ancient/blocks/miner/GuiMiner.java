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
package com.raffaele.ancient.blocks.miner;

import com.raffaele.ancient.Info;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author Raffaele Francesco Mancino
 */
public class GuiMiner extends GuiContainer
{
    private static final ResourceLocation guiTexture = new ResourceLocation(Info.ID +  ":textures/gui/" + "miner.png");
    private TileEntityMiner tileEntityMiner;

    public GuiMiner(InventoryPlayer inventoryPlayer, TileEntityMiner entityMiner)
    {
        super(new ContainerMiner(inventoryPlayer, entityMiner));
        this.tileEntityMiner = entityMiner;
        this.ySize=195;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        //this.fontRendererObj.drawString(this.inventory.hasCustomInventoryName() ? this.inventory.getInventoryName() : I18n.format(this.inventory.getInventoryName(), new Object[0]), 8, 6, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(guiTexture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        if (this.tileEntityMiner.isBurning())
        {
            int i1 = this.tileEntityMiner.getBurnTimeRemainingScaled(13);
            this.drawTexturedModalRect(k + 8, l + 18 - i1, 176, 12 - i1, 14, i1 + 1);
        }
    }
}
