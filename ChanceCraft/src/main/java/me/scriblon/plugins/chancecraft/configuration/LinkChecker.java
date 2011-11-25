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
package me.scriblon.plugins.chancecraft.configuration;

import me.scriblon.plugins.chancecraft.ChanceCraft;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * Althoug loader isn't the right name, it handels the checking and loading of
 * depenedent plugins.
 * @author Coen Meulenkamp (Scriblon, ~theJaf) <coenmeulenkamp at gmail.com>
 */
public class LinkChecker {

    public static boolean checkSpout(PluginManager pm) {
        if (pm.getPlugin("Spout") == null) {
            ChanceCraft.logInfo("detects that there is no Spout installed");
            return false;
        }
        return true;
    }

    public static Plugin checkJobs(PluginManager pm) {
        Plugin jobs = pm.getPlugin("Jobs");
        if (jobs == null) {
            ChanceCraft.logInfo("detects that there is no Jobs installed.");
        } else {
            ChanceCraft.logInfo("Jobs plugin detected.");
        }
        return jobs;
    }
}
