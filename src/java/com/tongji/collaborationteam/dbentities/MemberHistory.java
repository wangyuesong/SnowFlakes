/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tongji.collaborationteam.dbentities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author WYS
 */
@Entity
@Table(name = "MemberHistory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MemberHistory.findAll", query = "SELECT m FROM MemberHistory m"),
    @NamedQuery(name = "MemberHistory.findById", query = "SELECT m FROM MemberHistory m WHERE m.id = :id"),
    @NamedQuery(name = "MemberHistory.findByMessage", query = "SELECT m FROM MemberHistory m WHERE m.message = :message"),
    @NamedQuery(name = "MemberHistory.findByTime", query = "SELECT m FROM MemberHistory m WHERE m.time = :time")})
public class MemberHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "message")
    private Integer message;
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    @ManyToOne
    private User memberId;
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @ManyToOne
    private Project projectId;

    public MemberHistory() {
    }

    public MemberHistory(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public User getMemberId() {
        return memberId;
    }

    public void setMemberId(User memberId) {
        this.memberId = memberId;
    }

    public Project getProjectId() {
        return projectId;
    }

    public void setProjectId(Project projectId) {
        this.projectId = projectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MemberHistory)) {
            return false;
        }
        MemberHistory other = (MemberHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tongji.collaborationteam.dbentities.MemberHistory[ id=" + id + " ]";
    }
    
}
