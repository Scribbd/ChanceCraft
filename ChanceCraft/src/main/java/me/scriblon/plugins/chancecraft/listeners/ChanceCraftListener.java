/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriblon.plugins.chancecraft.listeners;

import me.scriblon.plugins.chancecraft.ChanceCraft;
import me.scriblon.plugins.chancecraft.managers.ChanceManager;
import me.scriblon.plugins.chancecraft.managers.JobsManager;
import me.scriblon.plugins.chancecraft.managers.SettingsManager;
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
        //Check for canceled event
        if(event.isCancelled())
            return;
        
    }
    
}
