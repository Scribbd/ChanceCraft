/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriblon.plugins.chancecraft;

import me.scriblon.plugins.chancecraft.util.Linker;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginManager;

/**
 * Handels the configuration file and saves/load the configurations in nice containers.
 * @author Coen Meulenkamp (Scriblon, ~theJaf) <coenmeulenkamp at gmail.com>
 */
public class Configurator {    
    
    private ChanceCraft plugin;
    
    
    public Configurator(){
        plugin = ChanceCraft.getInstance();
    }
    
    public boolean isPluginConfigurable(){
        // Check if Spout is Installed
        if(!Linker.checkSpout(plugin.getServer().getPluginManager())){
            this.requestStop();
            return false;
        }     
        // Check config.yml file
        if(!checkFiles())
            return false;
        // Check contents config.yml file
        if(!checkItems())
            return false;
        // And finally when everything checks out:
        return true;
    }
    
    private boolean checkFiles(){
        final File configFile = new File(plugin.getDataFolder() + File.separator + "config.yml");
        if(!configFile.exists()){
            try {
                ChanceCraft.logInfo("runs for the first time. Creating configuration-file");
                this.firstRun(configFile, plugin.getResource("config.yml"));
                ChanceCraft.logInfo("Configuration File created. Please configure this file and restart server.");
                this.requestStop();
            } catch (FileNotFoundException ex) {
                ChanceCraft.logSevere(ex.getMessage());
            } catch (IOException ex) {
                ChanceCraft.logSevere(ex.getMessage());
            } finally {
                return false;
            }
        }
        return true;
    }
    
    private void firstRun(File configFile, InputStream resource) throws FileNotFoundException, IOException{
         configFile.getParentFile().mkdirs();
         copyFile(resource, configFile);
    }
    
    private void copyFile(InputStream in, File file) throws FileNotFoundException, IOException {
        final OutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int len;
        while((len=in.read(buf))!=-1){
            out.write(buf,0,len);
        }
        out.close();
        in.close();       
    }
    
    private boolean checkItems(){
        final ConfigurationSection itemSection = plugin.getConfig().getConfigurationSection("Items");
        if(itemSection.getKeys(false).isEmpty()){
            ChanceCraft.logSevere("detects that no items are configured. Please configure Items and restart server!");
            this.requestStop();
        }
        
        return true;
    }
    
    private void requestStop(){
        final PluginManager pm = plugin.getServer().getPluginManager();
        ChanceCraft.logSevere("recieved stop request! now disabeling plugin.");
        pm.disablePlugin(plugin);
    }
}
