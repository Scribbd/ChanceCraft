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

/* Inspired by the Kickstarter tool */

package me.scriblon.plugins.chancecraft;


import java.util.logging.Level;
import java.util.logging.Logger;

import me.scriblon.plugins.chancecraft.listeners.ChanceCraftListener;
import me.scriblon.plugins.chancecraft.managers.ChanceManager;
import me.scriblon.plugins.chancecraft.managers.JobsManager;
import me.scriblon.plugins.chancecraft.managers.SettingsManager;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ChanceCraft extends JavaPlugin {
    
    //General fields
    private static final Logger LOG = Logger.getLogger("Minecraft");
    private static final String PREFIX = "[ChanceCraft] ";
    //Configuration
    private static ChanceCraft chance;
    private Configurator configurator;
    //managers
    private ChanceManager chanceManager;
    private SettingsManager settingsManager;
    private JobsManager jobsManager;
    //Listeners
    private ChanceCraftListener cListener;

    public void onEnable() {
        //Prepare Fields
        logInfo("is now loading");
        chance = this;
        //Configure 
        configurator = new Configurator();
        if(!configurator.isPluginConfigurable())
            return; //If this check fails the plugin should be disabled!
        
        //Setting up Managers
        settingsManager = new SettingsManager();
        settingsManager.configureSettings();
        jobsManager = new JobsManager();
        chanceManager = new ChanceManager();
        //Events
        cListener = new ChanceCraftListener();
        //Register All
        this.registerEvents();
        this.registerCommands();
        
        logInfo("has succesfully loaded.");
    }
    
    public void onDisable() {
        logInfo(" is now disabled.");
    }
    
    private void registerEvents(){
        //Set high as it is an defining plugin
        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvent(Type.CUSTOM_EVENT, cListener, Priority.High, this);
    }
    
    private void registerCommands(){
        //None at the moment.
    }

    public ChanceManager getChanceManager() {
        return chanceManager;
    }

    public Configurator getConfigurator() {
        return configurator;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }
    
    public JobsManager getJobsManager(){
        return jobsManager;
    }
    
    public static ChanceCraft getInstance(){
        return chance;
    }
    
    public static void logInfo(String message){
        LOG.log(Level.INFO, PREFIX.concat(message));
    }
    
    public static void logSevere(String message){
        LOG.log(Level.SEVERE, PREFIX.concat(message));
    }
}
