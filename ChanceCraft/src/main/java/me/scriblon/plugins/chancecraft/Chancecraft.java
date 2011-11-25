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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.scriblon.plugins.chancecraft.configuration.Configurator;
import me.scriblon.plugins.chancecraft.configuration.Loader;
import me.scriblon.plugins.chancecraft.container.GeneralConfigurations;
import me.scriblon.plugins.chancecraft.listeners.ChanceCraftListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Chancecraft extends JavaPlugin {

    //General fields
    private static final Logger log = Logger.getLogger("Minecraft");
    private PluginDescriptionFile description;
    private String prefix;
    //Configuration
    private Plugin jobs;
    private GeneralConfigurations general;
    private HashMap items;
    //Listeners
    private ChanceCraftListener cListener;

    public void onEnable() {
        //Prepare Fields
        description = getDescription();
        prefix = "[" + description.getFullName() + "] ";
        log.log(Level.INFO, prefix + "is now loading");
        //Loading basics.
        final PluginManager pm = getServer().getPluginManager();
        final FileConfiguration config = this.getConfig();
        //Config file
        File configFile = new File(getDataFolder(), "config.yml");
        
        //Check Other Plugins installed
        if(!Loader.checkSpout(pm, log, prefix)){
            pm.disablePlugin(this); //If Spout isn't installed dissable own plugin.
            return;
        }
        jobs = Loader.checkJobs(pm, log, prefix);
        
        //Check if Config is already made, else let it be created.
        if(configFile.exists())
        {
            //Get general configuration
            general = Configurator.getGeneralConfig(config);
            //Get items
            try {
                items = Configurator.getItemConfig(config);
            }catch(NullPointerException ex){
                if(general.isDebugPrint())
                    log.log(Level.SEVERE, null, ex);
                log.log(Level.SEVERE, prefix + "Probably no items set, please set items and restart/reload plugin.");
                pm.disablePlugin(this);
                return;
            }
        }else{
            try {
                log.log(Level.INFO, prefix + "runs for the first time. Creating configuration-File");
                Configurator.firstRun(configFile, this.getResource("config.yml"));
                log.log(Level.INFO, prefix + "Configuration File created, please configure file and restart server.");        
            } catch (FileNotFoundException ex) {
                log.log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                log.log(Level.SEVERE, null, ex);
            } finally {
                pm.disablePlugin(this);
                return;
            }
        }
        //Register Events
        cListener = new ChanceCraftListener(general, items, jobs);
        pm.registerEvent(Type.CUSTOM_EVENT, cListener, Priority.High, this); //Set high as it is an defining plugin
        //Register Commands (non at the moment)
        
        //Register Tasks (non at the moment)
        
        
        log.log(Level.INFO, prefix + "has succesfully loaded.");
    }

    public void onDisable() {
        // TODO: Place any custom disable code here.
        log.log(Level.INFO, prefix + "has succesfully disabled. END OF LINE.");
    }
}
