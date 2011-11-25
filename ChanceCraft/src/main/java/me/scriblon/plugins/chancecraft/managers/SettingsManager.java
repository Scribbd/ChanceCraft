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

import java.util.HashMap;
import java.util.Set;
import me.scriblon.plugins.chancecraft.container.GeneralConfigurations;
import me.scriblon.plugins.chancecraft.container.ItemChance;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

/**
 *
 * @author Coen Meulenkamp (Scriblon, ~theJaf) <coenmeulenkamp at gmail.com>
 */
public class SettingsManager {
    //SettingConatainers
    private final GeneralConfigurations general;
    
    
    public GeneralConfigurations getGeneralConfig(Configuration config){
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
    
    public HashMap getItemConfig(Configuration config) throws NullPointerException{
        HashMap<String,ItemChance> items = new HashMap<String,ItemChance>();
        ConfigurationSection section = config.getConfigurationSection("Items");
        //Get itemConfig
        Set<String> itemSet = section.getKeys(false);
        for(String itemID : itemSet){
            double normalChance = section.getDouble(itemID + ".NormalChance", 100.1);
            boolean professionExclusive = section.getBoolean(itemID + ".ProfessionExclusive", false);
            ConfigurationSection profSection = section.getConfigurationSection("Professions");
            items.put(itemID, new ItemChance(null, itemID, professionExclusive, normalChance, profSection));
        }
        return items;
    }
}
