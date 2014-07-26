/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.util;

import java.util.Date;

/**
 *
 * @author WYS
 */
public class EventJSON {
    private String description;
    private Date starttime;
    private Date finishtime;
    private String assignee;
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the starttime
     */
    public Date getStarttime() {
        return starttime;
    }

    /**
     * @param starttime the starttime to set
     */
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    /**
     * @return the finishtime
     */
    public Date getFinishtime() {
        return finishtime;
    }

    /**
     * @param finishtime the finishtime to set
     */
    public void setFinishtime(Date finishtime) {
        this.finishtime = finishtime;
    }

    /**
     * @return the assignee
     */
    public String getAssignee() {
        return assignee;
    }

    /**
     * @param assignee the assignee to set
     */
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
    
}
