/*
 *Copyright (C) 2011 Coen Meulenkamp (Scriblon) <coenmeulenkamp at gmail.com>
 *This program is free software: you can redistribute it and/or modify
 *it under the terms of the GNU General Public License as published by
 *the Free Software Foundation, either version 3 of the License, or
 *(at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copyFile of the GNU General Public License
 *along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.scriblon.plugins.chancecraft.tasks;

import me.scriblon.plugins.chancecraft.ChanceCraft;
import me.scriblon.plugins.chancecraft.util.InvenUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.getspout.spoutapi.inventory.CraftingInventory;

/**
 *
 * @author Coen Meulenkamp (Scriblon, ~theJaf) <coenmeulenkamp at gmail.com>
 */
public class BenchModifier implements Runnable{
    
    private final ChanceCraft plugin;
    private final BukkitScheduler scheduler;
    private final Player player;
    private final CraftingInventory inventory;
    private final boolean failToss;
    private final Location benchLoc;
    
    private int iD;
    
    public BenchModifier(Player player, CraftingInventory inventory, boolean failToss, Location benchLoc){
        plugin = ChanceCraft.getInstance();
        scheduler = plugin.getServer().getScheduler();
        this.player = player;
        this.inventory = inventory;
        this.failToss = failToss;
        this.benchLoc = benchLoc;
        
        iD = -1;
    }
    
    public void run() {
        ItemStack[] substractionStack = plugin.getChanceManager().getSubstractionStack(inventory.getMatrix());
        ItemStack[] substractedStack = InvenUtil.substractStack(inventory.getMatrix(), substractionStack);
        if(failToss){
            InvenUtil.dropStack(substractedStack, benchLoc);
            substractedStack = InvenUtil.emptyStack(substractedStack);
        }
        inventory.setMatrix(substractedStack);
    }
    
    public void scheduleMe(){
        iD = scheduler.scheduleSyncDelayedTask(plugin, this, 1L);
    }
    
    public void cancelMe(){
        if(iD != -1)
            scheduler.cancelTask(iD);
        else
            ChanceCraft.logSevere("Canceling task failed! On player: " + player.getDisplayName());
    }
}