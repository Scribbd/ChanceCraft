/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriblon.plugins.chancecraft.container;

/**
 *
 * @author Coen Meulenkamp (Scriblon, ~theJaf) <coenmeulenkamp at gmail.com>
 */
public class ProfChance {
    
    private String jobsName;
    private double startChance, endChance, zeroChance;
    private int startLvl, endLvl;

    public ProfChance(String jobsName, double startChance, double endChance, double zeroChance, int startLvl, int endLvl) {
        this.jobsName = jobsName;
        this.startChance = startChance;
        this.endChance = endChance;
        this.zeroChance = zeroChance;
        this.startLvl = startLvl;
        this.endLvl = endLvl;
    }

    public double getEndChance() {
        return endChance;
    }

    public int getEndLvl() {
        return endLvl;
    }

    public String getJobsName() {
        return jobsName;
    }

    public double getStartChance() {
        return startChance;
    }

    public int getStartLvl() {
        return startLvl;
    }

    public double getZeroChance() {
        return zeroChance;
    }
    
    public double calculateChance(int lvl){
        if(lvl <= startLvl){
            return zeroChance;
        }
        else if(lvl > endLvl){
            return endChance;
        }else{
            double increment = (endChance-startChance)/(endLvl-startLvl);
            return increment * (startLvl - lvl);
        }
    }
}
