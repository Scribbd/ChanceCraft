/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriblon.plugins.chancecraft.listeners;

import me.scriblon.plugins.chancecraft.ChanceCraft;
import me.scriblon.plugins.chancecraft.managers.ChanceManager;
import me.scriblon.plugins.chancecraft.managers.JobsManager;
import me.scriblon.plugins.chancecraft.managers.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.event.inventory.InventoryCraftEvent;
import org.getspout.spoutapi.event.inventory.InventoryListener;
import org.getspout.spoutapi.inventory.CraftingInventory;

/**
 *
 * @author Coen Meulenkamp (Scriblon, ~theJaf) <coenmeulenkamp at gmail.com>
 */
public class ChanceCraftListener extends InventoryListener{
    
    private final ChanceCraft plugin;
    private final JobsManager jobs;
    private final ChanceManager chances;
    private final SettingsManager settings;

    public ChanceCraftListener() {
        plugin = ChanceCraft.getInstance();
        jobs = plugin.getJobsManager();
        chances = plugin.getChanceManager();
        settings = plugin.getSettingsManager();
    }
    
    @Override
    public void onInventoryCraft(InventoryCraftEvent event) {
        super.onInventoryCraft(event);
     //--Prepration
        //Check for canceled event
        if(event.isCancelled())
            return;
        //Check if item is a chanced item.
        final int resultTypeID = event.getResult().getTypeId();
        if(!chances.isChanceItem(resultTypeID))
            return;
        //Check if player has bypass permission
        final Player player = event.getPlayer();
        if(player.hasPermission("ChanceCraft.bypass")){
            player.sendMessage(ChatColor.DARK_GREEN + "ChanceCraft bypassed!");
        }
        //Check if item is an exclusive one.
        if(chances.isProfExclusive(resultTypeID)){
            player.sendMessage(ChatColor.RED + "The item you try to craft can only be crafted by professionals.");
            event.setCancelled(true);
            return;
        }
            
        final double chance = chances.calculateChance(resultTypeID, player);
        final int roll = chances.rollDice();
     //---Succeeding
        if(chances.determineSucces(roll, chance)){
            player.sendMessage("You succesfuly crafted the item.");
        }
        
     //----Failing
        if(settings.general.isDetailPrint()){
            player.sendMessage("");
        } else {
            player.sendMessage("You failed to craft the item.");
        }
    }
    
}
