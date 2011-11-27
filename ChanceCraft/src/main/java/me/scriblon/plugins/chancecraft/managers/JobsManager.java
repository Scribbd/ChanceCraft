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
package me.scriblon.plugins.chancecraft.managers;

import com.zford.jobs.Jobs;
import com.zford.jobs.config.container.Job;
import com.zford.jobs.config.container.JobProgression;
import com.zford.jobs.config.container.JobsPlayer;
import java.util.List;
import me.scriblon.plugins.chancecraft.ChanceCraft;
import me.scriblon.plugins.chancecraft.container.ItemChance;
import me.scriblon.plugins.chancecraft.util.Linker;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

/**
 *
 * @author Coen Meulenkamp (Scriblon, ~theJaf) <coenmeulenkamp at gmail.com>
 */
public class JobsManager {
    
    private final ChanceCraft plugin;
    
    private Jobs jobs;
    private boolean isJobsAvailable;
    
    public JobsManager(){
        plugin = ChanceCraft.getInstance();
        
        final PluginManager pm = plugin.getServer().getPluginManager();
        isJobsAvailable = Linker.checkJobs(pm);
        if(isJobsAvailable)
            jobs = Linker.getJobs(pm);
    }
    
    public double calculateChance(Player player, ItemChance item){
        if(jobs.getJobsPlayer(player.getName()) != null)
            return item.getNormalChance();
        
        if(!hasPlayerJob(player.getName(), item))
            return item.getNormalChance();
        
        return getHighestChance(jobs.getJobsPlayer(player.getName()), item);
    }
    
    public boolean hasPlayerJob(String player, ItemChance item){
        final JobsPlayer jobsCollection = jobs.getJobsPlayer(player);
        final List<Job> jobsList = jobsCollection.getJobs();
        for(Job job : jobsList){
            if(item.hasProfession(job.getName()))
                return true;
        }
        return false;
    }
    
    public double getHighestChance(JobsPlayer jobsList, ItemChance item){
        double maxChance = -1.0;
        
        for(Job job : jobsList.getJobs()){
            final int lvl = jobsList.getJobsProgression(job).getLevel();
            final double jobChance = item.getChance(job.getName(), lvl);
            if(maxChance < jobChance){
                maxChance = jobChance;
            }
        }
        
        return maxChance;
    }
    
    public boolean isAvailable(){
        return isJobsAvailable;
    }
}
