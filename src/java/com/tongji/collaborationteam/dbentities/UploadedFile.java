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
@Table(name = "UploadedFile")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UploadedFile.findAll", query = "SELECT u FROM UploadedFile u"),
    @NamedQuery(name = "UploadedFile.findById", query = "SELECT u FROM UploadedFile u WHERE u.id = :id"),
    @NamedQuery(name = "UploadedFile.findByUploadTime", query = "SELECT u FROM UploadedFile u WHERE u.uploadTime = :uploadTime"),
    @NamedQuery(name = "UploadedFile.findByName", query = "SELECT u FROM UploadedFile u WHERE u.name = :name"),
    @NamedQuery(name = "UploadedFile.findByExt", query = "SELECT u FROM UploadedFile u WHERE u.ext = :ext"),
    @NamedQuery(name = "UploadedFile.findByDescription", query = "SELECT u FROM UploadedFile u WHERE u.description = :description"),
    @NamedQuery(name = "UploadedFile.findByDownloadCounts", query = "SELECT u FROM UploadedFile u WHERE u.downloadCounts = :downloadCounts")})
public class UploadedFile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "upload_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadTime;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "ext")
    private String ext;
    @Column(name = "description")
    private String description;
    @Column(name = "download_counts")
    private Integer downloadCounts;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Project projectId;

    public UploadedFile() {
    }

    public UploadedFile(Integer id) {
        this.id = id;
    }

    public UploadedFile(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDownloadCounts() {
        return downloadCounts;
    }

    public void setDownloadCounts(Integer downloadCounts) {
        this.downloadCounts = downloadCounts;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof UploadedFile)) {
            return false;
        }
        UploadedFile other = (UploadedFile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tongji.collaborationteam.dbentities.UploadedFile[ id=" + id + " ]";
    }
    
}
