/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriblon.plugins.chancecraft.listeners;

import com.zford.jobs.Jobs;
import com.zford.jobs.config.container.Job;
import com.zford.jobs.config.container.JobsPlayer;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.scriblon.plugins.chancecraft.container.GeneralConfigurations;
import me.scriblon.plugins.chancecraft.container.chances.ItemChance;
import me.scriblon.plugins.chancecraft.listeners.randomizers.Dice;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.event.inventory.InventoryCraftEvent;
import org.getspout.spoutapi.event.inventory.InventoryListener;
import org.getspout.spoutapi.inventory.CraftingInventory;

/**
 *
 * @author Coen Meulenkamp (Scriblon, ~theJaf) <coenmeulenkamp at gmail.com>
 */
public class ChanceCraftListener extends InventoryListener{
    
    // finals
    public static final double NO_JOB = Double.MIN_VALUE;
    private static final Logger log = Logger.getLogger("Minecraft");
    // fields
    private GeneralConfigurations generalConfig;
    private HashMap<String, ItemChance> itemConfig;
    private Plugin jobs;
    private Dice dice;

    public ChanceCraftListener(GeneralConfigurations generalConfig, HashMap<String, ItemChance> itemConfig, Plugin jobs) {
        this.generalConfig = generalConfig;
        this.itemConfig = itemConfig;
        this.jobs = jobs;
        dice = new Dice();
    }
    
    @Override
    public void onInventoryCraft(InventoryCraftEvent event) {
        super.onInventoryCraft(event);
        //Check for canceled event
        if(event.isCancelled()){
            if(generalConfig.isDebugPrint())
                log.log(Level.INFO, "[ChanceCraft] Event already canceled");
            return;
        }
        //Check if item is being crafted
        if(event.getInventory().getResult() == null){
            if(generalConfig.isDebugPrint() || generalConfig.isCommandPrint())
                log.log(Level.INFO, "[ChanceCraft] " + event.getPlayer().getName() + " tried to craft a non-item");
            return;
        }
        //Getting info to process event
        CraftingInventory craft = event.getInventory();
        String itemID = Integer.toString(craft.getResult().getTypeId());
        Player player = event.getPlayer();
        //Get info
        if(itemConfig.containsKey(itemID)){
            double highestChance = NO_JOB;
            ItemChance item = itemConfig.get(itemID);
            //Get info from Jobs
            if(jobs != null){
                Jobs jobsPlug = (Jobs) jobs;
                JobsPlayer importedJobs = jobsPlug.getJobsPlayer(player.getName());
                List<Job> jobList = importedJobs.getJobs();
                //Check if Job is in targeted item
                for(Job singleJob: jobList){
                    if(item.hasProfession(singleJob.getName())){
                        int currentLvl = importedJobs.getJobsProgression(singleJob).getLevel();
                        double currentChance = item.getChance(singleJob.getName(), currentLvl);
                        if(currentChance > highestChance){
                            highestChance = currentChance;
                        }
                    }         
                }
            }
            //If there was no job then normal chance should be applied.
            if(highestChance == NO_JOB){
                highestChance = item.getNormalChance();
            }
        }
    }
    
}
