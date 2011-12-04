/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriblon.plugins.chancecraft.listeners;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Coen
 */
public class CommandManager implements CommandExecutor{

    public boolean onCommand(CommandSender cs, Command cmnd, String label, String[] args) {
        if(!(cs instanceof Player)){
            cs.sendMessage("You need to be a player to use the commands.");
            return true;
        }
        
        return false;
    }
    
}
