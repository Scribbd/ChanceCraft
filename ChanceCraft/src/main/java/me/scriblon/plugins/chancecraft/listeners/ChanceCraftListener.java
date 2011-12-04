/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriblon.plugins.chancecraft.listeners;

import me.scriblon.plugins.chancecraft.ChanceCraft;
import me.scriblon.plugins.chancecraft.managers.ChanceManager;
import me.scriblon.plugins.chancecraft.managers.JobsManager;
import me.scriblon.plugins.chancecraft.managers.SettingsManager;
import me.scriblon.plugins.chancecraft.tasks.BenchModifier;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.event.inventory.InventoryCraftEvent;
import org.getspout.spoutapi.event.inventory.InventoryListener;

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
        if(!chances.isChanceItem(resultTypeID)){
            if(settings.general.isDebugPrint())
                ChanceCraft.logInfo("Item not of ChanceType: " + event.getResult().getType().name());
            return;
        }
        //Check if player has bypass permission
        final Player player = event.getPlayer();
        if(player.hasPermission("ChanceCraft.bypass")){
            player.sendMessage(ChatColor.DARK_GREEN + "ChanceCraft bypassed!");
            if(settings.general.isCommandPrint() || settings.general.isDebugPrint())
                ChanceCraft.logInfo("Player has bypassed ChanceCraft: " + player.getDisplayName());
            return;
        }
        //Check if item is an exclusive one.
        if(chances.isProfExclusive(resultTypeID)){
            player.sendMessage(ChatColor.RED + "The item you try to craft can only be crafted by professionals.");
            if(settings.general.isCommandPrint() || settings.general.isDebugPrint())
                ChanceCraft.logInfo("Player tried to craft ProfExclusive item " + event.getResult().getType().name() + " : " + player.getDisplayName());
            event.setCancelled(true);
            return;
        }
            
        final double chance = chances.calculateChance(resultTypeID, player);
        final int roll = chances.rollDice();
     //---Succeeding
        if(chances.determineSucces(roll, chance)){
            player.sendMessage(ChatColor.DARK_GREEN + "You succesfuly crafted the item.");
            if(settings.general.isDetailPrint()){
                player.sendMessage(ChatColor.DARK_GREEN + "Details: \n"
                        + ChatColor.GREEN + " Your Chance: "+ ChatColor.GRAY + Math.floor(chance)
                        + ChatColor.GREEN + " Your Roll: "+ ChatColor.GRAY + roll);
            }
            if(settings.general.isCommandPrint()){
                ChanceCraft.logInfo("Player succesfuly to craft the item " + event.getResult().getType().name() + " : " + player.getDisplayName());
            }
            return;
        }
        
     //----Failing
        player.sendMessage(ChatColor.RED + "You failed to craft the item.");
        if(settings.general.isDetailPrint()){
            player.sendMessage(ChatColor.DARK_RED + "Details: \n"
                    + ChatColor.RED + " Your Chance: "+ ChatColor.GRAY + Math.floor(chance)
                    + ChatColor.RED + " Your Roll: "+ ChatColor.GRAY + roll);
        }
        if(settings.general.isCommandPrint()){
            ChanceCraft.logInfo("Player failed to craft the item " + event.getResult().getType().name() + " : " + player.getDisplayName());
        }
        event.setCancelled(true);
      // Stack must be mutated
        if(!settings.general.isReturnOnFail()){
            BenchModifier task = new BenchModifier(player, event.getInventory(), settings.general.isFailToss(), event.getLocation());
            task.scheduleMe();
        }
    }
    
}
