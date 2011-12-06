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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import me.scriblon.plugins.chancecraft.ChanceCraft;
import me.scriblon.plugins.chancecraft.container.GeneralConfigurations;
import me.scriblon.plugins.chancecraft.container.ItemChance;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

/**
 *
 * @author Coen Meulenkamp (Scriblon, ~theJaf) <coenmeulenkamp at gmail.com>
 */
public class SettingsManager {
    private final ChanceCraft chance;    
    //Setting Containers
    public final GeneralConfigurations general;
    private Map<Integer, ItemChance> items;
    
    public SettingsManager(){
        chance = ChanceCraft.getInstance();
        
        final Configuration config = chance.getConfigurator().getConfig();
        general = extractGeneralConfig(config);
        items = extractItemConfig(config);
    }
    
    public GeneralConfigurations getGeneral(){
        return general;
    }
    
    public Map<Integer, ItemChance> getItems(){
        return Collections.unmodifiableMap(items);
    }
    
    public final GeneralConfigurations extractGeneralConfig(Configuration config){
        boolean debugPrint, commandPrint, detailPlayerPrint, returnOnFail, tossOnFail;
        ConfigurationSection section = config.getConfigurationSection("General");
        // Get userConfig
        debugPrint = section.getBoolean("DebugPrint", false);
        commandPrint = section.getBoolean("CommandPrint", false);
        detailPlayerPrint = section.getBoolean("DetailPlayerPrint", true);
        returnOnFail = section.getBoolean("ReturnOnFail", false);
        tossOnFail = section.getBoolean("TossOnFail");

        return new GeneralConfigurations(debugPrint, commandPrint, detailPlayerPrint, returnOnFail, tossOnFail);
    }
    
    public final HashMap extractItemConfig(Configuration config) throws NullPointerException{
        HashMap<String,ItemChance> itemsa = new HashMap<String,ItemChance>();
        ConfigurationSection section = config.getConfigurationSection("Items");
        //Get itemConfig
        Set<String> itemSet = section.getKeys(false);
        for(String itemID : itemSet){
            String name = itemID.substring(1);
            double normalChance = section.getDouble(itemID + ".NormalChance", 100.1);
            boolean professionExclusive = section.getBoolean(itemID + ".ProfessionExclusive", false);
            ConfigurationSection profSection = section.getConfigurationSection("Professions");
            itemsa.put(name, new ItemChance("", name, professionExclusive, normalChance, profSection));
        }
        return itemsa;
    }
}
