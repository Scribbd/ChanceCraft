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

/**
 *
 * @author Coen Meulenkamp (Scriblon, ~theJaf) <coenmeulenkamp at gmail.com>
 */        
public class GeneralConfigurations {
    private boolean debugPrint;
    private boolean commandPrint;
    private boolean detailPrint;
    private boolean returnOnFail;
    private boolean failToss;

    public GeneralConfigurations(boolean debugPrint, boolean commandPrint, boolean detailPrint, boolean returnOnFail, boolean failToss) {
        this.debugPrint = debugPrint;
        this.commandPrint = commandPrint;
        this.detailPrint = detailPrint;
        this.returnOnFail = returnOnFail;
        this.failToss = failToss;
    }

    public boolean isCommandPrint() {
        return commandPrint;
    }

    public void setCommandPrint(boolean commandPrint) {
        this.commandPrint = commandPrint;
    }

    public boolean isDebugPrint() {
        return debugPrint;
    }

    public void setDebugPrint(boolean debugPrint) {
        this.debugPrint = debugPrint;
    }

    public boolean isDetailPrint() {
        return detailPrint;
    }

    public void setDetailPrint(boolean detailPrint) {
        this.detailPrint = detailPrint;
    }

    public boolean isFailToss() {
        return failToss;
    }

    public void setFailToss(boolean failToss) {
        this.failToss = failToss;
    }

    public boolean isReturnOnFail() {
        return returnOnFail;
    }

    public void setReturnOnFail(boolean returnOnFail) {
        this.returnOnFail = returnOnFail;
    }
    
}
