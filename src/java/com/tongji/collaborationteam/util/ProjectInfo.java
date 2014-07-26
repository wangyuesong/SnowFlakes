/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.util;

/**
 *
 * @author WYS
 */
public class ProjectInfo {
    private int totalTasks;
    private int finishedTasks;
    private float percentage;
    private int memberNumbers;

    /**
     * @return the totalTasks
     */
    public int getTotalTasks() {
        return totalTasks;
    }

    /**
     * @param totalTasks the totalTasks to set
     */
    public void setTotalTasks(int totalTasks) {
        this.totalTasks = totalTasks;
    }

    /**
     * @return the finishedTasks
     */
    public int getFinishedTasks() {
        return finishedTasks;
    }

    /**
     * @param finishedTasks the finishedTasks to set
     */
    public void setFinishedTasks(int finishedTasks) {
        this.finishedTasks = finishedTasks;
    }

    /**
     * @return the percentage
     */
    public float getPercentage() {
        return percentage;
    }

    /**
     * @param percentage the percentage to set
     */
    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    /**
     * @return the memberNumbers
     */
    public int getMemberNumbers() {
        return memberNumbers;
    }

    /**
     * @param memberNumbers the memberNumbers to set
     */
    public void setMemberNumbers(int memberNumbers) {
        this.memberNumbers = memberNumbers;
    }

   
}
