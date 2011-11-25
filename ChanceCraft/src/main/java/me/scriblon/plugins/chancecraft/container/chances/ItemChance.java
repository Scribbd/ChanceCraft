/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriblon.plugins.chancecraft.container.chances;

import java.util.HashMap;
import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;

/**
 *
 * @author Coen Meulenkamp (Scriblon, ~theJaf) <coenmeulenkamp at gmail.com>
 */
public class ItemChance {
    // Fields
    private String itemName;
    private String itemID;
    private boolean profExclusive;
    private double normalChance;
    // Map with chance professions.
    private HashMap<String,ProfChance> profs;
    
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
        profs = new HashMap<String, ProfChance>();
        this.itemID = itemID;
        this.itemName = itemName;
        this.profExclusive = profExclusive;
        this.normalChance = normalChance;
        // get configuration out of section
        if(section == null){
            Set<String> profList = section.getKeys(false);
            for(String prof : profList){
                double profChance0 = section.getDouble(prof + ".ProfessionChance0", -1.0);
                double profChanceMin = section.getDouble(prof + ".ProfessionChanceMin", -1.0);
                double profChanceMax = section.getDouble(prof + ".ProfessionChanceMax", -1.0);
                int minProfLvl = section.getInt(prof + ".MinProflvl", -1);
                int maxProfLvl = section.getInt(prof + ".MaxProflvl", -1);
                profs.put(prof, new ProfChance(itemName, profChanceMin, profChanceMax, profChance0, minProfLvl, maxProfLvl));
            }
        }
    }
    
    //Methods for recieving professional data
    public boolean hasProfession(String profName){
        return profs.containsKey(profName);
    }
    
    public double getChance(String profName, int lvl){
        return profs.get(profName).calculateChance(lvl);
    }
    
    public ProfChance getProfessions(String profName){
        return profs.get(profName);
    }
    
    //Auto generated
    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getNormalChance() {
        return normalChance;
    }

    public void setNormalChance(double normalChance) {
        this.normalChance = normalChance;
    }

    public boolean isProfExclusive() {
        return profExclusive;
    }

    public void setProfExclusive(boolean profExclusive) {
        this.profExclusive = profExclusive;
    }
    
    public HashMap<String, ProfChance> getProfChances(){
        return profs;
    }
}
