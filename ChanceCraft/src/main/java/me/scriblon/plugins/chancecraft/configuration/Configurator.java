/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriblon.plugins.chancecraft.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Set;
import me.scriblon.plugins.chancecraft.container.GeneralConfigurations;
import me.scriblon.plugins.chancecraft.container.chances.ItemChance;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Handels the configuration file and saves/load the configurations in nice containers.
 * @author Coen Meulenkamp (Scriblon, ~theJaf) <coenmeulenkamp at gmail.com>
 */
public class Configurator {
    
    public static GeneralConfigurations getGeneralConfig(Configuration config){
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
    
    public static HashMap getItemConfig(Configuration config) throws NullPointerException{
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
    
     public static void firstRun(File configFile, InputStream resource) throws FileNotFoundException, IOException{
         configFile.getParentFile().mkdirs();
         copyFile(resource, configFile);
    }
    
    public static void copyFile(InputStream in, File file) throws FileNotFoundException, IOException {
        OutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int len;
        while((len=in.read(buf))!=-1){
            out.write(buf,0,len);
        }
        out.close();
        in.close();       
    }
}
