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
 *You should have received a copy of the GNU General Public License
 *along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.scriblon.plugins.chancecraft.container;

import java.util.HashMap;
import java.util.Set;
import me.scriblon.plugins.chancecraft.ChanceCraft;
import org.bukkit.configuration.ConfigurationSection;

/**
 *
 * @author Coen Meulenkamp (Scriblon, ~theJaf) <coenmeulenkamp at gmail.com>
 */
public class ItemChance {
    // Fields
    private final String itemName;
    private final String itemID;
    private final boolean profExclusive;
    private final double normalChance;
    // Map with chance professions.
    private final HashMap<String,ProfChance> profs;
    
    /**
     * Needs profession map profided by external source.
     * @param itemName
     * @param itemID
     * @param profExclusive
     * @param normalChance 
     */
    public ItemChance(String itemName, String itemID, boolean profExclusive, double normalChance, HashMap profs) {
        profs = new HashMap<String, ProfChance>();
        this.itemName = itemName;
        this.itemID = itemID;
        this.profExclusive = profExclusive;
        this.normalChance = normalChance;
        this.profs = profs;
    }
    /**
     * Adds profession map on its own.
     * @param itemName
     * @param itemID
     * @param profExclusive
     * @param section 
     */
    public ItemChance(String itemName, String itemID, boolean profExclusive, double normalChance, ConfigurationSection section){
        final boolean debugCode = ChanceCraft.getInstance().getSettingsManager().getGeneral().isDebugPrint();
        
        profs = new HashMap<String, ProfChance>();
        this.itemID = itemID;
        this.itemName = itemName;
        this.profExclusive = profExclusive;
        this.normalChance = normalChance;
        
        if(debugCode){
            ChanceCraft.logInfo("(DebugCode) Item " + itemID + " has the following General-settings \n"
                    + "\t\t profExclusive \t" + profExclusive + "\n"
                    + "\t\t  normalChance \t" + normalChance);
        }
        
        // get configuration out of section
        if(section != null){
            Set<String> profList = section.getKeys(false);
            for(String prof : profList){
                double profChance0 = section.getDouble(prof + ".ProfessionChance0", -1.0);
                double profChanceMin = section.getDouble(prof + ".ProfessionChanceMin", -1.0);
                double profChanceMax = section.getDouble(prof + ".ProfessionChanceMax", -1.0);
                int minProfLvl = section.getInt(prof + ".MinProflvl", -1);
                int maxProfLvl = section.getInt(prof + ".MaxProflvl", -1);
                profs.put(prof, new ProfChance(itemName, profChanceMin, profChanceMax, profChance0, minProfLvl, maxProfLvl));
                
                if(debugCode){
                    ChanceCraft.logInfo("(DebugCode) Item " + itemID + " has prof-stetting: \n"
                            + "\t       ProfName  " + prof + "\n"
                            + "\t\t  ProfChanceMin  " + profChanceMin + "\n"
                            + "\t\t  ProfChanceMax  " + profChanceMax + "\n"
                            + "\t\t ProfChanceZero  " + profChance0 + "\n"
                            + "\t\t     ProfMinLvl  " + minProfLvl + "\n"
                            + "\t\t     ProfMaxLvl  " + maxProfLvl + "\n");
                }
            }
        } else {
            if(debugCode)
                ChanceCraft.logInfo("(DebugCode) Item " + itemID + " has no set proffesions.");
        }
    }
    
    //Methods for recieving professional data
    public boolean hasProfession(String profName){
        return profs.containsKey(profName);
    }
    
    public ProfChance getProfession(String profName){
        return profs.get(profName);
    }
    
    public Set<String> getProfessions(){
        return profs.keySet();
    }
    
    public double getChance(String profName, int lvl){
        return profs.get(profName).calculateChance(lvl);
    }
    
    //Auto generated
    public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public double getNormalChance() {
        return normalChance;
    }

    public boolean isProfExclusive() {
        return profExclusive;
    }
    
    public HashMap<String, ProfChance> getProfChances(){
        return profs;
    }
}
