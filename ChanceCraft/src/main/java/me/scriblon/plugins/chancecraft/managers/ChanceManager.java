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

import java.util.Map;
import me.scriblon.plugins.chancecraft.ChanceCraft;
import me.scriblon.plugins.chancecraft.container.ItemChance;
import me.scriblon.plugins.chancecraft.util.Dice;
import org.bukkit.entity.Player;

/**
 *
 * @author Coen Meulenkamp (Scriblon, ~theJaf) <coenmeulenkamp at gmail.com>
 */
public class ChanceManager {
    
    private final ChanceCraft plugin;
    private final SettingsManager settings;
    private final JobsManager jobs;
    private final Dice dice;
    
    private Map<Integer, ItemChance> items;
    
    public ChanceManager(){
        plugin =  ChanceCraft.getInstance();
        settings = plugin.getSettingsManager();
        jobs = plugin.getJobsManager();
        dice = new Dice();
    }
    
    public boolean isChanceItem(int typeID){
        return items.containsKey(typeID);
    }
    
    public boolean isCraftSucces(int typeID, Player player){
        if(!isChanceItem(typeID))
            return false;
        
        final ItemChance item = items.get(typeID);
        
        if(jobs.isAvailable()){
            return dice.succes(jobs.calculateChance(player, item));
        } else {
            return dice.succes(item.getNormalChance());
        }
    }
    
}
