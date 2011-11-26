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
import me.scriblon.plugins.chancecraft.ChanceCraft;
import me.scriblon.plugins.chancecraft.util.Linker;
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
    
    public boolean isAvailable(){
        return isJobsAvailable;
    }
}
