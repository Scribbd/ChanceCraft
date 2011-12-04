/*
 *Copyright (C) 2011 Coen Meulenkamp (Scriblon, ~theJaf) <coenmeulenkamp at gmail.com>
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
package me.scriblon.plugins.chancecraft.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Coen Meulenkamp (Scriblon, ~theJaf) <coenmeulenkamp at gmail.com>
 */
public class InvenUtil {
    
    public static ItemStack[] substractStack(ItemStack[] original, ItemStack[] substraction){
        if(original.length != substraction.length)
            return null;
        
        for(int i = 0; i<original.length; i++){
            if(original[i] != null){
                int newAmount = original[i].getAmount() - substraction[i].getAmount();
                if(newAmount >= 0){
                    original[i].setAmount(newAmount);
                } else {
                    original[i].setType(Material.AIR);
                    original[i].setAmount(0);
                }
            }
        }
        return original;
    }
    
    public static ItemStack[] emptyStack(ItemStack[] original){
        for(int i = 0; i<original.length; i++){
            if(original[i] != null){
                original[i].setType(Material.AIR);
                original[i].setAmount(0);
            }
        }
        return null;
    }
    
    public static void dropStack(ItemStack[] original, Location location){
        for(int i = 0; i<original.length; i++){
            location.getWorld().dropItemNaturally(location, original[i]);
        }
    }
}
